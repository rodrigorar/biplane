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

import com.rodrigorar.biplane.eviction.Policy;

import java.util.function.Function;

public class CacheConfigurationLoading<K, V> implements CacheConfiguration<K, V> {
	private final Policy<V> _evictionPolicy;
	private final int _maxEntries;
	private final Function<K, V> _cacheLoader;

	public CacheConfigurationLoading(
			Policy<V> evictionPolicy,
			Function<K, V> cacheLoader,
			int maxEntries) {

		_evictionPolicy = evictionPolicy;
		_maxEntries = maxEntries;
		_cacheLoader = cacheLoader;
	}

	@Override
	public Policy<V> getEvictionPolicy() {
		return _evictionPolicy;
	}

	@Override
	public int getMaxEntries() {
		return _maxEntries;
	}

	public Function<K, V> getCacheLoader() {
		return _cacheLoader;
	}

	@Override
	public CacheConfigurationLoading subCast() {
		return this;
	}
}
