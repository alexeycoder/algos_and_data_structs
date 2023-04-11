package edu.alexey.algos.various;

import java.util.ArrayList;
import java.util.List;

public class Demo {

	public static void main(String[] args) {
		List<Integer> res = findSimpleNumbers(50);
		res.stream().forEach(System.out::println);
	}

	public static List<Integer> findAvailableDivider(int number) {
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < number; ++i) {
			if (number % i == 0) {
				result.add(i);
			}
		}
		return result;
	}

	public static List<Integer> findSimpleNumbers(int max) {
		int count = 0;
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i <= max; ++i) {
			boolean simple = true;
			for (int j = 2; j < i; ++j) {
				++count;
				if (i % j == 0) {
					simple = false;
				}
			}
			if (simple) {
				result.add(i);
			}
		}

		System.out.println("Count of findSimpleNumbers() is " + count);
		return result;
	}

	public static double findSum(int sum) {
		int count = 0;
		int successResult = 0;
		for (int i = 1; i <= 6; i++) {
			for (int j = 1; i <= 6; j++) {
				for (int k = 1; i <= 6; k++) {
					if (i + j + k == sum) {
						successResult++;
					}
					count++;
				}
			}
		}
		return ((double) successResult) / ((double) count);

		
	}

}
