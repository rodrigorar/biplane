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

package com.rodrigorar.biplane.toolkit.cache;

import com.rodrigorar.biplane.core.cache.AbstractFactoryCache;
import com.rodrigorar.biplane.core.cache.Cache;
import com.rodrigorar.biplane.core.cache.InternalCache;
import com.rodrigorar.biplane.core.utils.Validator;
import com.rodrigorar.biplane.toolkit.policy.FactoryPolicy;

import java.time.Duration;
import java.util.function.Function;

public class FactoryCacheLoading<K, V> extends AbstractFactoryCache<K, V, CacheConfigurationLoading<K, V>> {
	private Duration _timeToLive;
	private Integer _maxEntries;
	private Function<K, V> _cacheLoader;

	public FactoryCacheLoading<K, V> timeToLive(Duration timeToLive) {
		_timeToLive = timeToLive;
		return this;
	}

	public FactoryCacheLoading<K, V> maxEntries(int maxEntries) {
		_maxEntries = maxEntries;
		return this;
	}

	public FactoryCacheLoading<K, V> cacheLoader(Function<K, V> cacheLoader) {
		_cacheLoader = cacheLoader;
		return this;
	}

	@Override
	protected Cache<K, V> doBuild() {
		Validator.validateOrDefault(_timeToLive, Duration.ofMinutes(10));
		Validator.validateOrDefault(_maxEntries, 100);
		Validator.validateOrDefault(_cacheLoader, o -> null);

		_configuration.setEvictionPolicy(FactoryPolicy.timeBased(_timeToLive));
		_configuration.setMaxEntries(_maxEntries);
		_configuration.setCacheLoader(_cacheLoader);
		InternalCache<K, V> internal = new InternalCacheLoading<K, V>(_configuration);
		return new Cache<>(internal);
	}
}
