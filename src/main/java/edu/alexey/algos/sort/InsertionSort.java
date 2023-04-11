package edu.alexey.algos.sort;

/**
 * Сортировка вставками.
 * 
 * Принцип: Массив разделён на 2 части: левая -- упорядочена, правая -- нет.
 * На каждом шаге: берём первый элемент правой части, вставляем его на
 * подходящее место в левой части.
 * 
 * Является стабильной сортировкой, поскольку, в отличие от сортировки выбором,
 * мы не бегаем по всей правой части в поисках очередного минимального элемента,
 * а берём ячейку, следующую непосредственно за левой частью,
 * т.е. первую ячейку текущей ещё неотсортированной правой части, и вставляем её
 * в подходящее место в левой части. Т.о. порядок равнозначных элементов
 * сохраняется.
 */
public class InsertionSort implements Sort {

	@Override
	public void sort(int[] array) {
		insertionSortV3(array, 0, array.length - 1);
	}

	// private static void insertionSortV1(int[] array) {
	// for (int i = 0; i < array.length - 1; ++i) {
	// for (int j = i + 1; j < array.length; ++j) {
	// if (array[i] > array[j]) {
	// Sort.exchange(array, i, j);
	// }
	// }
	// }
	// }

	// private static void insertionSortV2(int[] array, int l, int r) {
	// for (int i = l + 1; i <= r; ++i) {
	// for (int j = i; j > l; --j) {
	// Sort.compareExchange(array, j - 1, j);
	// }
	// }
	// }

	// private static void insertionSortV2Opt(int[] array, int l, int r) {
	// for (int i = l + 1; i <= r; ++i) {
	// for (int j = i; j > l; --j) {
	// if (array[j - 1] <= array[j]) {
	// break;
	// }
	// Sort.exchange(array, j - 1, j);
	// }
	// }
	// }

	private static void insertionSortV3(int[] array, int l, int r) {
		// move min to the most left [0] position - sentinel key
		for (int i = r; i > l; --i) {
			Sort.compareExchange(array, i - 1, i);
		}

		for (int i = l + 2; i <= r; ++i) {
			int j = i;
			int v = array[i];
			while (v < array[j - 1]) {
				array[j] = array[j - 1];
				--j;
			}
			array[j] = v;
		}
	}
}
