package edu.alexey.algos.tree;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class Heap<T> {

	final Comparator<T> comparator;

	final T[] array;
	int heapSize;
	int lastLeafIndex;

	public Heap(Comparator<T> comparator, T[] array) {
		Objects.requireNonNull(comparator);
		this.comparator = comparator;

		this.array = Arrays.copyOf(array, array.length);
		this.heapSize = this.array.length;
		this.lastLeafIndex = lastLeafIndex(this.heapSize);

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
			int parentIndex = parentIndex(index);
			T parentValue = array[parentIndex];
			T value = array[index];
			if (comparator.compare(value, parentValue) <= 0) {
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

			T value = array[index];

			int leftIndex = leftChildIndex(index);
			T leftValue = array[leftIndex];

			int largestIndex = index;
			swap = false;

			if (comparator.compare(value, leftValue) < 0) {
				largestIndex = leftIndex;
				swap = true;
			}
			// то что узел не является листом гарантирует лишь существование левого
			// дочернего, но не гарантирует существование правого
			int rightIndex = rightChildIndex(index);
			if (rightIndex < heapSize) {
				T rightValue = array[rightIndex];
				if (comparator.compare(value, rightValue) < 0) {
					if (swap) {
						if (comparator.compare(rightValue, leftValue) > 0) {
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

	public T extractMax() {
		if (heapSize <= 0) {
			return null;
		}

		// Запоминаем значение корня
		T result = array[0];
		// Переносим последний элемент кучи в корень
		array[0] = array[heapSize - 1];
		// Дополнительно запоминаем максимум в последний элемент кучи,
		// что позволит получить отсортированный по возрастанию массив
		// по окончании экстракции поочерёдно всех элементов кучи
		array[heapSize - 1] = result;
		// Удаляем (забываем) последний элемент
		--heapSize;
		lastLeafIndex = lastLeafIndex(heapSize);
		// Делаем siftDown для нового значения корня
		if (heapSize > 0) {
			siftDown(0);
		}

		return result;
	}

	boolean isLeaf(int index) {
		return index >= lastLeafIndex;
	}

	static int leftChildIndex(int index) {
		return 1 + (index << 1);
	}

	static int rightChildIndex(int index) {
		return 2 + (index << 1);
	}

	static int parentIndex(int index) {
		return (index - 1) >> 1;
	}

	static int lastLeafIndex(int size) {
		return size >> 1;
	}

	@Override
	public String toString() {
		return Heap.class.getSimpleName() + ":" + Arrays.toString(array);
	}

}
