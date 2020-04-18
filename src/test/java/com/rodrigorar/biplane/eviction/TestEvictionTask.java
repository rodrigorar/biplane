/***
 * Copyright 2020 Rodrigo Ramos Rosa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
