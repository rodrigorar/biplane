package com.rodrigorar.biplane.core.cache;

import com.rodrigorar.biplane.core.eviction.Policy;

public interface CacheConfiguration<K, V> {
	Policy<V> getEvictionPolicy();
	int getMaxEntries();
	<C extends CacheConfiguration> C subCast();
}
