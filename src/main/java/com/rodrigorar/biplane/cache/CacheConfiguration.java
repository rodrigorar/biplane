package com.rodrigorar.biplane.cache;

import com.rodrigorar.biplane.eviction.Policy;

import java.util.Optional;

public interface CacheConfiguration<V> {
	Optional<Policy<V>> getEvictionPolicy();
	Optional<Integer> getMaxEntries();
}
