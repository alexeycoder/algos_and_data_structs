package edu.alexey.algos.sort;

public class ShellSort implements Sort {

	@Override
	public void sort(int[] array) {
		shellSort(array, 0, array.length - 1);
	}

	private static void shellSort(int[] array, int l, int r) {
		assert l <= r;
		int n = r - l + 1;
		int h = 1;
		while (h < n / 3) {
			h = 3 * h + 1; // 1, 4, 13, 40, 121, ...
		}

		for (; h > 0; h /= 3) {

			// h-sort:
			for (int i = l + h; i <= r; ++i) {
				// insert array[i] in a[i-h], a[i-2*h], a[i-3*h],...
				var v = array[i];
				int j = i;
				for (; j >= l + h && array[j - h] > v; j -= h) {
					array[j] = array[j - h];
				}
				array[j] = v;
			}

		}
	}
}
