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

package com.rodrigorar.biplane.eviction;

import com.rodrigorar.biplane.cache.Entry;
import com.rodrigorar.biplane.utils.Validator;

import java.time.Duration;
import java.time.Instant;

class PolicyTimeBased<V> implements Policy<V> {
	private final Duration _timeToLive;

	PolicyTimeBased(Duration timeToLive) {
		_timeToLive = timeToLive;
	}

	@Override
	public boolean evaluate(Entry<V> value) {
		Validator.isNotNull(value);

		Instant lastAccessTime = value.getLastAccessTime();
		return Instant.now().isBefore(lastAccessTime.plusMillis(_timeToLive.toMillis()));
	}
}
