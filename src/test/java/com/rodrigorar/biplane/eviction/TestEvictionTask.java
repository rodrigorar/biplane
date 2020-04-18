package com.rodrigorar.biplane.eviction;

import com.rodrigorar.biplane.cache.InternalCache;
import org.junit.Test;
import org.mockito.Mockito;

public class TestEvictionTask {

	@Test
	public void testEvictionSuccess() {
		InternalCache mockedCache1 = Mockito.mock(InternalCache.class);
		InternalCache mockedCache2 = Mockito.mock(InternalCache.class);
		InternalCache mockedCache3 = Mockito.mock(InternalCache.class);

		EvictionTask underTest = new EvictionTask();
		underTest.addCache(mockedCache1);
		underTest.addCache(mockedCache2);
		underTest.addCache(mockedCache3);
		underTest.run();

		Mockito.verify(mockedCache1).evict();
		Mockito.verify(mockedCache2).evict();
		Mockito.verify(mockedCache3).evict();

		Mockito.verifyNoMoreInteractions(mockedCache1, mockedCache2, mockedCache3);
	}

	@Test
	public void testEvictionNoCaches() {
		EvictionTask underTest = new EvictionTask();
		underTest.run();
	}

	@Test (expected = IllegalArgumentException.class)
	public void testAddNullCache() {
		EvictionTask underTest = new EvictionTask();
		underTest.addCache(null);
	}
}
