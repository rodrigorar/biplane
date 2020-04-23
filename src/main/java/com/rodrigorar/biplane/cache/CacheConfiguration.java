package com.rodrigorar.biplane.cache;

import com.rodrigorar.biplane.eviction.Policy;

public interface CacheConfiguration<K, V> {
	Policy<V> getEvictionPolicy();
	int getMaxEntries();
	<C extends CacheConfiguration> C subCast();
}
