package edu.alexey.algos.sort;

import java.util.concurrent.ThreadLocalRandom;

public class QuickSortBasicRandom implements Sort {
	@Override
	public void sort(int[] array) {
		randomizedQuickSort(array, 0, array.length - 1);
	}

	private static void randomizedQuickSort(int[] array, int l, int r) {
		if (l >= r)
			return;

		int q = randomizedPartition(array, l, r);
		randomizedQuickSort(array, l, q - 1);
		randomizedQuickSort(array, q + 1, r);
	}

	private static int randomizedPartition(int[] array, int l, int r) {
		int i = ThreadLocalRandom.current().nextInt(l, r + 1);
		Sort.exchange(array, r, i);
		return partition(array, l, r);
	}

	private static int partition(int[] array, int l, int r) {
		int pivotValue = array[r];
		int i = l - 1;
		for (int j = l; j <= r - 1; ++j) {
			if (array[j] <= pivotValue) {
				++i;
				Sort.exchange(array, i, j);
			}
		}
		Sort.exchange(array, i + 1, r);
		return i + 1;
	}
}
