package edu.alexey.algos.sort;

/**
 * Сортировка выбором.
 * 
 * Не является стабильной, поскольку порядок равнозначных элементов может
 * нарушится в момент очередного обмена ячейки, следующей после обработанной
 * левой части массива, с ячейкой, соответствующей минимуму ещё необработанной
 * правой части (т.е. по сути со случайной ячейкой, в общем случае).
 */
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
