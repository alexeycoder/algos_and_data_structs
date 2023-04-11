package edu.alexey.algos.tree;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Demo {

	public static void main(String[] args) {
		// demoHeap();
		// demoBinaryTree();
		demoRedBlackTree();
	}

	static void demoRedBlackTree() {
		System.out.println("\nДЕМО: Красно-Чёрное Дерево\n");

		var array = getRandomArray(15, 0, 99);
		System.out.println("Исходный (случайный) массив:");
		System.out.println(Arrays.toString(array));

		var binTree = new BinaryTree<>(Comparator.naturalOrder(), array.clone());
		System.out.println("\nДвоичное дерево поиска, BST:");
		System.out.println(binTree.toStringByLevels());

		var redBlackTree = new RedBlackTree<>(Comparator.naturalOrder(), array.clone());
		System.out.println("\nКрасно-чёрное дерево, RBT:");
		System.out.println(redBlackTree.toStringByLevels());

	}

	static void demoHeap() {
		System.out.println("\nDEMO Heap\n");

		var array = getRandomArray(10, 0, 99);

		System.out.println("Original array:");
		System.out.println(Arrays.toString(array));

		Heap<Integer> heap = new Heap<>(Comparator.<Integer>naturalOrder(), array);
		System.out.println();
		System.out.println(heap);

		System.out.println("\nConsecutive extractMax():");
		while (heap.size() > 0) {
			System.out.print(heap.extractMax() + ", ");
		}
		System.out.println();
		System.out.println(heap);
	}

	static void demoBinaryTree() {
		System.out.println("\nDEMO Binary Tree\n");

		var array = new String[] { "AA",
				"AB", "AC",
				"BD", "BE", "DF", "DG",
				"Dh", "Di", "Ej", "Ek", "Fl", "Fm", "Gn", "Go",
				"hp", "hq", "ir", "is", "jt" };

		var binTree = new BinaryTree<>(Comparator.naturalOrder(), array);

		System.out.println("Initial array:");
		System.out.println(Arrays.toString(array));

		System.out.println();
		System.out.println(binTree.toStringByLevels());

		System.out.println();
		System.out.println(binTree);
		System.out.println("binTree.count() = " + binTree.count());
	}

	static Integer[] getRandomArray(int size, int min, int max) {
		return new Random().ints(min, max + 1).limit(size).boxed().toArray(Integer[]::new);
	}
}
