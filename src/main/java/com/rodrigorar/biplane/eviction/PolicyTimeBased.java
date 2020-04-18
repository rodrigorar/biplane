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
