package edu.alexey.algos.linkedlist;

import java.util.NoSuchElementException;

public class BidirectionalLinkedList<T> implements LinkedList<T> {

	private static class Node<T> {
		T value;
		Node<T> prior;
		Node<T> next;

		public Node() {
		}

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

	public void add(T value) {
		var node = new Node<>(value);
		if (tail == null) {
			head = tail = node;
			length = 1;
		} else {
			tail.next = node;
			node.prior = tail;
			++length;
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

	private void addNodeInFront(Node<T> node) {
		if (head != null) {
			node.next = head;
			head.prior = node;
		} else {
			tail = node;
		}
		head = node;
	}

	private void addNodeToBack(Node<T> node) {
		if (tail != null) {
			node.prior = tail;
			tail.next = node;
		} else {
			head = node;
		}
		tail = node;
	}

	@Override
	public void addFront(T value) {
		var node = new Node<>(value);
		addNodeInFront(node);
		++length;
	}

	@Override
	public void addBack(T value) {
		var node = new Node<>(value);
		addNodeToBack(node);
		++length;
	}

	@Override
	public void addFront(T[] values) {
		if (values == null || values.length == 0) {
			return;
		}
		var frontLinkedList = new BidirectionalLinkedList<>(values);
		addNodeInFront(frontLinkedList.tail);
		length += values.length;
	}

	@Override
	public void addBack(T[] values) {
		if (values == null || values.length == 0) {
			return;
		}
		var backLinkedList = new BidirectionalLinkedList<>(values);
		addNodeToBack(backLinkedList.head);
		length += values.length;
	}

	@Override
	public T removeFront() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'removeFront'");
	}

	@Override
	public T removeBack() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'removeBack'");
	}

	@Override
	public void reverse() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'reverse'");
	}
}
