package com.rodrigorar.biplane.eviction;

import com.rodrigorar.biplane.cache.InternalCache;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Timer;

public class EvictionExecutioner {
	public static final Duration DEFAULT_PERIOD = Duration.of(5, ChronoUnit.MINUTES);

	private static EvictionExecutioner _instance;
	public Timer _timer;
	public EvictionTask _task;
	public boolean _isRunning;

	private EvictionExecutioner() {
		_timer = new Timer();
		_isRunning = false;
	}

	public static EvictionExecutioner getInstance() {
		if (_instance == null) {
			_instance = new EvictionExecutioner();
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