package edu.alexey.algos.linkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Двунаправленный связный список.
 */
public class BidirectionalLinkedList<T> implements LinkedList<T> {

	private static class Node<T> {
		T value;
		Node<T> prior;
		Node<T> next;

		public Node(T value) {
			this.value = value;
		}
	}

	private Node<T> head;
	private Node<T> tail;
	private int length;

	@SafeVarargs
	public BidirectionalLinkedList(T... args) {
		length = args != null ? args.length : 0;
		if (length == 0) {
			return;
		}

		head = new Node<T>(args[0]);
		tail = head;
		for (int i = 1; i < length; ++i) {
			tail.next = new Node<T>(args[i]);
			tail.next.prior = tail;
			tail = tail.next;
		}
	}

	@Override
	public int length() {
		return length;
	}

	@Override
	public boolean isEmpty() {
		return head == null;
	}

	@Override
	public void clear() {
		head = tail = null;
		length = 0;
	}

	@Override
	public boolean contains(T value) {
		if (head == null) {
			return false;
		}
		if (head == tail) {
			return Objects.equals(value, head.value);
		}

		var forward = head;
		var backward = tail;
		for (;;) {

			if (Objects.equals(value, forward.value)
					|| Objects.equals(value, backward.value)) {
				return true;
			}

			forward = forward.next;
			if (forward == backward) {
				return false;
			}
			backward = backward.prior;
			if (forward == backward) {
				return Objects.equals(value, forward.value);
			}
		}
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
		if (tail == null) {
			throw new NoSuchElementException();
		}
		return tail.value;
	}

	private void putNodesInFront(Node<T> insertionHead, Node<T> insertionTail) {
		if (head != null) {
			insertionTail.next = head;
			head.prior = insertionTail;
		} else {
			tail = insertionTail;
		}
		head = insertionHead;
	}

	private void putNodesToBack(Node<T> insertionHead, Node<T> insertionTail) {
		if (tail != null) {
			insertionHead.prior = tail;
			tail.next = insertionHead;
		} else {
			head = insertionHead;
		}
		tail = insertionTail;
	}

	@Override
	public void addFront(T value) {
		var node = new Node<>(value);
		putNodesInFront(node, node);
		++length;
	}

	@Override
	public void addBack(T value) {
		var node = new Node<>(value);
		putNodesToBack(node, node);
		++length;
	}

	@Override
	public void addFront(T[] values) {
		if (values == null || values.length == 0) {
			return;
		}
		var frontLinkedList = new BidirectionalLinkedList<>(values);
		putNodesInFront(frontLinkedList.head, frontLinkedList.tail);
		length += values.length;
	}

	@Override
	public void addBack(T[] values) {
		if (values == null || values.length == 0) {
			return;
		}
		var backLinkedList = new BidirectionalLinkedList<>(values);
		putNodesToBack(backLinkedList.head, backLinkedList.tail);
		length += values.length;
	}

	@Override
	public T removeFront() {
		if (head == null) {
			throw new NoSuchElementException();
		}
		var nodeToRemove = head;

		head = head.next;
		if (head != null) {
			head.prior = null;
		} else {
			tail = null;
		}
		--length;

		return nodeToRemove.value;
	}

	@Override
	public T removeBack() {
		if (tail == null) {
			throw new NoSuchElementException();
		}
		var nodeToRemove = tail;

		tail = tail.prior;
		if (tail != null) {
			tail.next = null;
		} else {
			head = null;
		}
		--length;

		return nodeToRemove.value;
	}

	private Node<T> findForward(T value) {
		for (Node<T> node = head; node != null; node = node.next) {
			if (Objects.equals(value, node.value)) {
				return node;
			}
		}
		return null;
	}

	private Node<T> findBackward(T value) {
		for (Node<T> node = tail; node != null; node = node.prior) {
			if (Objects.equals(value, node.value)) {
				return node;
			}
		}
		return null;
	}

	private void removeNode(Node<T> node) {
		assert node != null;
		var priorNode = node.prior;
		var nextNode = node.next;
		if (priorNode != null) {
			priorNode.next = nextNode;
		} else { // i.e. node == head
			head = nextNode;
		}
		if (nextNode != null) {
			nextNode.prior = priorNode;
		} else { // i.e. node == tail
			tail = priorNode;
		}
	}

	@Override
	public boolean removeFront(T value) {
		Node<T> nodeToRemove = findForward(value);
		if (nodeToRemove == null) {
			return false;
		}
		removeNode(nodeToRemove);
		--length;
		return true;
	}

	@Override
	public boolean removeBack(T value) {
		Node<T> nodeToRemove = findBackward(value);
		if (nodeToRemove == null) {
			return false;
		}
		removeNode(nodeToRemove);
		--length;
		return true;
	}

	@Override
	public void reverse() {
		if (head == tail) {
			return;
		}

		Node<T> node = head;
		do {
			var bkp = node.next;
			node.next = node.prior;
			node.prior = bkp;
			node = bkp;
		} while (node != null);

		var temp = head;
		head = tail;
		tail = temp;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private Node<T> node = BidirectionalLinkedList.this.head;

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
				.collect(Collectors.joining(", ", "[\u21c4[ ", " ]]"));
	}
}
