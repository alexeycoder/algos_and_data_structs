package edu.alexey.algos.sort;

import java.util.Arrays;
import java.util.Random;

public class Demo {
	public static void main(String[] args) {
		int[] array = new Random().ints(0, 99).limit(100).toArray();

		System.out.println(Arrays.toString(array));
		// new BubbleSort().sort(array);
		// new SelectionSort().sort(array);
		// new InsertionSort().sort(array);
		// new QuickSort().sort(array);
		// new HeapSort().sort(array);
		new ShellSort().sort(array);
		System.out.println(Arrays.toString(array));
		System.out.println("Sorting is " + (Sort.checkIfSorted(array) ? "OK" : "Bad!"));
	}
}
