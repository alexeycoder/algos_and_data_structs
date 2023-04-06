package edu.alexey.algos.sort;

public class HeapSort implements Sort {

	public void sort(int[] array) {
		for (int i = (array.length >> 1) - 1; i >= 0; --i) {
			heapify(array, array.length, i);
		}

		for (int i = array.length - 1; i >= 0; --i) {
			int temp = array[0];
			array[0] = array[i];
			array[i] = temp;

			heapify(array, i, 0);
		}
	}

	private static void heapify(int[] array, int heapSize, int rootIndex) {
		int largestItemIndex = rootIndex;
		int leftChildIndex = (rootIndex << 1) + 1; // 2 * rootIndex + 1;
		int rightChildIndex = leftChildIndex + 1; // 2 * rootIndex + 2;

		if (leftChildIndex < heapSize && array[leftChildIndex] > array[largestItemIndex]) {
			largestItemIndex = leftChildIndex;
		}
		if (rightChildIndex < heapSize && array[rightChildIndex] > array[largestItemIndex]) {
			largestItemIndex = rightChildIndex;
		}
		if (largestItemIndex != rootIndex) {
			int temp = array[rootIndex];
			array[rootIndex] = array[largestItemIndex];
			array[largestItemIndex] = temp;

			heapify(array, heapSize, largestItemIndex);
		}
	}

}
