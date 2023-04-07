package edu.alexey.algos.linkedlist;

import java.util.NoSuchElementException;

/**
 * Вариант API, общего как для двунаправленного, так и для
 * разных вариаций однонаправленных списков.
 * 
 * Тип/API узла не раскрывается.
 * 
 * Поскольку список допускает хранение null-значений как таковых, то,
 * использование null в качестве значения, сигнализирующего о пустоте списка,
 * при возврате любым методом, подразумевающим возвращение значения какого-либо
 * элемента, приводит к неоднозначности интерпретации. Поэтому данный API
 * требует предварительно проверять наличие элементов в списке перед вызовом
 * всякого метода, возвращающего значение какого-либо элемента,
 * например, используя проверку !isEmpty(). В противном случае, такой метод
 * имеет право выбросить исключение типа NoSuchElementException.
 */
public interface LinkedList<T> extends Iterable<T> {

	int length();

	boolean isEmpty();

	void clear();

	boolean contains(T value);

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

	boolean removeFront(T value);

	boolean removeBack(T value);

	/**
	 * Разворот связного списка.
	 */
	void reverse();
}
