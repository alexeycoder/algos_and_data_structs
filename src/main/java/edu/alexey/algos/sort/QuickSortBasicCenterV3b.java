package edu.alexey.algos.sort;

public class QuickSortBasicCenterV3b implements Sort {
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
			int updPivotIndex = pivotIndex;
			int di = 1;
			int dj = -1;
			boolean movePivotIndexToJ = false;

			for (;;) {
				if (i < pivotIndex) {
					if (array[i] <= pivotValue) {
						++i;
					} else {
						break;
					}
				} else {
					// means i == pivotIndex
					updPivotIndex = j;
					movePivotIndexToJ = true;
					dj = 0;
					break;
				}
			}

			for (;;) {
				if (j > pivotIndex) {
					if (array[j] >= pivotValue) {
						--j;
					} else {
						if (movePivotIndexToJ) {
							updPivotIndex = j;
						}
						break;
					}
				} else {
					// means j == pivotIndex
					updPivotIndex = i;
					di = 0;
					break;
				}
			}

			if (i == j) {
				if (i != pivotIndex) {
					throw new RuntimeException("Inconsistent behavior.");
				}
				return pivotIndex;
			}

			Sort.exchange(array, i, j);
			pivotIndex = updPivotIndex;
			i += di;
			j += dj;
		}
	}
}
