package edu.alexey.algos.sort;

public class SelectionSort implements Sort {

	@Override
	public void sort(int[] array) {
		for (int i = 0; i < array.length - 1; ++i) {
			int minPosition = i;
			for (int j = i + 1; j < array.length; ++j) {
				if (array[j] < array[minPosition]) {
					minPosition = j;
				}
			}
			if (i != minPosition) {
				Sort.exchange(array, i, minPosition);
			}
		}
	}

}
