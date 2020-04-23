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

class CacheConfigurationSimple<K, V> implements CacheConfiguration<K, V> {
	private final Policy<V> _evictionPolicy;
	private final Integer _maxEntries;
	
	CacheConfigurationSimple(Policy<V> evictionPolicy, int maxEntries) {
		_evictionPolicy = evictionPolicy;
		_maxEntries = maxEntries;
	}

	@Override
	public Policy<V> getEvictionPolicy() {
		return _evictionPolicy;
	}

	@Override
	public int getMaxEntries() {
		return _maxEntries;
	}

	@Override
	public CacheConfigurationSimple subCast() {
		return this;
	}
}
