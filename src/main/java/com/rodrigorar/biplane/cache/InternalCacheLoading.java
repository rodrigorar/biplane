package com.rodrigorar.biplane.cache;

import com.rodrigorar.biplane.eviction.Policy;
import com.rodrigorar.biplane.utils.Validator;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class InternalCacheLoading<K, V> implements InternalCache<K, V> {
	private final Map<K, Entry<V>> _entryMap;
	private final CacheConfiguration _configuration;
	private final Function<K, V> _cacheLoader;

	InternalCacheLoading(CacheConfiguration configuration, Function<K, V> cacheLoader) {
		_entryMap = new ConcurrentHashMap<>();
		_configuration = configuration;
		_cacheLoader = cacheLoader;
	}

	@Override
	public void put(K key, Entry<V> value) {
		Validator.isNotNull(key);
		Validator.isNotNull(value);

		_entryMap.put(key, value);
	}

	@Override
	public Optional<Entry<V>> get(K key) {
		Validator.isNotNull(key);

		if (! _entryMap.containsKey(key)) {
			V loadedValue = _cacheLoader.apply(key);
			if (loadedValue != null) {
				_entryMap.put(key, new Entry<>(loadedValue));
			}
		}

		return Optional.ofNullable(_entryMap.get(key));
	}

	@Override
	public void remove(K key) {
		Validator.isNotNull(key);

		if (_entryMap.containsKey(key)) {
			_entryMap.remove(key);
		}
	}

	@Override
	public void evict() {
		Policy<V> evictionPolicy = _configuration.getEvictionPolicy();
		_entryMap.forEach((k, v) -> {
			if (evictionPolicy.evaluate(v)) {
				_entryMap.remove(k);
			}
		});
	}

	@Override
	public Map<K, Entry<V>> entries() {
		return _entryMap;
	}

	@Override
	public CacheConfiguration getConfiguration() {
		return _configuration;
	}
}
