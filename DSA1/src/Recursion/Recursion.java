package Recursion;
import java.util.LinkedList;

public class Recursion {

	public static void main(String[] args) {
		String str = "drab bard";
		System.out.println(isPalindrome(str));
		int[] array = {13, 25, 28, 30, 39, 43, 44, 58, 66, 70, 78, 81, 86, 88, 95};
		printReverse(array, 0);
		System.out.println();
		System.out.println(Factorial(5));
		System.out.println(Fibonacci(5));
		int[] arr = {26,  18,  48,  77,  30,  42,  23,  69,  33};
		String st = "abc";
		quickSort(arr);
		for (int i : arr) {
			System.out.print(i + " ");
		}
	}

	public static int partition(int[] array, int start, int end) {
		int pivot = end;
		int pivotIndex = start-1;
		for (int i = start; i <= end; i++) {
			if (array[i] <= array[pivot]) {
				pivotIndex++;
				int temp = array[i];
				array[i] = array[pivotIndex];
				array[pivotIndex] = temp;
			}
		}
		return pivotIndex;
	}
	
	public static void quickSort(int[] array) {
		quickSort(array, 0, array.length-1);
	}
	
	private static void quickSort(int[] array, int start, int end) {
		if (start < end) {
			int pivot = partition(array, start, end);
			quickSort(array, start, pivot-1);
			quickSort(array, pivot+1, end);
		}
	}
	
	public static void TowerOfHanoi(int disks) {
		towerOfHanoi(disks, 1, 2, 3);
	}
	
	private static void towerOfHanoi(int disks, int source, int scrap, int goal) {
		if (disks > 0) {
			towerOfHanoi(disks-1, source, goal, scrap);
			System.out.println("Move disk " + disks + " from tower " + source + " to tower "+ goal + ".");
			towerOfHanoi(disks-1, scrap, source, goal);
		}
	}
	
	public static void powerSet(String str) {
		powerSet(str, 0, "");
	}
	
	private static void powerSet(String str, int index, String curr) {
		if (index == str.length()) {
			System.out.print(curr + ", ");
			return;
		}
		powerSet(str, index + 1, curr);
		powerSet(str, index + 1, curr + str.charAt(index));
	}
	
	public static void printReverse(int[] array, int index) {
		if (index == array.length)
			return;
		printReverse(array, index + 1);
		System.out.print(array[index] + " ");
	}
	
	
	public static int Power(int num, int exp) {
		if (exp == 0)
			return 1;
		else
			return Power(num, exp - 1) * num;
	}
	
	public static int countNumber(int[] array, int elem, int index) {
		if (index == array.length)
			return 0;
		else if (array[index] == elem)
			return countNumber(array, elem, index + 1) + 1;
		else
			return countNumber(array, elem, index + 1);
	}
	
	public static String reverseString(String str, int index) {
		if (index == str.length()-1)
			return str.charAt(index) + "";
		else
			return reverseString(str, index + 1) + str.charAt(index);
	}
	
	public static int Sum(int[] array, int index) {
		if (index == array.length)
			return 0;
		else
			return array[index] + Sum(array, index + 1);
	}
	
	public static int Fibonacci(int num) {
		if (num < 3)
			return 1;
		else
			return Fibonacci(num-1) + Fibonacci(num-2);
	}
	
	public static int Factorial(int num) {
		if (num <= 1)
			return 1;
		else {
			return  num * Factorial(num-1);
		}
	}
	
	public static int binarySearch(int[] array, int elem) {
		int left = 0, count = 0;
		int right = array.length-1;
		int mid;
		
		while (left <= right) {
			count++;
			mid = (right + left) / 2;
			if (array[mid] == elem)
				return count;
			else if(array[mid] > elem) {
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		
		return -1;
	}
	
	public static boolean isPalindrome(String str) {
		return isPalindrome(str, 0, str.length()-1);
	}
	
	private static boolean isPalindrome(String str, int left, int right) {
		if (left <= right) {
			return true;
		} else if (str.charAt(left) != str.charAt(right))
			return false;
		else {
			return isPalindrome(str, left+1, right-1);
		}
	}
}
