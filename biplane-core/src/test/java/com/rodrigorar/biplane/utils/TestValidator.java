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

package com.rodrigorar.biplane.utils;

import org.junit.Assert;
import org.junit.Test;

public class TestValidator {

	// isNotNull

	@Test
	public void isNotNullSuccess() {
		String value = "";
		Validator.isNotNull(value);
	}

	@Test (expected = IllegalArgumentException.class)
	public void isNotNullWithNullValue() {
		Validator.isNotNull(null);
	}

	// isValidArgument

	@Test
	public void isValidArgumentSuccess() {
		Validator.isValidArgument(true);
	}

	@Test (expected = IllegalArgumentException.class)
	public void isValidArgumentFailure() {
		Validator.isValidArgument(false);
	}

	// validateOrDefault

	@Test
	public void validateOrDefaultSuccess() {
		String value = "value";
		String defaultValue = "default";

		String result = Validator.validateOrDefault(value, defaultValue);

		Assert.assertEquals(value, result);
	}

	@Test
	public void validateOrDefaultRetDefault() {
		String value = null;
		String defaultValue = "default";

		String result = Validator.validateOrDefault(value, defaultValue);

		Assert.assertEquals(defaultValue, result);
	}

}
