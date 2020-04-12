/**
Copyright 2020 Rodrigo Ramos Rosa

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
**/

package com.rodrigorar.biplane.cache;

import com.rodrigorar.biplane.eviction.Policy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class TestInternalCacheSimple {
	static final String KEY_1 = "key-1";
	static final String KEY_2 = "key-2";
	static final String KEY_3 = "key-3";
	static final String KEY_NULL = null;
	static final Entry<String> VALUE_1 = new Entry("value-1");
	static final Entry<String> VALUE_2 = new Entry("value-2");
	static final Entry<String> VALUE_3 = new Entry("value-3");
	static final Entry<String> VALUE_NULL = null;
	
	// put
	
	@Test
	public void testPutSuccess() {
		final CacheConfiguration mockedConfiguration =
				new CacheConfiguration(new Policy<String>() {
					@Override
					public boolean evaluate(Entry<String> value) {
						return true;
					}
				}, 100);

		final InternalCacheSimple underTest = new InternalCacheSimple(mockedConfiguration);
		underTest.put(KEY_1, VALUE_1);
		underTest.put(KEY_2, VALUE_2);
		underTest.put(KEY_3, VALUE_3);

		final Optional<Entry<String>> entry1 = underTest.get(KEY_1);
		Assertions.assertNotNull(entry1);
		Assertions.assertTrue(entry1.isPresent());
		Assertions.assertEquals(VALUE_1.value(), entry1.get().value());

		final Optional<Entry<String>> entry2 = underTest.get(KEY_2);
		Assertions.assertNotNull(entry2);
		Assertions.assertTrue(entry2.isPresent());
		Assertions.assertEquals(VALUE_2.value(), entry2.get().value());

		final Optional<Entry<String>> entry3 = underTest.get(KEY_3);
		Assertions.assertNotNull(entry3);
		Assertions.assertTrue(entry3.isPresent());
		Assertions.assertEquals(VALUE_3.value(), entry3.get().value());
	}
	
	@Test
	public void testPutNullKey() {
		final CacheConfiguration mockedConfiguration =
				new CacheConfiguration(new Policy<String>() {
					@Override
					public boolean evaluate(Entry<String> value) {
						return true;
					}
				}, 100);

		final InternalCacheSimple underTest = new InternalCacheSimple(mockedConfiguration);
		Assertions.assertThrows(IllegalArgumentException.class, () -> underTest.put(KEY_NULL, VALUE_1));
	}
	
	@Test
	public void testPutNullValue() {
		final CacheConfiguration mockedConfiguration =
				new CacheConfiguration(new Policy<String>() {
					@Override
					public boolean evaluate(Entry<String> value) {
						return true;
					}
				}, 100);

		final InternalCacheSimple underTest = new InternalCacheSimple(mockedConfiguration);
		Assertions.assertThrows(IllegalArgumentException.class, () -> underTest.put(KEY_1, VALUE_NULL));
	}
	
	// get
	
	@Test
	public void testGetNullKey() {
		final CacheConfiguration mockedConfiguration =
				new CacheConfiguration(new Policy<String>() {
					@Override
					public boolean evaluate(Entry<String> value) {
						return true;
					}
				}, 100);

		final InternalCacheSimple underTest = new InternalCacheSimple(mockedConfiguration);
		Assertions.assertThrows(IllegalArgumentException.class, () -> underTest.get(KEY_NULL));
	}
	
	@Test
	public void testGetNoEntry() {
		final CacheConfiguration mockedConfiguration =
				new CacheConfiguration(new Policy<String>() {
					@Override
					public boolean evaluate(Entry<String> value) {
						return true;
					}
				}, 100);

		final InternalCacheSimple underTest = new InternalCacheSimple(mockedConfiguration);
		Optional<Entry<String>> result = underTest.get(KEY_1);

		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isPresent());
	}
	
	// remove
	
	@Test
	public void testRemoveSuccess() {
		// TODO: Not implemented
	}
	
	@Test
	public void testRemoveNullKey() {
		// TODO: Not implemented
	}
	
	@Test
	public void testRemoveNoEntry() {
		// TODO: Not implemented
	}
	
	// evict
	
	@Test
	public void testEvictSuccess() {
		// TODO: Not implemented
	}
	
	@Test
	public void testEvictNoEntries() {
		// TODO: Not implemented
	}
}
