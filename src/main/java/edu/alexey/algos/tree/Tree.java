package edu.alexey.algos.tree;

import java.util.ArrayList;
import java.util.List;

public class Tree {
	public static class Node {
		int value;
		List<Node> children;
	}

	Node root;

	/**
	 * Проверка существования значения в дереве методом поиска в глубину.
	 * 
	 * @param value
	 * @return
	 */
	public boolean existsDepthFirst(int value) {
		return root != null && findDepthFirst(root, value) != null;
	}

	/**
	 * Поиск узла по значению в глубину.
	 * 
	 * @param node
	 * @param value
	 * @return
	 */
	private Node findDepthFirst(Node node, int value) {
		if (node.value == value) {
			return node;
		}

		for (Node child : node.children) {
			Node found = findDepthFirst(child, value);
			if (found != null) {
				return found;
			}
		}

		return null;
	}

	/**
	 * Поиск узла по значению в ширину.
	 * 
	 * @param value
	 * @return
	 */
	private Node findBreadthFirst(int value) {
		if (root == null) {
			return null;
		}
		List<Node> line = List.of(root);
		do {
			List<Node> nextLine = new ArrayList<>();
			for (Node node : line) {
				if (node.value == value) {
					return node;
				}
				nextLine.addAll(node.children);
			}
			line = nextLine;
		} while (line.size() > 0);

		return null;
	}

}
