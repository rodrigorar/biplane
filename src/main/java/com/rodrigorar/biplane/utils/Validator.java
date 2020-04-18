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

public class Validator {

	public static void isNotNull(Object value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
	}

	public static void isValidArgument(boolean evaluation) {
		if (! evaluation) {
			throw new IllegalArgumentException();
		}
	}
}
