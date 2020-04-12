/**
Copyright 2020 Rodrigo Ramos Rosa

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
**/

package com.rodrigorar.cache;

import java.util.Map;

interface InternalCache<K, V> {	
	void put(K key, Entry<V> value);
	Entry<V> get(K key);
	void remove(K key);
	Map<K, Entry<V>> entries();
	CacheConfiguration getConfiguration();
	void evict(K key);
}
