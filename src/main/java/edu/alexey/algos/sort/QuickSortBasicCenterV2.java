package edu.alexey.algos.sort;

import java.util.Arrays;

public class QuickSortBasicCenterV2 implements Sort {
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
		Sort.exchange(array, r, pivotIndex);

		int i = l;
		int j = r - 1;

		do {
			while (array[i] < pivotValue) {
				++i;
			}
			while (j > i && array[j] >= pivotValue) {
				--j;
			}

			if (i < j) {
				Sort.exchange(array, i, j);
				++i;
				--j;
			}

			if (i >= j) {
				if (array[j] > pivotValue) {
					Sort.exchange(array, j, r);
					pivotIndex = j;
				} else {
					pivotIndex = j + 1;
					Sort.exchange(array, pivotIndex, r);
				}
				return pivotIndex;
			}

		} while (true);
	}

	private static int partitionDebug(int[] array, int l, int r) {

		int pivotIndex = (l >> 1) + (r >> 1);
		int pivotValue = array[pivotIndex];
		if (debug) {
			System.err.println("Start: PV=" + pivotValue + " PI=" + pivotIndex);
			System.err.println(Arrays.toString(array));
		}

		Sort.exchange(array, r, pivotIndex);

		int i = l;
		int j = r - 1;
		if (debug) {
			System.err.println("Store Pivot:");
			System.err.println(Arrays.toString(array));
			System.err.println(i + " / " + j);
		}
		do {
			while (array[i] < pivotValue) {
				++i;

				if (debug)
					System.err.println(i + " / " + j);
			}
			while (j > i && array[j] >= pivotValue) {
				--j;

				if (debug)
					System.err.println(i + " / " + j);
				// if (i >= j) {
				// break;
				// }
			}

			if (i < j) {
				Sort.exchange(array, i, j);
				++i;
				--j;

				if (debug) {
					System.err.println("After regular exchange:");
					System.err.println(Arrays.toString(array));
					System.err.println(i + " / " + j);
				}
			}

			if (i >= j) {

				if (debug) {
					System.err.println("I meet or crossover J:");
					System.err.println(Arrays.toString(array));
					System.err.println(i + " / " + j);
				}

				if (array[j] > pivotValue) {
					Sort.exchange(array, j, r);
					pivotIndex = j;
				} else {
					pivotIndex = j + 1;
					Sort.exchange(array, pivotIndex, r);
				}

				if (debug) {
					System.err.println("Before return:");
					System.err.println(Arrays.toString(array));
					System.err.println(i + " / " + j);
				}

				return pivotIndex;
			}

		} while (true);
	}

	private static boolean debug = false;

	public static void main(String[] args) {

		// int[] arr = { 1, 2, 0, 0, 1, 0, 2, 4, 8, 8 };
		// int[] arr = { 9, 3, 5, 8, 6, 3, 3, 8, 5, 2 };
		// int[] arr = { 9, 1, 1, 3, 9, 6, 9, 5, 8, 9 };
		// int[] arr = { 5, 5, 5, 1, 9, 5, 8, 3, 7, 8 };
		// int[] arr = { 4, 3, 0, 0, 7, 7, 4, 5, 0, 7 };
		// int[] arr = { 3, 0, 0, 3, 7, 9, 8, 5, 9, 6 };
		// int q = partitionDebug(arr, 0, arr.length - 1);
		// System.out.println(q);

		findProblem();
	}

	private static void findProblem() {
		while (true) {
			int[] originalArr = new java.util.Random().ints(0, 10).limit(10).toArray();
			int[] cloneArr = originalArr.clone();

			int pvOriginal = originalArr[(originalArr.length - 1) / 2];

			int i = partitionDebug(cloneArr, 0, cloneArr.length - 1);

			boolean bad = pvOriginal != cloneArr[i];
			if (!bad) {
				for (int k = i - 1; k >= 0; --k) {
					if (cloneArr[k] > cloneArr[i]) {
						bad = true;
						break;
					}
				}
			}
			if (!bad) {
				for (int k = i + 1; k < cloneArr.length; ++k) {
					if (cloneArr[k] < cloneArr[i]) {
						bad = true;
						break;
					}
				}
			}
			if (bad) {
				System.out.println(java.util.Arrays.toString(originalArr));
				System.out.println(java.util.Arrays.toString(cloneArr));
				System.out.println("pIndex = " + i + " pValue orig = " + pvOriginal +
						" pValue res = " + cloneArr[i]);
				break;
			}
		}
	}
}
