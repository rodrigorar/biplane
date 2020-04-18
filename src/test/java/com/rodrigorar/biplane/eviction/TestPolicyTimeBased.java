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
