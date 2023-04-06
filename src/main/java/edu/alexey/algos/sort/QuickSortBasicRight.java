package edu.alexey.algos.sort;

public class QuickSortBasicRight implements Sort {

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
