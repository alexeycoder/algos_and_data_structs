package edu.alexey.algos.tree;

/*
 * Бинарное дерево -- частный случай дерева, у которого
 *  1) все узлы строго уникальны и
 *  2) имеют не более 2-х дочерних узлов, при этом
 *  3) левый дочерний узел -- всегда меньше родителя,
 *     правый дочерний узел -- всегда больше родителя.
 * 
 * Сбалансированное дерево -- частный случай бинарного дерева, у которого
 * выполняется след. требование:
 *   для любого узла дерева высота его правого поддерева отличается от высоты
 *   левого поддерева не более чем на 1.
 * Таким образом, корень такого дерева -- его центральный элемент -- количество
 * элементов справа и слева отличается не более чем на 1,
 * и сложность поиска по сбалансированному дереву ~O(log n).
 * 
 * Красно-чёрное дерево:
 * - Каждый узел м.б. либо чёрным либо красным и иметь 2-х потомков.
 * - Корень всегда чёрный.
 * - Дети красного узла обязательно чёрные.
 * 
 *   Для частного случая -- левостороннего красно-чёрного дерева -- также
 *   применяется следующий критерий:
 *     Красный узел м.б. только левым потомком.
 * 
 * 3 вида операции для проведения балансировки:
 * 
 * 1. Левосторонний поворот (малый левый поворот) -- когда красный узел, являющийся
 * левым ребёнком, должен стать правым ребёнком, при этом происходит обмен
 * значений родителя и ребёнка местами.
 * 
 * 2. Правосторонний поворот (малый правый поворот) -- когда правый красный узел
 * переезжает на левую сторону, относительно родителя:
 * у родительского узла левый ребёнок остаётся его левым ребёнком,
 * у дочернего красного узла её правый ребёнок остаётся правым ребёнком,
 * при этом левый ребёнок красного узла переезжает и становится правым ребёнком
 * родительского узла, и красный узел становится родителем узла.
 * 
 * 3. Смена цвета
 * Оба дочерних красных узла становятся чёрными, а их родительский узел
 * становится красным.
 * 
 */

public class RedBlackTree {

	private static class Node {
		private int value;
		private boolean isRed;
		private Node left;
		private Node right;

		@Override
		public String toString() {
			return String.format("Node{value=%d, %s}", value, isRed ? "RED" : "BLACK");
		}
	}

	Node root;

	public boolean add(int value) {
		if (root != null) {
			boolean result = addNode(root, value);
			root = rebalance(root);
			root.isRed = false;
			return result;
		} else {
			root = new Node();
			root.isRed = false;
			root.value = value;
			return true;
		}
	}

	private boolean addNode(Node node, int value) {
		if (node.value == value) {
			return false;
		}

		if (node.value > value) {

			if (node.left != null) {
				boolean result = addNode(node.left, value);
				node.left = rebalance(node.left);
				return result;
			} else {
				node.left = new Node();
				node.left.isRed = true;
				node.left.value = value;
				return true;
			}

		} else { // node.value < value

			if (node.right != null) {
				boolean result = addNode(node.right, value);
				node.right = rebalance(node.right);
				return result;
			} else {
				node.right = new Node();
				node.right.isRed = true;
				node.right.value = value;
				return true;
			}

		}

	}

	private Node rebalance(Node node) {
		Node result = node;
		boolean needRebalance;
		do {
			needRebalance = false;

			if (result.right != null && result.right.isRed
					&& (result.left == null || !result.left.isRed)) {
				needRebalance = true;
				result = rightSwap(result);
			}

			if (result.left != null && result.left.isRed
					&& result.left.left != null && result.left.left.isRed) {
				needRebalance = true;
				result = leftSwap(result);
			}

			if (result.left != null && result.left.isRed
					&& result.right != null && result.right.isRed) {
				needRebalance = true;
				colorSwap(result);
			}
		} while (needRebalance);

		return result;
	}

	private Node rightSwap(Node node) {
		Node right = node.right;
		Node between = right.left;
		right.left = node;
		node.right = between;
		right.isRed = node.isRed;
		node.isRed = true;
		return right;
	}

	private Node leftSwap(Node node) {
		Node left = node.left;
		Node between = left.right; // node which parent have to be changed
		left.right = node;
		node.left = between;
		left.isRed = node.isRed;
		node.isRed = true;
		return left;
	}

	/**
	 * Вызывается только в ситуациях, когда у узла два красных дочерних узла.
	 * 
	 * @param node
	 */
	private void colorSwap(Node node) {
		assert node.left.isRed && node.right.isRed;
		node.right.isRed = false;
		node.left.isRed = false;
		node.isRed = true;
	}

}
