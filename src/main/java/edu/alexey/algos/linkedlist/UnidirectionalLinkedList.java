package edu.alexey.algos.linkedlist;

import java.util.Objects;

/**
 * Однонаправленный, начиная с головного элемента, связный список.
 */
public class UnidirectionalLinkedList<T> {

	static class Node<T> {
		T value;
		Node<T> next;

		public Node() {
		}

		public Node(T value) {
			this.value = value;
		}
	}

	Node<T> head;
	int length;

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

	public boolean empty() {
		return head == null;
	}

	// O(1)
	public void addFront(T value) {
		var node = new Node<>(value);
		if (head == null) {
			head = node;
			length = 1;
		} else {
			node.next = head;
			head = node;
			++length;
		}
	}

	// O(n) <--getTail();
	public void addBack(T value) {
		var node = new Node<>(value);
		if (head == null) {
			head = node;
			length = 1;
		} else {
			var tail = getTail();
			tail.next = node;
			++length;
		}
	}

	Node<T> find(T value) {

		for (var node = head; node != null; node = node.next) {
			if (Objects.equals(value, node.value)) {
				return node;
			}
		}
		return null;
	}

	Node<T> getTail() {
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
