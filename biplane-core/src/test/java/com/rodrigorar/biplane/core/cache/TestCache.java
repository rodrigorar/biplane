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

package com.rodrigorar.biplane.core.cache;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class TestCache {
	private static final String KEY_1 = "key-1";
	private static final String NULL_KEY = null;
	private static final String VALUE_1 = "value-1";
	private static final String NULL_VALUE = null;

	// put

	@Test
	public void putSuccess() {
		InternalCache<String, String> mockedInternalCache = Mockito.mock(InternalCache.class);

		Cache<String, String> underTest = new Cache<>(mockedInternalCache);
		underTest.put(KEY_1, VALUE_1);

		Mockito.verify(mockedInternalCache).put(eq(KEY_1), any(Entry.class));

		Mockito.verifyNoMoreInteractions(mockedInternalCache);
	}

	@Test (expected = IllegalArgumentException.class)
	public void putNullKey() {
		InternalCache<String, String> mockedInternalCache = Mockito.mock(InternalCache.class);

		Cache<String, String> underTest = new Cache<>(mockedInternalCache);
		try {
			underTest.put(NULL_KEY, VALUE_1);
		} finally {
			Mockito.verifyNoMoreInteractions(mockedInternalCache);
		}
	}

	@Test (expected = IllegalArgumentException.class)
	public void putNullValue() {
		InternalCache<String, String> mockedInternalCache = Mockito.mock(InternalCache.class);

		Cache<String, String> underTest = new Cache<>(mockedInternalCache);
		try {
			underTest.put(KEY_1, NULL_VALUE);
		} finally {
			Mockito.verifyNoMoreInteractions(mockedInternalCache);
		}
	}

	// get

	@Test
	public void getSuccess() {
		InternalCache<String, String> mockedInternalCache = Mockito.mock(InternalCache.class);
		Mockito.when(mockedInternalCache.get(eq(KEY_1))).thenReturn(Optional.of(new Entry<>(VALUE_1)));

		Cache<String, String> underTest = new Cache<>(mockedInternalCache);
		Optional<String> result = underTest.get(KEY_1);

		Assert.assertTrue(result.isPresent());
		Assert.assertEquals(VALUE_1, result.get());

		Mockito.verify(mockedInternalCache).get(eq(KEY_1));

		Mockito.verifyNoMoreInteractions(mockedInternalCache);
	}

	@Test (expected = IllegalArgumentException.class)
	public void getNullKey() {
		InternalCache<String, String> mockedInternalCache = Mockito.mock(InternalCache.class);

		Cache<String, String> underTest = new Cache<>(mockedInternalCache);
		try {
			underTest.get(NULL_KEY);
		} finally {
			Mockito.verifyNoMoreInteractions(mockedInternalCache);
		}
	}

	// remove

	@Test
	public void removeSuccess() {
		InternalCache<String, String> mockedInternalCache = Mockito.mock(InternalCache.class);

		Cache<String, String> underTest = new Cache<>(mockedInternalCache);
		underTest.remove(KEY_1);

		Mockito.verify(mockedInternalCache).remove(eq(KEY_1));

		Mockito.verifyNoMoreInteractions(mockedInternalCache);
	}

	@Test (expected = IllegalArgumentException.class)
	public void removeNullKey() {
		InternalCache<String, String> mockedInternalCache = Mockito.mock(InternalCache.class);

		Cache<String, String> underTest = new Cache<>(mockedInternalCache);
		try {
			underTest.remove(NULL_KEY);
		} finally {
			Mockito.verifyNoMoreInteractions(mockedInternalCache);
		}
	}
}
