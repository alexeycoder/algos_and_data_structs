# Algorithms and data structures

## Задание №1. Пирамидальная сортировка (сортировка кучей).

[HeapSort.java](https://github.com/alexeycoder/algos_and_data_structs/blob/main/src/main/java/edu/alexey/algos/sort/HeapSort.java)

## Задание №2. Разворот одно- и двусвязного списка.

[API связных списков](https://github.com/alexeycoder/algos_and_data_structs/blob/main/src/main/java/edu/alexey/algos/linkedlist/LinkedList.java)

Реализации метода разворота &mdash; см. метод `reverse()` в
реализациях [двусвязного](https://github.com/alexeycoder/algos_and_data_structs/blob/main/src/main/java/edu/alexey/algos/linkedlist/BidirectionalLinkedList.java) и [односвязного](https://github.com/alexeycoder/algos_and_data_structs/blob/main/src/main/java/edu/alexey/algos/linkedlist/UnidirectionalLinkedList.java) списков.

*Демонстрация работы:*

![linked-lists-reverse](https://user-images.githubusercontent.com/109767480/230520191-32f57867-fccc-47fa-9981-0ab32c2ccd7e.png)

## Задание №3. Красно-черное дерево.

Необходимо превратить собранное на семинаре дерево поиска в полноценное левостороннее красно-черное дерево. И реализовать в нем метод добавления новых элементов с балансировкой.

Красно-черное дерево имеет следующие критерии:
* Каждая нода имеет цвет (красный или черный)
* Корень дерева всегда черный
* Новая нода всегда красная
* Красные ноды могут быть только левым ребенком
* У красной ноды все дети черного цвета

Соответственно, чтобы данные условия выполнялись, после добавления элемента в дерево необходимо произвести балансировку, благодаря которой все критерии выше станут валидными. Для балансировки существует 3 операции: левый малый поворот, правый малый поворот и смена цвета.

Реализация левостороннего красно-чёрного дерева (методов добавления элементов с балансировкой) &mdash; [tree/RedBlackTree.java](https://github.com/alexeycoder/algos_and_data_structs/blob/main/src/main/java/edu/alexey/algos/tree/RedBlackTree.java)

Вариант реализации двоичного дерева поиска, в котором ключ (значение) в любом узле больше или равен ключам во всех узлах его левого поддерева и меньше или равен ключам во всех узлах его правого поддерева &mdash; [tree/BinaryTree.java](https://github.com/alexeycoder/algos_and_data_structs/blob/main/src/main/java/edu/alexey/algos/tree/BinaryTree.java)

*Демонстрация работы (BST vs RBT):*

![red-black-tree](https://user-images.githubusercontent.com/109767480/231037251-4f36b6bb-f4db-4453-afc7-79cd6ccc87d0.png)
