package edu.alexey.algos.linkedlist;

public class Demo {
	public static void main(String[] args) {

		System.out.println("\nДемонстрация разворота связного списка (одно- и двусвязного).");

		// UNIDIRECTIONAL
		{
			var unidirectionalA = new UnidirectionalLinkedList<Integer>(1, 2, 3, 4, 5, 6, 7, 8, 9);
			unidirectionalA.addFront(new Integer[] { 10, 20, 30, 40, 50 });
			unidirectionalA.addBack(new Integer[] { 111, 222, 333, 444, 555 });

			var unidirectionalB = new UnidirectionalLinkedList<String>("a", "bb", "ccc", "dddd");
			unidirectionalB.addFront(new String[] { "XX", "YY", "ZZ" });
			unidirectionalB.addBack(new String[] { "UV", "WQ", "NS" });

			System.out.println("\nОдносвязный \u2014 вариант А");
			System.out.println("Исходный:");
			System.out.println(unidirectionalA);
			unidirectionalA.reverse();
			System.out.println("Развёрнутый:");
			System.out.println(unidirectionalA);

			System.out.println("\nОдносвязный \u2014 вариант Б");
			System.out.println("Исходный:");
			System.out.println(unidirectionalB);
			unidirectionalB.reverse();
			System.out.println("Развёрнутый:");
			System.out.println(unidirectionalB);
		}

		// BIDIRECTIONAL
		{
			var bidirectionalA = new BidirectionalLinkedList<Integer>(1, 2, 3, 4, 5, 6, 7, 8, 9);
			bidirectionalA.addFront(new Integer[] { 10, 20, 30, 40, 50 });
			bidirectionalA.addBack(new Integer[] { 111, 222, 333, 444, 555 });

			var bidirectionalB = new BidirectionalLinkedList<String>("a", "bb", "ccc", "dddd");
			bidirectionalB.addFront(new String[] { "XX", "YY", "ZZ" });
			bidirectionalB.addBack(new String[] { "UV", "WQ", "NS" });

			System.out.println("\nДвусвязный \u2014 вариант А");
			System.out.println("Исходный:");
			System.out.println(bidirectionalA);
			bidirectionalA.reverse();
			System.out.println("Развёрнутый:");
			System.out.println(bidirectionalA);

			System.out.println("\nДвусвязный \u2014 вариант Б");
			System.out.println("Исходный:");
			System.out.println(bidirectionalB);
			bidirectionalB.reverse();
			System.out.println("Развёрнутый:");
			System.out.println(bidirectionalB);
		}
	}

}
