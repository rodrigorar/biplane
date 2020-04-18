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

import java.util.Optional;

class CacheConfiguration<V> {
	private Policy<V> _evictionPolicy;
	private Integer _maxEntries;
	
	CacheConfiguration(Policy<V> evictionPolicy, int maxEntries) {
		_evictionPolicy = evictionPolicy;
		_maxEntries = maxEntries;
	}

	CacheConfiguration(Policy<V> evictionPolicy) {
		_evictionPolicy = evictionPolicy;
	}

	CacheConfiguration(int maxEntries) {
		_maxEntries = maxEntries;
	}

	CacheConfiguration() { /* Default Constructor */ }
	
	Optional<Policy<V>> getEvictionPolicy() {
		return Optional.ofNullable(_evictionPolicy);
	}
	
	Optional<Integer> getMaxEntries() {
		return Optional.ofNullable(_maxEntries);
	}
}
