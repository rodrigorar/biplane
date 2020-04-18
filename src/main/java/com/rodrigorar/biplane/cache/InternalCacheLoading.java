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

import com.rodrigorar.biplane.utils.Validator;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class InternalCacheLoading<K, V> implements InternalCache<K, V> {
	private final Map<K, Entry<V>> _entryMap;
	private final CacheConfiguration<V> _configuration;
	private final Function<K, V> _cacheLoader;

	InternalCacheLoading(CacheConfiguration configuration, Function<K, V> cacheLoader) {
		_entryMap = new ConcurrentHashMap<>();
		_configuration = configuration;
		_cacheLoader = cacheLoader;
	}

	@Override
	public void put(K key, Entry<V> value) {
		Validator.isNotNull(key);
		Validator.isNotNull(value);

		_entryMap.put(key, value);
	}

	@Override
	public Optional<Entry<V>> get(K key) {
		Validator.isNotNull(key);

		if (! _entryMap.containsKey(key)) {
			V loadedValue = _cacheLoader.apply(key);
			if (loadedValue != null) {
				_entryMap.put(key, new Entry<>(loadedValue));
			}
		}

		return Optional.ofNullable(_entryMap.get(key));
	}

	@Override
	public void remove(K key) {
		Validator.isNotNull(key);

		if (_entryMap.containsKey(key)) {
			_entryMap.remove(key);
		}
	}

	@Override
	public void evict() {
		_configuration.getEvictionPolicy()
				.ifPresent(policy ->
					_entryMap.forEach((k, v) -> {
						if (policy.evaluate(v)) {
							_entryMap.remove(k);
						}
					}));
	}

	@Override
	public Map<K, Entry<V>> entries() {
		return _entryMap;
	}

	@Override
	public CacheConfiguration<V> getConfiguration() {
		return _configuration;
	}
}
