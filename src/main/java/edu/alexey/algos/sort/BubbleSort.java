package edu.alexey.algos.sort;

public class BubbleSort implements Sort {

	public void sort(int[] array) {
		boolean anySwap;
		do {
			anySwap = false;
			for (int i = 0; i < array.length - 1; ++i) {
				if (array[i] > array[i + 1]) {
					Sort.exchange(array, i, i + 1);
					anySwap = true;
				}
			}
		} while (anySwap);
	}
}
