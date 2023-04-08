package edu.alexey.algos.tree;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class DemoHeap {
	public static void main(String[] args) {

		// var arr = new Integer[] { 3, 1, 3, 6, 8, 1, 6, 9, 7, 6 };
		var arr = getRandomArray(30, 0, 99);
		System.out.println(Arrays.toString(arr));

		Heap<Integer> heap = new Heap<>(Comparator.<Integer>naturalOrder(), arr);
		System.out.println(heap);

		while (heap.size() > 0) {
			System.out.print(heap.extractMax() + ", ");
		}
		System.out.println();
		System.out.println(heap);
	}

	private static Integer[] getRandomArray(int size, int min, int max) {
		return new Random().ints(min, max + 1).limit(size).boxed().toArray(Integer[]::new);
	}
}
