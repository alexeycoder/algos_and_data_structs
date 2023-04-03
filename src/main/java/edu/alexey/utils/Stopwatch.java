package edu.alexey.utils;

/**
 * A class to help benchmark code.
 */
public class Stopwatch {
	private long startTime = -1;
	private long stopTime = -1;
	private boolean running = false;

	public Stopwatch start() {
		running = true;
		startTime = System.nanoTime();
		return this;
	}

	public Stopwatch stop() {
		stopTime = System.nanoTime();
		running = false;
		return this;
	}

	/**
	 * Returns elapsed time in nanoseconds
	 * if the watch has never been started then
	 * return zero
	 */
	public long getElapsedTime() {
		if (startTime == -1) {
			return 0;
		}
		if (running) {
			return System.nanoTime() - startTime;
		} else {
			return stopTime - startTime;
		}
	}

	public Stopwatch reset() {
		startTime = -1;
		stopTime = -1;
		running = false;
		return this;
	}
}
