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
import java.util.Optional;

public class Cache<K, V> {
	private final InternalCache<K, V> _internalCache;

	public Cache(InternalCache<K, V> internalCache) {
		_internalCache = internalCache;
	}

	public void put(K key, V value) {
		Validator.isNotNull(key);
		Validator.isNotNull(value);

		_internalCache.put(key, new Entry<>(value));
	}

	public Optional<V> get(K key) {
		Validator.isNotNull(key);

		return _internalCache.get(key).map(Entry::value);
	}

	public void remove(K key) {
		Validator.isNotNull(key);

		_internalCache.remove(key);
	}

	InternalCache<K, V> getInternalCache() {
		return _internalCache;
	}
}
