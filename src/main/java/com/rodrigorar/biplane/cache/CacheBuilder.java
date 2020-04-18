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

import com.rodrigorar.biplane.eviction.FactoryPolicy;

import java.time.Duration;
import java.util.function.Function;

public class CacheBuilder<K, V> {
	private Duration _timeToLive;
	private Integer _maxEntries;
	private Function<K, V> _cacheLoader;

	public CacheBuilder timeToLive(Duration timeToLive) {
		_timeToLive = timeToLive;
		return this;
	}

	public CacheBuilder maxEntries(int maxEntries) {
		_maxEntries = maxEntries;
		return this;
	}

	public CacheBuilder cacheLoader(Function<K, V> cacheLoder) {
		_cacheLoader = cacheLoder;
		return this;
	}

	private CacheConfiguration<V> buildConfiguration() {
		final CacheConfiguration<V> configuration;
		if (_maxEntries != null && _timeToLive != null) {
			configuration = new CacheConfiguration<>(FactoryPolicy.timeBased(_timeToLive), _maxEntries);
		} else if (_maxEntries != null && _timeToLive == null) {
			configuration = new CacheConfiguration<>(_maxEntries);
		} else if (_maxEntries == null && _timeToLive != null) {
			configuration = new CacheConfiguration<>(FactoryPolicy.timeBased(_timeToLive));
		} else {
			configuration = new CacheConfiguration<>();
		}
		return configuration;
	}

	private InternalCache<K, V> buildInternalCache(CacheConfiguration<V> configuration) {
		final InternalCache<K, V> internalCache;
		if (_cacheLoader != null) {
			internalCache = new InternalCacheLoading<>(configuration, _cacheLoader);
		} else {
			internalCache = new InternalCacheSimple<>(configuration);
		}
		return internalCache;
	}

	public Cache<K, V> build() {
		CacheConfiguration<V> configuration = buildConfiguration();
		InternalCache<K, V> internalCache = buildInternalCache(configuration);
		return new Cache<>(internalCache);
	}
}
