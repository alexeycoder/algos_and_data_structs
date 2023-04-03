package edu.alexey.algos.sort;

public class QuickSort implements Sort {

	public void sort(int[] array) {
		quickSort(array, 0, array.length - 1);
	}

	private static void quickSort(int[] array, int startIndex, int endIndex) {
		int pivotIndex = (startIndex + endIndex) >> 1;
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
			quickSort(array, leftIndex, endIndex);
		}
		if (rightIndex > startIndex) {
			quickSort(array, startIndex, rightIndex);
		}
	}
}
