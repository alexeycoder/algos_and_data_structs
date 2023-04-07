package edu.alexey.algos.linkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Однонаправленный, начиная с головного элемента, связный список.
 */
public class UnidirectionalLinkedList<T> implements LinkedList<T> {

	private static class Node<T> {
		T value;
		Node<T> next;

		public Node(T value) {
			this.value = value;
		}
	}

	private static record Neighbors<V>(V prior, V pointed) {
	}

	private Node<T> head;
	private int length;

	@SafeVarargs
	public UnidirectionalLinkedList(T... args) {
		length = args != null ? args.length : 0;
		if (length == 0) {
			return;
		}

		head = new Node<T>(args[0]);
		var node = head;
		for (int i = 1; i < length; ++i) {
			node.next = new Node<T>(args[i]);
			node = node.next;
		}
	}

	public int length() {
		return length;
	}

	public boolean isEmpty() {
		return head == null;
	}

	@Override
	public void clear() {
		head = null;
		length = 0;
	}

	@Override
	public boolean contains(T value) {
		return findForward(value) != null;
	}

	@Override
	public T getFront() {
		if (head == null) {
			throw new NoSuchElementException();
		}
		return head.value;
	}

	@Override
	public T getBack() {
		var tail = getTail();
		if (tail == null) {
			throw new NoSuchElementException();
		}
		return tail.value;
	}

	private void putNodesInFront(Node<T> insertionHead, Node<T> insertionTail) {
		if (head != null) {
			insertionTail.next = head;
		}
		head = insertionHead;
	}

	private void putNodesToBack(Node<T> insertionHead) {
		var tail = getTail();
		if (tail != null) {
			tail.next = insertionHead;
		} else {
			head = insertionHead;
		}
	}

	// O(1)
	@Override
	public void addFront(T value) {
		var node = new Node<>(value);
		putNodesInFront(node, node);
		++length;
	}

	// O(n) <--getTail();
	@Override
	public void addBack(T value) {
		var node = new Node<>(value);
		putNodesToBack(node);
		++length;
	}

	@Override
	public void addFront(T[] values) {
		if (values == null || values.length == 0) {
			return;
		}
		var frontLinkedList = new UnidirectionalLinkedList<>(values);
		putNodesInFront(frontLinkedList.head, frontLinkedList.getTail());
		length += values.length;
	}

	@Override
	public void addBack(T[] values) {
		if (values == null || values.length == 0) {
			return;
		}
		var backLinkedList = new UnidirectionalLinkedList<>(values);
		putNodesToBack(backLinkedList.head);
		length += values.length;
	}

	@Override
	public T removeFront() {
		if (head == null) {
			throw new NoSuchElementException();
		}
		var nodeToRemove = head;

		head = head.next;
		--length;

		return nodeToRemove.value;
	}

	@Override
	public T removeBack() {
		if (head == null) {
			throw new NoSuchElementException();
		}

		Node<T> nodeToRemove = head;
		Node<T> priorNode = null;
		while (nodeToRemove.next != null) {
			priorNode = nodeToRemove;
			nodeToRemove = nodeToRemove.next;
		}

		if (priorNode == null) { // i.e. head is the only node
			head = null;
			length = 0;
			return nodeToRemove.value;
		}

		priorNode.next = null;
		--length;

		return nodeToRemove.value;
	}

	// private Node<T> findBackward(T value) {
	// Node<T> lastFound = null;
	// for (Node<T> node = head; node != null; node = node.next) {
	// if (Objects.equals(value, node.value)) {
	// lastFound = node;
	// }
	// }
	// return lastFound;
	// }

	private Neighbors<Node<T>> findForward(T value) {
		for (Node<T> node = head, prior = null; node != null; prior = node, node = node.next) {
			if (Objects.equals(value, node.value)) {
				return new Neighbors<Node<T>>(prior, node);
			}
		}
		return null;
	}

	private Neighbors<Node<T>> findBackward(T value) {
		Node<T> priorToLastFound = null;
		Node<T> lastFound = null;
		for (Node<T> node = head, prior = null; node != null; prior = node, node = node.next) {
			if (Objects.equals(value, node.value)) {
				lastFound = node;
				priorToLastFound = prior;
			}
		}
		return lastFound != null ? new Neighbors<Node<T>>(priorToLastFound, lastFound) : null;
	}

	private void removeNode(Neighbors<Node<T>> neighbors) {
		assert neighbors.pointed != null;
		var node = neighbors.pointed;
		var priorNode = neighbors.prior;
		var nextNode = node.next;
		if (priorNode != null) {
			priorNode.next = nextNode;
		} else { // i.e. node == head
			head = nextNode;
		}
	}

	@Override
	public boolean removeFront(T value) {
		var foundNeighbors = findForward(value);
		if (foundNeighbors == null) {
			return false;
		}
		removeNode(foundNeighbors);
		--length;
		return true;
	}

	@Override
	public boolean removeBack(T value) {
		var foundNeighbors = findBackward(value);
		if (foundNeighbors == null) {
			return false;
		}
		removeNode(foundNeighbors);
		--length;
		return true;
	}

	@Override
	public void reverse() {
		if (head == null || head.next == null) {
			return;
		}

		Node<T> node = head;
		Node<T> prior = null;

		do {

			var bkp = node.next;

			node.next = prior;
			prior = node;
			node = bkp;

		} while (node != null);

		head = prior;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private Node<T> node = UnidirectionalLinkedList.this.head;

			@Override
			public boolean hasNext() {
				return node != null;
			}

			@Override
			public T next() {
				if (node == null) {
					throw new NoSuchElementException();
				}
				var nodeToReturn = node;
				node = nodeToReturn.next;
				return nodeToReturn.value;
			}
		};
	}

	@Override
	public String toString() {
		return StreamSupport.stream(this.spliterator(), false).map(Object::toString)
				.collect(Collectors.joining(", ", "[\u2192[ ", " ]]"));
	}

	// common

	private Node<T> getTail() {
		if (head == null) {
			return null;
		}
		var node = head;
		while (node.next != null) {
			node = node.next;
		}
		return node;
	}
}
