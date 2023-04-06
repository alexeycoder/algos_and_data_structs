package edu.alexey.algos.sort;

public class QuickSortBasicCenterV3 implements Sort {
	@Override
	public void sort(int[] array) {
		quickSort(array, 0, array.length - 1);
	}

	private static void quickSort(int[] array, int l, int r) {
		if (l >= r)
			return;

		int q = partition(array, l, r);
		quickSort(array, l, q - 1);
		quickSort(array, q + 1, r);
	}

	private static int partition(int[] array, int l, int r) {

		int pivotIndex = (l >> 1) + (r >> 1);
		int pivotValue = array[pivotIndex];

		int i = l;
		int j = r;

		for (;;) {
			while (i < pivotIndex && array[i] <= pivotValue) {
				++i;
			}
			while (j > pivotIndex && array[j] >= pivotValue) {
				--j;
			}

			if (i == j) {
				if (i != pivotIndex) {
					throw new RuntimeException("Inconsistent behavior.");
				}
				return pivotIndex;
			}

			Sort.exchange(array, i, j);

			if (i == pivotIndex) {
				pivotIndex = j;
				++i;
			} else if (j == pivotIndex) {
				pivotIndex = i;
				--j;
			} else {
				++i;
				--j;
			}
		}
	}
}
