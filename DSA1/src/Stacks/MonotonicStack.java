package Stacks;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Stack;

public class MonotonicStack {

	public static void main(String[] args) {
		int[] nums1 = {73,74,75,71,69,72,76,73};
		int[] result = dailyTemperatures(nums1);
		for (int n : result) {
			System.out.print(n + " ");
		}
		System.out.println();
	}
	
	public static int[] dailyTemperatures(int[] temperatures) {
		/*
		 * Leet-code 739 (medium)
		 */
		/*
		 * Using ArrayQueue as a stack to store indexes of each
		 * element. And result array will store the distance from each index.
		 */
		Deque<Integer> indexesStack = new ArrayDeque<>();
		int[] result = new int[temperatures.length];
		for (int i = 0; i < temperatures.length; i++) {
			/*
			 * Here we do the same thing, we check if the current is greater
			 * than last index on stack and if it is, we just calculate the distance
			 * between two indexes, and put it in the "index" position in the result array.
			 */
			int temp = temperatures[i];
			while (!indexesStack.isEmpty() && temperatures[indexesStack.peek()] < temp) {
				int index = indexesStack.pop();
				result[index] = i - index;
			}
			indexesStack.push(i);
		}
		return result;
	}
	
	public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
		/*
		 * Leet-code 496
		 */
		/*
		 * This approach uses Monotonic stack with HashMap
		 * Initializing result array, map to store the next
		 * greater element of an integer from nums2. checks is
		 * a stack that pushes that element on the stack to check.
		 */
		int[] result = new int[nums1.length];
		HashMap<Integer, Integer> greaters = new HashMap<>();
		Stack<Integer> checks = new Stack<>();
		for (int i = 0; i < nums2.length; i++) {
			int num = nums2[i];
			/*
			 * Here we check if the stack is not empty and the
			 * element we pushed on the stack is smaller than
			 * our current element, if yes we update the map with
			 * the element in the stack as key and current element
			 * as its greater element as the value.
			 */
			while (!checks.isEmpty() && checks.peek() < num) {
				greaters.put(checks.peek(), num);
				checks.pop();
			}
			checks.push(num);
		}
		/*
		 * This loop just traverses the nums1 array and check if
		 * each element occurs in the map or not, if yes we put its
		 * greater element in result array and -1 if it doesn't have one.
		 */
		for (int j = 0; j < nums1.length; j++) {
			int n = nums1[j];
			if (greaters.containsKey(n))
				result[j] = greaters.get(n);
			else
				result[j] = -1;
		}
		return result;
	}

}
