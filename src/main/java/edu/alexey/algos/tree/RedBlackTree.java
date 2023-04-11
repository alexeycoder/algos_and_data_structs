package edu.alexey.algos.tree;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import edu.alexey.utils.StringUtils;

/**
 * Красно-Чёрное Дерево -- двоичное дерево поиска
 * (Рудольф Байер, 1972).
 * 
 * 1. Каждый узел промаркирован: красный или чёрный.
 * 2. Корень и конечные узлы (листья) дерева -- чёрные.
 * 3. У красного узла родительский узел -- чёрный.
 * Как следствие, у красной ноды все дети черного цвета,
 * и, на пути не могут лежать подряд два красных узла.
 * 4. Все простые пути от любого узла до листьев содержат одинаковое количество
 * чёрных узлов.
 * 
 * Для частного случая -- Левостороннего Красно-Чёрного Дерева -- также
 * применяется следующий критерий: красный узел м.б. только левым потомком.
 * 
 * ∃ 3 вида операции для проведения балансировки:
 * 
 * 1. Левосторонний поворот (малый левый поворот) -- когда красный узел,
 * являющийся левым ребёнком, должен стать правым ребёнком, при этом происходит
 * обмен значений родителя и ребёнка местами.
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
 */
public class RedBlackTree<E> {

	private static class Node<T> {
		T value;
		boolean isRed;
		Node<T> left;
		Node<T> right;

		private Node(T value, boolean isRed) {
			this.value = value;
			this.isRed = isRed;
		}

		static <T> Node<T> createBlack(T value) {
			return new Node<>(value, false);
		}

		static <T> Node<T> createRed(T value) {
			return new Node<>(value, true);
		}
	}

	final Comparator<E> comparator;
	Node<E> root;

	public RedBlackTree(Comparator<E> comparator, E[] array) {
		Objects.requireNonNull(comparator);
		Objects.requireNonNull(array);
		this.comparator = comparator;
		fill(array);
	}

	private void fill(E[] array) {
		for (E e : array) {
			add(e);
		}
	}

	public boolean add(E value) {
		if (root != null) {
			boolean result = addNode(root, value);
			root = rebalance(root);
			root.isRed = false;
			return result;
		} else {
			root = Node.createBlack(value); // root is Black
			return true;
		}
	}

	private boolean addNode(Node<E> node, E value) {
		if (node.value == value) {
			return false;
		}

		if (comparator.compare(value, node.value) < 0) {

			if (node.left != null) {
				boolean result = addNode(node.left, value);
				node.left = rebalance(node.left);
				return result;
			} else {
				node.left = Node.createRed(value);
				return true;
			}

		} else { // node.value < value

			if (node.right != null) {
				boolean result = addNode(node.right, value);
				node.right = rebalance(node.right);
				return result;
			} else {
				node.right = Node.createRed(value);
				return true;
			}

		}
	}

	private Node<E> rebalance(Node<E> node) {
		var result = node;
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

	private Node<E> rightSwap(Node<E> node) {
		var right = node.right;
		var between = right.left;
		right.left = node;
		node.right = between;
		right.isRed = node.isRed;
		node.isRed = true;
		return right;
	}

	private Node<E> leftSwap(Node<E> node) {
		var left = node.left;
		var between = left.right; // node which parent have to be changed
		left.right = node;
		node.left = between;
		left.isRed = node.isRed;
		node.isRed = true;
		return left;
	}

	/**
	 * Вызывается только в ситуациях, когда у узла два красных дочерних узла.+
	 * 
	 * @param node Узел
	 */
	private void colorSwap(Node<E> node) {
		assert node.left.isRed && node.right.isRed;
		node.right.isRed = false;
		node.left.isRed = false;
		node.isRed = true;
	}

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
					nextLevelSb.append("\u001b[1;").append(child.isRed ? "91m" : "97m")
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
					nextLevelSb.append("\u001b[1;").append(child.isRed ? "91m" : "97m")
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

	// private static <T> void breadthFirstSearch(Node<T> node, int levelId,
	// BiConsumer<Node<T>, Integer> visit) {
	// assert node != null && visit != null;

	// Queue<Node<T>> level = new ArrayDeque<>();
	// level.offer(node);

	// do {
	// Queue<Node<T>> nextLevel = new ArrayDeque<>();

	// do {
	// node = level.poll();
	// visit.accept(node, levelId);
	// if (node.left != null) {
	// nextLevel.offer(node.left);
	// }
	// if (node.right != null) {
	// nextLevel.offer(node.right);
	// }

	// } while (!level.isEmpty());

	// level = nextLevel;
	// ++levelId;

	// } while (!level.isEmpty());
	// }

	// public String toStringByLevels() {
	// if (root == null) {
	// return "/null";
	// }
	// StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
	// AtomicInteger lastLevelId = new AtomicInteger(-1);
	// breadthFirstSearch(root, 0, (n, i) -> {
	// if (lastLevelId.get() != i) {
	// sb.append(System.lineSeparator()).append(i.toString()).append(": ");
	// lastLevelId.set(i);
	// }

	// sb.append("\u001b[1;")
	// .append(n.isRed ? "91m" : "97m")
	// .append(n.value.toString()).append("\u001b[0m ");
	// });
	// return sb.toString();
	// }

}
