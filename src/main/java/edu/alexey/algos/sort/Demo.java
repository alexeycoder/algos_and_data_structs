package edu.alexey.algos.sort;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalLong;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import edu.alexey.utils.Stopwatch;

public class Demo {
	static final int WARM_ROUNDS = 1000;
	static final int TOTAL_ROUNDS = WARM_ROUNDS + 1000;
	static final int MIN_VALUE = 0;
	static final int MAX_VALUE = 999;

	static final int[] SIZES = {
			10_000,
			20_000,
			30_000,
			40_000,
			50_000,
			60_000,
			70_000,
			80_000,
			90_000,
			100_000,
			// 1_000_000
	};

	static final List<Sort> ALGOS = List.of(
			new Sort() {
				@Override
				public void sort(int[] array) {
					Arrays.sort(array);
				}

				@Override
				public String toString() {
					return "Java Sort";
				}
			},
			new QuickSortBasicCenterV3b(),
			new QuickSortBasicCenterV3(),
			// new QuickSortBasicCenter(),
			new QuickSortBasicRandom(),
			new QuickSort(),
			new InsertionSort(),
			new ShellSort(),
			new HeapSort(),
			new SelectionSort(),
			new BubbleSort());

	public static void main(String[] args) {
		// int maxSizeLen = Arrays.stream(SIZES).map(s ->
		// Integer.toString(s).length()).max().getAsInt();

		System.out.println("\nTEST RESULTS:");

		for (var algo : ALGOS) {
			var name = algo.getClass().getSimpleName();
			if (name.isEmpty()) {
				name = algo.toString();
			}
			System.out.println("\nSorting algo is " + name);
			System.out.println();
			System.out.println("Random input:");
			for (int n : SIZES) {
				var elapsedTimeNsOpt = Estimate(algo, Demo::getRandomArray, n);
				printResult(elapsedTimeNsOpt, n);
			}
			System.out.println("Sorted asc input:");
			for (int n : SIZES) {
				var elapsedTimeNsOpt = Estimate(algo, Demo::getSortedAscArray, n);
				printResult(elapsedTimeNsOpt, n);
			}
			System.out.println("Sorted desc input:");
			for (int n : SIZES) {
				var elapsedTimeNsOpt = Estimate(algo, Demo::getSortedDescArray, n);
				printResult(elapsedTimeNsOpt, n);
			}
			System.out.println("Uniform array input:");
			for (int n : SIZES) {
				var elapsedTimeNsOpt = Estimate(algo, Demo::getUniformArray, n);
				printResult(elapsedTimeNsOpt, n);
			}
		}
	}

	private static void printResult(OptionalLong elapsedTimeNsOpt, int n) {
		if (elapsedTimeNsOpt.isPresent()) {
			System.out.printf("N = %-6d : ", n);
			System.out.println("Avg Elapsed Time = " + getTimeStr(elapsedTimeNsOpt.getAsLong()));
		} else {
			printError();
		}
	}

	private static OptionalLong Estimate(Sort algo, IntFunction<int[]> arrayProducer, int n) {
		long totalTime = 0;
		int count = 0;
		Stopwatch stopwatch = new Stopwatch();

		for (int rounds = 1; rounds <= TOTAL_ROUNDS; ++rounds) {

			int[] array = arrayProducer.apply(n);
			int arrayLen = array.length;
			int controlSum = Arrays.stream(array).sum();

			stopwatch.start();
			algo.sort(array);
			stopwatch.stop();

			if (rounds > WARM_ROUNDS) {
				totalTime += stopwatch.getElapsedTime();
				++count;
			}

			int checkSum = Arrays.stream(array).sum();
			if (array.length != arrayLen
					|| !Sort.checkIfSorted(array)
					|| checkSum != controlSum) {
				return OptionalLong.empty();
			}
		}

		if (count == 0) {
			return OptionalLong.empty();
		}

		long nanoseconds = totalTime / count;
		return OptionalLong.of(nanoseconds);
	}

	private static int[] getRandomArray(int size) {
		return new Random().ints(MIN_VALUE, MAX_VALUE + 1).limit(size).toArray();
	}

	private static int[] getSortedAscArray(int size) {
		AtomicInteger counter = new AtomicInteger(MIN_VALUE);
		return IntStream.generate(counter::incrementAndGet).limit(size).toArray();
	}

	private static int[] getSortedDescArray(int size) {
		AtomicInteger counterDsc = new AtomicInteger(MIN_VALUE + size - 1);
		return IntStream.generate(counterDsc::decrementAndGet).limit(size).toArray();
	}

	private static int[] getUniformArray(int size) {
		int[] arr = new int[size];
		Arrays.fill(arr, MIN_VALUE);
		return arr;
	}

	private static String getTimeStr(long nanoseconds) {
		var seconds = TimeUnit.SECONDS.convert(nanoseconds, TimeUnit.NANOSECONDS);
		long dsec = TimeUnit.NANOSECONDS.convert(seconds, TimeUnit.SECONDS);

		var millis = TimeUnit.MILLISECONDS.convert(nanoseconds - dsec, TimeUnit.NANOSECONDS);
		long dmillis = TimeUnit.NANOSECONDS.convert(millis, TimeUnit.MILLISECONDS);

		var micros = TimeUnit.MICROSECONDS.convert(nanoseconds - dsec - dmillis, TimeUnit.NANOSECONDS);

		String res = String.format("%02d sec %03d ms %03d \u00b5s = %,d ns in total",
				seconds, millis, micros, nanoseconds);
		return res;
	}

	private static void printError() {
		System.err.println("ERROR: Inconsistent result of the algorithm work!");
	}
}
