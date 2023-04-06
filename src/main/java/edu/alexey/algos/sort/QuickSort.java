package edu.alexey.algos.sort;

public class QuickSort implements Sort {

	public void sort(int[] array) {
		quickSortV1(array, 0, array.length - 1);
	}

	private static void quickSortV1(int[] array, int startIndex, int endIndex) {
		int pivotIndex = (startIndex >> 1) + (endIndex >> 1);
		int pivotValue = array[pivotIndex];

		int leftIndex = startIndex;
		int rightIndex = endIndex;
		do {
			while (array[leftIndex] < pivotValue) {
				++leftIndex;
			}
			while (array[rightIndex] > pivotValue) {
				--rightIndex;
			}
			if (leftIndex <= rightIndex) {
				Sort.compareExchange(array, leftIndex, rightIndex);
				++leftIndex;
				--rightIndex;
			}
		} while (leftIndex <= rightIndex);

		if (leftIndex < endIndex) {
			quickSortV1(array, leftIndex, endIndex);
		}
		if (rightIndex > startIndex) {
			quickSortV1(array, startIndex, rightIndex);
		}
	}

	// private static void quickSort(int[] array, int l, int r) {
	// if (r <= l)
	// return;

	// int i = partitionLeftPivotVersion(array, l, r);
	// quickSort(array, l, i - 1);
	// quickSort(array, i + 1, r);
	// }

	// private static int partitionLeftPivotVersion(int[] array, int l, int r) {
	// assert r > l;

	// int pivotValue = array[r];
	// int i = l;
	// int j = r - 1;
	// for (;;) {

	// while (array[i] < pivotValue) {
	// ++i;
	// }
	// while (j > l && array[j] > pivotValue) {
	// --j;
	// }
	// if (i >= j) {
	// break;
	// }
	// Sort.exchange(array, i, j);
	// ++i;
	// --j;
	// }

	// Sort.exchange(array, r, i);
	// return i;
	// }

	// private static int partitionRightPivotVersion(int[] array, int l, int r) {
	// int v = array[l];
	// int i = l;
	// int j = r + 1;
	// for (;;) {

	// while (array[++i] < v) {
	// if (i == r)
	// break;
	// }
	// while (array[--j] > v) {
	// if (j == l)
	// break;
	// }
	// if (i >= j)
	// break;
	// Sort.exchange(array, i, j);
	// }
	// Sort.exchange(array, l, j);
	// return j;
	// }

	// public static void main(String[] args) {
	// int[] array = { 5, 1, 4, 7, 1, 3, 2, 1, 9, 8 };
	// System.out.println(java.util.Arrays.toString(array));

	// int i = partitionLeftPivotVersion(array, 0, array.length - 1);

	// System.out.println(java.util.Arrays.toString(array));
	// System.out.println(i);
	// }
}
