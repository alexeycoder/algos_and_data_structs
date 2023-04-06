package edu.alexey.algos.linkedlist;

import java.util.NoSuchElementException;

/**
 * Вариант API, общего как для двунаправленного, так и для
 * разных вариаций однонаправленных списков.
 * (Тип/API узла не раскрывается.)
 */
public interface LinkedList<T> {

	int length();

	boolean isEmpty();

	void clear();

	/**
	 * @return Возвращает значение головного элемента списка.
	 *         Список не должен быть пустым.
	 * @throws NoSuchElementException если список пуст.
	 */
	T getFront();

	/**
	 * @return Возвращает значение хвостового элемента списка.
	 *         Список не должен быть пустым.
	 * @throws NoSuchElementException если список пуст.
	 */
	T getBack();

	void addFront(T value);

	void addBack(T value);

	void addFront(T[] values);

	void addBack(T[] values);

	/**
	 * Удаляет головной элемент списка.
	 * 
	 * @return Возвращает значение удалённого головного элемента списка.
	 *         Список не должен быть пустым.
	 * @throws NoSuchElementException если список пуст.
	 */
	T removeFront();

	/**
	 * Удаляет хвостовой элемент списка.
	 * 
	 * @return Возвращает значение удалённого хвостового элемента списка.
	 *         Список не должен быть пустым.
	 * @throws NoSuchElementException если список пуст.
	 */
	T removeBack();

	void reverse();
}
