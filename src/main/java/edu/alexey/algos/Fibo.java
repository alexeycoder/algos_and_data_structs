package edu.alexey.algos;

import java.util.concurrent.atomic.AtomicInteger;

public class Fibo {

	public static void main(String[] args) {
		AtomicInteger counter;
		int n;
		System.out.printf("Fib(%d) = %d\tCount = %d\n", n = 10, fib(n, counter = new AtomicInteger()), counter.get());
		System.out.printf("Fib(%d) = %d\tCount = %d\n", n = 11, fib(n, counter = new AtomicInteger()), counter.get());
		System.out.printf("Fib(%d) = %d\tCount = %d\n", n = 12, fib(n, counter = new AtomicInteger()), counter.get());
	}

	public static int fib(int position, AtomicInteger counter) {
		counter.incrementAndGet();
		if (position == 1) {
			return 0;
		} else if (position == 2) {
			return 1;
		}
		return fib(position - 1, counter) + fib(position - 2, counter);
	}
}
