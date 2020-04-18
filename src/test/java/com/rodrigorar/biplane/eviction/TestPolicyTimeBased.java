package com.rodrigorar.biplane.eviction;

import com.rodrigorar.biplane.cache.Entry;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class TestPolicyTimeBased {
	static final Duration VALID_DURATION = Duration.of(10, ChronoUnit.MINUTES);
	static final Duration INVALID_DURATION = Duration.of(1, ChronoUnit.MILLIS);
	static final Entry<String> ENTRY_1 = new Entry("some-test-value");
	static final Entry<String> ENTRY_NULL = null;

	@Test
	public void testValid() {
		PolicyTimeBased<String> underTest = new PolicyTimeBased<>(VALID_DURATION);
		boolean result = underTest.evaluate(ENTRY_1);
		Assert.assertTrue(result);
	}

	@Test
	public void testInvalid() throws InterruptedException {
		PolicyTimeBased<String> underTest = new PolicyTimeBased<>(INVALID_DURATION);
		Thread.sleep(10);
		boolean result = underTest.evaluate(ENTRY_1);
		Assert.assertFalse(result);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testNullValue() {
		PolicyTimeBased<String> underTest = new PolicyTimeBased<>(VALID_DURATION);
		underTest.evaluate(ENTRY_NULL);
	}
}
