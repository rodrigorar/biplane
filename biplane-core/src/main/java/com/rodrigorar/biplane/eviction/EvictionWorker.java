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

import com.rodrigorar.biplane.cache.InternalCache;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Timer;

public class EvictionWorker {
	public static final Duration DEFAULT_PERIOD = Duration.of(5, ChronoUnit.MINUTES);

	private static EvictionWorker _instance;
	public Timer _timer;
	public EvictionTask _task;
	public boolean _isRunning;

	private EvictionWorker() {
		_timer = new Timer();
		_isRunning = false;
	}

	public static EvictionWorker getInstance() {
		if (_instance == null) {
			_instance = new EvictionWorker();
		}
		return _instance;
	}

	public static boolean isRunning() {
		return _instance != null && _instance._isRunning;
	}

	public void addCache(InternalCache cache) {
		start(DEFAULT_PERIOD);
		_task.addCache(cache);
	}

	public void start(Duration period) {
		if (! _isRunning) {
			_isRunning = true;
			_task = new EvictionTask();
			_timer.schedule(_task, 100, period.toMillis());
		}
	}

	public void stop() {
		if (_isRunning) {
			_timer.cancel();
			_timer = new Timer();
			_isRunning = false;
		}
	}

	public void restart(Duration period) {
		stop();
		start(period);
	}
}
