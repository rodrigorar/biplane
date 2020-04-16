package com.rodrigorar.biplane.cache;

import com.rodrigorar.biplane.eviction.Policy;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class TestInternalCacheLoading {
	static final String KEY_1 = "key-1";
	static final String KEY_2 = "key-2";
	static final String KEY_3 = "key-3";
	static final String KEY_NULL = null;
	static final Entry<String> VALUE_1 = new Entry<>("value-1");
	static final Entry<String> VALUE_2 = new Entry<>("value-2");
	static final Entry<String> VALUE_3 = new Entry<>("value-3");
	static final Entry<String> VALUE_NULL = null;
	static final String LOADED_VALUE = "value-loaded-from-loader";

	// put

	@Test
	public void testPutSuccess() {
		final CacheConfiguration<String> mockedConfiguration = Mockito.mock(CacheConfiguration.class);
		final Function<String, String> mockedCacheLoader = Mockito.mock(Function.class);
		final InternalCacheLoading<String, String> underTest;
		underTest = new InternalCacheLoading<>(mockedConfiguration, mockedCacheLoader);
		underTest.put(KEY_1, VALUE_1);
		underTest.put(KEY_2, VALUE_2);
		underTest.put(KEY_3, VALUE_3);

		final Optional<Entry<String>> entry1 = underTest.get(KEY_1);
		Assert.assertNotNull(entry1);
		Assert.assertTrue(entry1.isPresent());
		Assert.assertEquals(VALUE_1.value(), entry1.get().value());

		final Optional<Entry<String>> entry2 = underTest.get(KEY_2);
		Assert.assertNotNull(entry2);
		Assert.assertTrue(entry2.isPresent());
		Assert.assertEquals(VALUE_2.value(), entry2.get().value());

		final Optional<Entry<String>> entry3 = underTest.get(KEY_3);
		Assert.assertNotNull(entry3);
		Assert.assertTrue(entry3.isPresent());
		Assert.assertEquals(VALUE_3.value(), entry3.get().value());

		Mockito.verifyNoMoreInteractions(mockedConfiguration);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testPutNullKey() {
		final CacheConfiguration<String> mockedConfiguration = Mockito.mock(CacheConfiguration.class);
		final Function<String, String> mockedCacheLoader = Mockito.mock(Function.class);
		final InternalCacheLoading<String, String> underTest;
		underTest = new InternalCacheLoading<>(mockedConfiguration, mockedCacheLoader);
		underTest.put(KEY_NULL, VALUE_1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testPutNullValue() {
		final CacheConfiguration<String> mockedConfiguration = Mockito.mock(CacheConfiguration.class);
		final Function<String, String> mockedCacheLoader = Mockito.mock(Function.class);
		final InternalCacheLoading<String, String> underTest;
		underTest = new InternalCacheLoading<>(mockedConfiguration, mockedCacheLoader);
		underTest.put(KEY_1, VALUE_NULL);
	}

	// get

	@Test (expected = IllegalArgumentException.class)
	public void testGetNullKey() {
		final CacheConfiguration<String> mockedConfiguration = Mockito.mock(CacheConfiguration.class);
		final Function<String, String> mockedCacheLoader = Mockito.mock(Function.class);
		final InternalCacheLoading<String, String> underTest;
		underTest = new InternalCacheLoading<>(mockedConfiguration, mockedCacheLoader);
		underTest.get(KEY_NULL);
	}

	@Test
	public void testGetNoEntry() {
		final CacheConfiguration<String> mockedConfiguration = Mockito.mock(CacheConfiguration.class);
		final Function<String, String> mockedCacheLoader = Mockito.mock(Function.class);
		Mockito.when(mockedCacheLoader.apply(ArgumentMatchers.any()))
				.thenReturn(LOADED_VALUE);
		final InternalCacheLoading<String, String> underTest;
		underTest = new InternalCacheLoading<>(mockedConfiguration, mockedCacheLoader);
		Optional<Entry<String>> result = underTest.get(KEY_1);
		Assert.assertNotNull(result);
		Assert.assertTrue(result.isPresent());
		Assert.assertEquals(LOADED_VALUE, result.get().value());

		Mockito.verify(mockedCacheLoader)
				.apply(ArgumentMatchers.any());

		Mockito.verifyNoMoreInteractions(mockedConfiguration, mockedCacheLoader);
	}

	// remove

	@Test
	public void testRemoveSuccess() {
		final CacheConfiguration<String> mockedConfiguration = Mockito.mock(CacheConfiguration.class);
		final Function<String, String> mockedCacheLoader = Mockito.mock(Function.class);
		final InternalCacheLoading<String, String> underTest;
		underTest = new InternalCacheLoading<>(mockedConfiguration, mockedCacheLoader);
		underTest.put(KEY_1, VALUE_1);
		underTest.put(KEY_2, VALUE_2);
		underTest.put(KEY_3, VALUE_3);

		Optional<Entry<String>> key2Result = underTest.get(KEY_2);
		Assert.assertNotNull(key2Result);
		Assert.assertTrue(key2Result.isPresent());
		Assert.assertEquals(VALUE_2, key2Result.get());

		underTest.remove(KEY_2);
		Optional<Entry<String>> result = underTest.get(KEY_2);
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isPresent());

		Mockito.verifyNoMoreInteractions(mockedConfiguration);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testRemoveNullKey() {
		final CacheConfiguration<String> mockedConfiguration = Mockito.mock(CacheConfiguration.class);
		final Function<String, String> mockedCacheLoader = Mockito.mock(Function.class);
		final InternalCacheLoading<String, String> underTest;
		underTest = new InternalCacheLoading<>(mockedConfiguration, mockedCacheLoader);
		underTest.remove(KEY_NULL);
	}

	@Test
	public void testRemoveNoEntry() {
		final CacheConfiguration<String> mockedConfiguration = Mockito.mock(CacheConfiguration.class);
		final Function<String, String> mockedCacheLoader = Mockito.mock(Function.class);
		final InternalCacheLoading<String, String> underTest;
		underTest = new InternalCacheLoading<>(mockedConfiguration, mockedCacheLoader);
		underTest.put(KEY_1, VALUE_1);
		underTest.put(KEY_3, VALUE_3);

		underTest.remove(KEY_2);
		Optional<Entry<String>> result = underTest.get(KEY_2);
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isPresent());

		Mockito.verifyNoMoreInteractions(mockedConfiguration);
	}

	// evict

	@Test
	public void testEvictSuccess() {
		final Policy<String> mockedPolicy = Mockito.mock(Policy.class);
		Mockito.when(mockedPolicy.evaluate(ArgumentMatchers.any(Entry.class)))
				.thenReturn(true);
		final CacheConfiguration<String> mockedConfiguration = Mockito.mock(CacheConfiguration.class);
		Mockito.when(mockedConfiguration.getEvictionPolicy())
				.thenReturn(mockedPolicy);

		final Function<String, String> mockedCacheLoader = Mockito.mock(Function.class);
		final InternalCacheLoading<String, String> underTest;
		underTest = new InternalCacheLoading<>(mockedConfiguration, mockedCacheLoader);
		underTest.put(KEY_1, VALUE_1);
		underTest.put(KEY_2, VALUE_2);
		underTest.put(KEY_3, VALUE_3);

		underTest.evict();

		Map<String, Entry<String>> entries = underTest.entries();
		Assert.assertNotNull(entries);
		Assert.assertEquals(0, entries.size());

		Mockito.verify(mockedConfiguration, Mockito.times(1))
				.getEvictionPolicy();
		Mockito.verify(mockedPolicy, Mockito.times(3))
				.evaluate(ArgumentMatchers.any(Entry.class));

		Mockito.verifyNoMoreInteractions(mockedConfiguration, mockedPolicy);
	}

	@Test
	public void testEvictNoEntries() {
		final Policy<String> mockedPolicy = Mockito.mock(Policy.class);
		Mockito.when(mockedPolicy.evaluate(ArgumentMatchers.any(Entry.class)))
				.thenReturn(true);
		final CacheConfiguration<String> mockedConfiguration = Mockito.mock(CacheConfiguration.class);
		Mockito.when(mockedConfiguration.getEvictionPolicy())
				.thenReturn(mockedPolicy);

		final Function<String, String> mockedCacheLoader = Mockito.mock(Function.class);
		final InternalCacheLoading<String, String> underTest;
		underTest = new InternalCacheLoading<>(mockedConfiguration, mockedCacheLoader);
		underTest.evict();

		Map<String, Entry<String>> entries = underTest.entries();
		Assert.assertNotNull(entries);
		Assert.assertEquals(0, entries.size());

		Mockito.verify(mockedConfiguration, Mockito.times(1))
				.getEvictionPolicy();

		Mockito.verifyNoMoreInteractions(mockedConfiguration, mockedPolicy);
	}
}
