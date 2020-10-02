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

import com.rodrigorar.biplane.eviction.EvictionWorker;

public abstract class AbstractFactoryCache<K, V, C extends CacheConfiguration> {
	protected C _configuration;

	public final void setConfiguration(C configuration) {
		if (_configuration == null) {
			_configuration = configuration;
		}
	}

	public final Cache<K, V> build() {
		final Cache<K, V> cache = doBuild();
		EvictionWorker evictionWorker = EvictionWorker.getInstance();
		evictionWorker.addCache(cache.getInternalCache());
		return cache;
	}

	protected abstract Cache<K, V> doBuild();
}
