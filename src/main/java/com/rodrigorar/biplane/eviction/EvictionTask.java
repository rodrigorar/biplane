package com.rodrigorar.biplane.eviction;

import com.rodrigorar.biplane.cache.InternalCache;
import com.rodrigorar.biplane.utils.Validator;

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
