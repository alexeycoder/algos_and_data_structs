package edu.alexey.algos.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import edu.alexey.utils.StringUtils;

public class BinaryTree<E> {

	private static class Node<T> {
		T value;
		Node<T> left;
		Node<T> right;

		Node(T value) {
			this.value = value;
		}
	}

	final Comparator<E> comparator;

	Node<E> root;

	public BinaryTree(Comparator<E> comparator, E[] array) {
		Objects.requireNonNull(array);
		Objects.requireNonNull(comparator);
		this.comparator = comparator;
		fill(array);
	}

	private void fill(E[] array) {
		for (E t : array) {
			// addNextStraightforward(t);
			insert(t);
		}
	}

	/**
	 * Добавляет значение в дерево поиска.
	 * 
	 * @param value
	 * @return
	 */
	private Node<E> insert(E value) {
		Node<E> newNode = new Node<>(value);

		if (root == null) {
			root = newNode;
			return newNode;
		}

		var node = root;
		do {
			if (comparator.compare(value, node.value) < 0) {
				if (node.left == null) {
					node.left = newNode;
					break;
				} else {
					node = node.left;
				}
			} else {
				if (node.right == null) {
					node.right = newNode;
					break;
				} else {
					node = node.right;
				}
			}
		} while (node != null);

		return newNode;
	}

	/**
	 * Добавляет узел с указанным значением в следующее свободное место без
	 * учёта значения родительского узла
	 * (для построения любого бинарного дерева, не обязательно соблюдающего
	 * критерий дерева поиска).
	 * 
	 * @param value
	 * @return
	 */
	private Node<E> addNextStraightforward(E value) {
		Node<E> newNode = new Node<>(value);

		if (root == null) {
			root = newNode;
			return newNode;
		}

		List<Node<E>> filledLevel = List.of(root);
		do {
			List<Node<E>> level = new ArrayList<>();

			for (Node<E> node : filledLevel) {
				if (node.left == null) {
					node.left = newNode;
					return newNode;
				} else if (node.right == null) {
					node.right = newNode;
					return newNode;
				} else {
					level.add(node.left);
					level.add(node.right);
				}
			}
			filledLevel = level;

		} while (!filledLevel.isEmpty());

		return newNode;
	}

	public int count() {
		return count(root);
	}

	private int count(Node<E> node) {
		if (node == null) {
			return 0;
		}
		return count(node.left) + count(node.right) + 1;
	}

	public void depthFirstSearch(Consumer<E> valuesConsumer) {
		depthFirstSearch(root, n -> valuesConsumer.accept(n.value));
	}

	private static <T> void depthFirstSearch(Node<T> node, Consumer<Node<T>> visit) {
		assert node != null && visit != null;
		if (node.left != null) {
			depthFirstSearch(node.left, visit);
		}
		if (node.right != null) {
			depthFirstSearch(node.right, visit);
		}
		visit.accept(node);
	}

	private static <T> void breadthFirstSearch(Node<T> node, Consumer<Node<T>> visit) {
		assert node != null && visit != null;

		Queue<Node<T>> q = new ArrayDeque<>();
		q.offer(node);

		do {
			node = q.poll();
			visit.accept(node);
			if (node.left != null) {
				q.offer(node.left);
			}
			if (node.right != null) {
				q.offer(node.right);
			}

		} while (!q.isEmpty());
	}

	private static <T> void breadthFirstSearch(Node<T> node, int levelId, BiConsumer<Node<T>, Integer> visit) {
		assert node != null && visit != null;

		Queue<Node<T>> level = new ArrayDeque<>();
		level.offer(node);

		do {
			Queue<Node<T>> nextLevel = new ArrayDeque<>();

			do {
				node = level.poll();
				visit.accept(node, levelId);
				if (node.left != null) {
					nextLevel.offer(node.left);
				}
				if (node.right != null) {
					nextLevel.offer(node.right);
				}

			} while (!level.isEmpty());

			level = nextLevel;
			++levelId;

		} while (!level.isEmpty());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName()).append("[ ");
		// depthFirstSearch(root, n -> {
		breadthFirstSearch(root, n -> {
			sb.append(n.value.toString()).append(" ");
		});
		return sb.append("]").toString();
	}

	// public String toStringByLevels() {
	// 	if (root == null) {
	// 		return "/null";
	// 	}
	// 	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
	// 	AtomicInteger lastLevelId = new AtomicInteger(-1);
	// 	breadthFirstSearch(root, 0, (n, i) -> {
	// 		if (lastLevelId.get() != i) {
	// 			sb.append(System.lineSeparator()).append(i.toString()).append(": ");
	// 			lastLevelId.set(i);
	// 		}
	// 		sb.append(n.value.toString()).append(" ");
	// 	});
	// 	return sb.toString();
	// }

	public String toStringByLevels() {
		if (root == null) {
			return "/null";
		}

		final int cellLen = 2;
		final String nilNodeStr = " ".repeat(cellLen);

		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());

		Queue<Node<E>> level = new ArrayDeque<>();
		level.offer(root);
		int levelId = 0;
		StringBuilder nextLevelSb = new StringBuilder("\u001b[1;97m")
				.append(StringUtils.padCenter(root.value.toString(), " ", cellLen))
				.append("\u001b[0m");

		do {
			sb.append(System.lineSeparator()).append(levelId).append(":   ").append(nextLevelSb);
			nextLevelSb = new StringBuilder();

			Queue<Node<E>> nextLevel = new ArrayDeque<>();
			do {
				var node = level.poll();
				var child = node.left;
				if (child != null) {
					nextLevel.offer(child);
					nextLevelSb.append("\u001b[1;").append("97m")
							.append(StringUtils.padCenter(child.value.toString(), " ", cellLen))
							.append("\u001b[0m")
							.append(", ");
				} else {
					nextLevelSb.append(nilNodeStr)
							.append("  ");
				}

				child = node.right;
				if (child != null) {
					nextLevel.offer(child);
					nextLevelSb.append("\u001b[1;").append("97m")
							.append(StringUtils.padCenter(child.value.toString(), " ", cellLen))
							.append("\u001b[0m");
				} else {
					nextLevelSb.append(nilNodeStr);
				}

				nextLevelSb.append(" | ");

			} while (!level.isEmpty());

			level = nextLevel;
			++levelId;

		} while (!level.isEmpty());

		return sb.toString();
	}
}
