package edu.alexey.algos.sort;

public interface Sort {

	void sort(int[] array);

	static void exchange(int[] array, int index1, int index2) {
		var temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}

	static void compareExchange(int[] array, int indexOfSmaller, int indexOfBigger) {
		if (array[indexOfBigger] < array[indexOfSmaller]) {
			exchange(array, indexOfSmaller, indexOfBigger);
		}
	}

	static <T> void exchange(T[] array, int index1, int index2) {
		var temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}

	static <T extends Comparable<T>> void compareExchange(T[] array, int lesserIndex, int greaterIndex) {
		if (array[greaterIndex].compareTo(array[lesserIndex]) < 0) {
			exchange(array, lesserIndex, greaterIndex);
		}
	}

	static boolean checkIfSorted(int[] array) {
		for (int i = 1; i < array.length; ++i) {
			if (array[i] < array[i - 1]) {
				return false;
			}
		}
		return true;
	}
}
