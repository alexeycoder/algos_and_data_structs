package edu.alexey.algos.search;

import java.util.Arrays;
import java.util.Random;

import edu.alexey.algos.sort.BubbleSort;

public class Search {

	public static void main(String[] args) {
		int[] array = new Random().ints(0, 100).limit(50).toArray();

		int valueToSearch = 99;
		System.out.println(Arrays.toString(array));
		System.out.printf("Simple Find:\nIndex of value %d is %d\n\n", valueToSearch, find(array, valueToSearch));

		new BubbleSort().sort(array);
		// Sort.selectionSort(array);
		System.out.println(Arrays.toString(array));
		System.out.printf("Binary Search:\nIndex of value %d is %d\n\n", valueToSearch,
				binarySearch(array, valueToSearch));

	}

	public static int find(int[] array, int value) {
		for (int i = 0; i < array.length; ++i) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}

	public static int binarySearch(int[] sortedAscArray, int value) {
		return binarySearch(sortedAscArray, value, 0, sortedAscArray.length - 1);
	}

	public static int binarySearch(int[] sortedAscArray, int value, int minIndex, int maxIndex) {
		if (maxIndex < minIndex) {
			return -1;
		}

		int midIndex = (minIndex + maxIndex) / 2;
		if (value > sortedAscArray[midIndex]) {
			return binarySearch(sortedAscArray, value, midIndex + 1, maxIndex);
		} else if (value < sortedAscArray[midIndex]) {
			return binarySearch(sortedAscArray, value, minIndex, midIndex - 1);
		} else {
			return midIndex;
		}
	}
}
