package edu.alexey.algos.sort;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import edu.alexey.utils.Stopwatch;

public class Demo {
	static final int ARRAY_SIZE = 999999;
	static final int WARM_ROUNDS = 100;
	static final int TOTAL_ROUNDS = WARM_ROUNDS + 100;

	public static void main(String[] args) {
		long totalTime = 0;
		int count = 0;

		for (int rounds = 1; rounds <= TOTAL_ROUNDS; ++rounds) {

			// AtomicInteger counter = new AtomicInteger();
			// int[] array = IntStream.generate(counter::incrementAndGet)
			// 		.limit(ARRAY_SIZE).toArray();
			// AtomicInteger counterDsc = new AtomicInteger(ARRAY_SIZE);
			// int[] array = IntStream.generate(counterDsc::decrementAndGet)
			// 		.limit(ARRAY_SIZE).toArray();
			int[] array = new Random().ints(-999, 999).limit(ARRAY_SIZE).toArray();

			Stopwatch stopwatch = new Stopwatch();
			stopwatch.start();

			// new BubbleSort().sort(array);
			// new SelectionSort().sort(array);
			// new InsertionSort().sort(array);
			// new QuickSort().sort(array);
			new HeapSort().sort(array);
			// new ShellSort().sort(array);

			stopwatch.stop();
			if (rounds >= WARM_ROUNDS) {
				totalTime += stopwatch.getElapsedTime();
				++count;
			}

			if (array.length != ARRAY_SIZE || !Sort.checkIfSorted(array)) {
				System.err.println("\nERROR: Sorting is Bad!");
				return;
			}
		}

		System.out.println("\nTEST RESULTS:");
		long elapsedTimeNs = totalTime / count;
		System.out.printf("Elapsed time (nanoseconds) = %,d\n", elapsedTimeNs);

		var seconds = TimeUnit.SECONDS.convert(elapsedTimeNs, TimeUnit.NANOSECONDS);
		long dsec = TimeUnit.NANOSECONDS.convert(seconds, TimeUnit.SECONDS);

		var millis = TimeUnit.MILLISECONDS.convert(elapsedTimeNs - dsec, TimeUnit.NANOSECONDS);
		long dmillis = TimeUnit.NANOSECONDS.convert(millis, TimeUnit.MILLISECONDS);

		var micros = TimeUnit.MICROSECONDS.convert(elapsedTimeNs - dsec - dmillis, TimeUnit.NANOSECONDS);

		System.out.printf(" %d sec %d ms %d \u00b5s\n", seconds, millis, micros);
	}
}
