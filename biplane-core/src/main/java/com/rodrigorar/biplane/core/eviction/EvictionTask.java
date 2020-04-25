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

package com.rodrigorar.biplane.core.eviction;

import com.rodrigorar.biplane.core.cache.InternalCache;
import com.rodrigorar.biplane.core.utils.Validator;

import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

class EvictionTask extends TimerTask {
	private final List<InternalCache> _caches;

	EvictionTask() {
		_caches = new LinkedList<>();
	}

	void addCache(InternalCache cache) {
		Validator.isNotNull(cache);
		_caches.add(cache);
	}

	@Override
	public void run() {
		_caches.forEach(InternalCache::evict);
	}
}
