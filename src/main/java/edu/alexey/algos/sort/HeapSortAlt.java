package edu.alexey.algos.sort;

import edu.alexey.algos.tree.IntHeap;

/**
 * Пирамидальная сортировка (сортировка кучей).
 * 
 * O(n log n)
 * Не является стабильной.
 */
public class HeapSortAlt implements Sort {

	@Override
	public void sort(int[] array) {
		var heap = new IntHeap(array);
		while (heap.size() > 0) {
			heap.extractMax();
		}
	}

}
