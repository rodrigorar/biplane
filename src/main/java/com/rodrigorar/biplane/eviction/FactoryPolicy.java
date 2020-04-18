package com.rodrigorar.biplane.eviction;

import java.time.Duration;

public class FactoryPolicy {

	public static <V> Policy<V> timeBased(Duration timeToLive) {
		return new PolicyTimeBased<>(timeToLive);
	}
}
