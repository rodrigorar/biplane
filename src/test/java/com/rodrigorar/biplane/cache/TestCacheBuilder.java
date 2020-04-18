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

package com.rodrigorar.biplane.cache;

import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class TestCacheBuilder {
	static final Duration TIME_TO_LIVE = Duration.of(5, ChronoUnit.MINUTES);
	static final int MAX_ENTRIES = 100;

	@Test
	public void buildSimpleCacheNoPolicy() {
		CacheBuilder<String, String> builder = new CacheBuilder<>();
		Cache<String, String> result = builder.build();
		Assert.assertNotNull(result);
		InternalCache<String, String> internalCache = result.getInternalCache();
		Assert.assertTrue(InternalCacheSimple.class.isInstance(internalCache));
	}

	@Test
	public void buildSimpleCacheWithPolicy() {
		CacheBuilder<String, String> builder = new CacheBuilder<>();
		builder.timeToLive(TIME_TO_LIVE);
		Cache<String, String> result = builder.build();
		Assert.assertNotNull(result);
		InternalCache<String, String> internalCache = result.getInternalCache();
		Assert.assertTrue(InternalCacheSimple.class.isInstance(internalCache));
		CacheConfiguration<String> configuration = internalCache.getConfiguration();
		Assert.assertNotNull(configuration);
		Assert.assertTrue(configuration.getEvictionPolicy().isPresent());
	}

	@Test
	public void buildLoadingCacheNoPolicy() {
		CacheBuilder<String, String> builder = new CacheBuilder<>();
		builder.cacheLoader(value -> value);
		Cache<String, String> result = builder.build();
		Assert.assertNotNull(result);
		InternalCache<String, String> internalCache = result.getInternalCache();
		Assert.assertTrue(InternalCacheLoading.class.isInstance(internalCache));
	}

	@Test
	public void buildLoadingCacheWithPolicy() {
		CacheBuilder<String, String> builder = new CacheBuilder<>();
		builder.timeToLive(TIME_TO_LIVE);
		builder.cacheLoader(value -> value);
		Cache<String, String> result = builder.build();
		Assert.assertNotNull(result);
		InternalCache<String, String> internalCache = result.getInternalCache();
		Assert.assertTrue(InternalCacheLoading.class.isInstance(internalCache));
		CacheConfiguration<String> configuration = internalCache.getConfiguration();
		Assert.assertNotNull(configuration);
		Assert.assertTrue(configuration.getEvictionPolicy().isPresent());
	}
}
