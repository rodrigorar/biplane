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

package com.rodrigorar.biplane.core.cache;

import java.time.Instant;

public class Entry<V> {
	private final V _value;
	private final Instant _creationTime;
	private Instant _lastAccessTime;
	
	public Entry(V value) {
		_value = value;
		_creationTime = Instant.now();
		_lastAccessTime = Instant.now();
	}
	
	V value() {
		_lastAccessTime = Instant.now();
		return _value;
	}
	
	Instant getCreationTime() {
		return _creationTime;
	}
	
	public Instant getLastAccessTime() {
		return _lastAccessTime;
	}
}
