package edu.alexey.algos.tree;

import java.util.NoSuchElementException;

public class IntHeap {
	final int[] array;
	int heapSize;
	int lastLeafIndex;

	public IntHeap(int[] array) {
		this.array = array; // Arrays.copyOf(array, array.length);
		this.heapSize = this.array.length;
		this.lastLeafIndex = Heap.lastLeafIndex(this.heapSize);

		buildMaxHeap();
	}

	public int size() {
		return heapSize;
	}

	/**
	 * Перестраивает массив в невозрастающую пирамиду (кучу).
	 */
	void buildMaxHeap() {
		for (int i = lastLeafIndex - 1; i >= 0; --i) {
			siftDown(i);
		}
	}

	/**
	 * Просеивание вверх: сравнивает элемент с родительским и, в случае если он
	 * больше родительского, меняется с ним местами (поднимается выше).
	 * 
	 * Используется при добавлении нового элемента в конец для восстановления
	 * свойств кучи.
	 */
	void siftUp(int index) {
		assert index >= 0 && index < heapSize;

		while (index > 0) {
			int parentIndex = Heap.parentIndex(index);
			int parentValue = array[parentIndex];
			int value = array[index];
			if (value <= parentValue) {
				return;
			}
			array[index] = parentValue;
			array[parentIndex] = value;
			index = parentIndex;
		}
	}

	/**
	 * Просеивание вниз: сравнивает элемент с дочерними и, в случае если он
	 * меньше какого-либо из них, меняется с наибольшим из них местами
	 * (спускается ниже).
	 */
	void siftDown(int index) {
		assert index >= 0 && index < heapSize;

		boolean swap;
		do {
			if (isLeaf(index)) {
				return;
			}

			int value = array[index];

			int leftIndex = Heap.leftChildIndex(index);
			int leftValue = array[leftIndex];

			int largestIndex = index;
			swap = false;

			if (value < leftValue) {
				largestIndex = leftIndex;
				swap = true;
			}
			// то что узел не является листом гарантирует лишь существование левого
			// дочернего, но не гарантирует существование правого
			int rightIndex = Heap.rightChildIndex(index);
			if (rightIndex < heapSize) {
				int rightValue = array[rightIndex];
				if (value < rightValue) {
					if (swap) {
						if (rightValue > leftValue) {
							largestIndex = rightIndex;
						}
					} else {
						largestIndex = rightIndex;
						swap = true;
					}
				}
			}

			if (swap) {
				array[index] = array[largestIndex];
				array[largestIndex] = value;

				// siftDown(largestIndex); // recursive way
				index = largestIndex; // non-recursive way -- do-while
			}
		} while (swap);
	}

	public int extractMax() {
		if (heapSize <= 0) {
			throw new NoSuchElementException();
		}

		// Запоминаем значение корня
		int result = array[0];
		// Переносим последний элемент кучи в корень
		array[0] = array[heapSize - 1];
		// Дополнительно запоминаем максимум в последний элемент кучи,
		// что позволит получить отсортированный по возрастанию массив
		// по окончании экстракции поочерёдно всех элементов кучи
		array[heapSize - 1] = result;
		// Удаляем (забываем) последний элемент
		--heapSize;
		lastLeafIndex = Heap.lastLeafIndex(heapSize);
		// Делаем siftDown для нового значения корня
		if (heapSize > 0) {
			siftDown(0);
		}

		return result;
	}

	boolean isLeaf(int index) {
		return index >= lastLeafIndex;
	}
}
