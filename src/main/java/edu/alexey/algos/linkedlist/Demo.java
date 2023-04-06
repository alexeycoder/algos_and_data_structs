package edu.alexey.algos.linkedlist;

public class Demo {
	public static void main(String[] args) {
		var ll = new java.util.LinkedList<Integer>();
		
		
		testMethod((Integer)null);
	}

	private static void testMethod(Integer... args) {
		if (args == null) {
			//return;
		}
		System.out.println(args.length);
	}
}
