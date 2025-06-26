package Recursion;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import LinkedLists.LinkedList.Node;

public class Recursion {

	public static void main(String[] args) {
		int[] array = {1,2,3,4};
//		HashSet<Integer> arr = totalNumbers(array);
//		for (int i : arr) {
//			System.out.print(i + " ");
//		}
//		System.out.println();
//		System.out.println(arr.size());
		
		
		Node head = new Node(11);
		Node two = new Node(22);
		Node three = new Node(33);
		Node four = new Node(44);
		Node five = new Node(55);
		Node six = new Node(66);
		Node tail = new Node(77);
		head.next = two; //two.random = head;
		two.next = three; //three.random = two;
		three.next = four; //four.random = three;
		four.next = five; //five.random = four;
		five.next = six; //tail.random = five;
		six.next = tail;
		Node walk = head;
		while (walk != null) {
			System.out.print(walk.val + "|");
			walk = walk.next;
		}
		System.out.println();
		Node walker = swapPairs(head);
		while (walker != null) {
			System.out.print(walker.val + "|");
			walker = walker.next;
		}
	}
	
	public static class Node {
	    int val;
	    Node next;
	    Node random;

	    public Node(int val) {
	        this.val = val;
	        this.next = null;
	        this.random = null;
	    }
	}
	
	public static char kthCharacter(int k) {
		String word = "a";
		while (word.length() < k) {
			String next = "";
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				char n = (char) ((c == 'z') ? 'a' : (c + 1));
				next += n;
			}
			word += next;
		}
		return word.charAt(k-1);
	}
    
	public static HashSet<Integer> totalNumbers(int[] digits) {
		//Generate all possible 3-digit combinations of digits array.
		HashSet<Integer> combos = new HashSet<>();
		int len = digits.length;
		for (int i = 0; i < len; i++) {
			if (digits[i] == 0) continue;
			for (int j = 0; j < len; j++) {
				if (j == i) continue;
				for (int k = 0; k < len; k++) {
					if (k != j && k != i) {
						if(digits[k] % 2 == 0) {
							int num = digits[i] * 100 + digits[j] * 10 + digits[k];
							combos.add(num);
						}
					}
				}
			}
		}
		return combos;
	}
	
	public static boolean isPowerOfTwo(int n) {
		if (n == 1)
			return true;
		if (n == 0 || n % 2 != 0)
			return false;
		
		return isPowerOfTwo(n/2);
		
	}
	
	public static Node swapPairs(Node head) {
		if (head == null || head.next == null)
			return head;
	
		Node ahead = head.next;
		head.next = swapPairs(ahead.next);
		ahead.next = head;
		
		return ahead;
	}
	
	public static Node reverseList(Node head) {
		if (head == null || head.next == null)
			return head;
		
		Node newHead = reverseList(head.next);
		head.next.next = head;
		head.next = null;
		
		return newHead;
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
