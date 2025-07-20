package Stacks;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class MonotonicStack {

	public static void main(String[] args) {
		int[] nums1 = {5,4,3,2,1};
		int[] heights = {2,1,5,6,2,3};
		int[] result = nextGreaterElements(nums1);
		for (int n : result) {
			System.out.print(n + " ");
		}
		System.out.println();
		System.out.println(largestRectangleArea(heights));
		StockSpanner stockSpanner = new StockSpanner();
		stockSpanner.next(100); // return 1
		stockSpanner.next(80);  // return 1
		stockSpanner.next(60);  // return 1
		stockSpanner.next(70);  // return 2
		stockSpanner.next(60);  // return 1
		stockSpanner.next(75);  // return 4
		stockSpanner.next(85);  // return 6
	}
	
	public static String removeDuplicates(String s) {
		/*
		 * Leet-code 316
		 */
		// Step 1: Count frequency of each character
		HashMap<Character, Integer> count = new HashMap<>();
		for (char c : s.toCharArray()) {
		    count.put(c, count.getOrDefault(c, 0) + 1);
		}

		// Step 2: Track which characters are already in result
		HashSet<Character> inResult = new HashSet<>();

		// Step 3: Build result using monotonic stack
		Deque<Character> stack = new ArrayDeque<>();

		for (char c : s.toCharArray()) {
		    // Decrease count (we're processing this character)
		    count.put(c, count.get(c) - 1);
		    
		    // If already in result, skip
		    if (inResult.contains(c)) continue;
		    
		    while (!stack.isEmpty() && stack.peek() > c && count.get(stack.peek()) > 0) {
		    	inResult.remove(stack.pop());
		    }
		    
		    // Add current character
		    stack.push(c);
		    inResult.add(c);
		}
		
		StringBuilder result = new StringBuilder();
		while (!stack.isEmpty()) {
		    result.append(stack.removeLast());
		}
		return result.toString();
	}
	
	public static int largestRectangleArea(int[] heights) {
		/*
		 * Leet-code 84 (hard)
		 */
		/*
		 * Here we are using stack to hold the indexes in the increasing
		 * order of heights, not the indexes, and initializing maxArea = 0.
		 */
		Deque<Integer> st = new ArrayDeque<>();
		int maxArea = 0;
		for (int i = 0; i < heights.length; i++) {
			/*
			 * This loop will check if the current element is smaller than
			 * the top element of the stack then we just calculate the area
			 * of that top element (bar) of the array and store it if its bigger.
			 */
			int h = heights[i];
			while (!st.isEmpty() && h < heights[st.peek()]) {
				/*
				 * left would be the left boundary which will give us width,
				 * if stack is empty then left boundary is -1.
				 */
				int left = 0;
				int index = st.pop();
				if (st.isEmpty())
					left = -1;
				else
					left = st.peek();
				// Area = height * (width = current index - left_boundary - 1)
				int area = heights[index] * (i - left - 1);
				maxArea = Math.max(area, maxArea);
			}
			st.push(i);
		}
		/*
		 * Here, we check if the stack is empty with the indexes, if not
		 * then we do the same thing for the remaining element with right
		 * boundary = heights.length, means it can't be wider than that.
		 */
		while (!st.isEmpty()) {
			int height = heights[st.pop()];
			int left = 0;
			if (st.isEmpty())
				left = -1;
			else
				left = st.peek();
			int area = height * (heights.length - left - 1);
			maxArea = Math.max(maxArea, area);
		}
		
		return maxArea;
	}
	
	public static int[] nextGreaterElements(int[] nums) {
		/*
		 * Leet-code 503 (medium)
		 */
		/*
		 * This is the second version of nextGreaterElements, where we
		 * are treating the array as a circular array and the next greater
		 * element can be after or before the current element.
		 */
		Deque<Integer> st = new ArrayDeque<>();
		int[] result = new int[nums.length];
		Arrays.fill(result, -1); // Filling the array with -1s as default
		
		/*
		 * The loop is going to run twice the length of array as it checks
		 * whole array for the next greater element. 
		 */
		for (int i = 0; i < 2 * nums.length; i++) {
			/*
			 * We can use modulo operator to get the required
			 * index and can use the stack again to see if it has
			 * the greater element.
			 */
			int num = nums[i % nums.length];
			while (!st.isEmpty() && nums[st.peek()] < num) {
				// If there is a greater element then we pop the
				// stack for index and put the current element in the result.
				int n = st.pop();
				result[n] = num;
			}
			// This makes sure that we just push the indexes once while iterating.
			if (i < nums.length)
				st.push(i);
		}
		return result;
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
	
	static class StockSpanner {
		/*
		 * Leet-code 901
		 */
		
		class pair {
			int key;
			int value;
			public pair(int k, int v) {
				this.key = k;
				this.value = v;
			}
		}
		
		Deque<pair> stack;
		
	    public StockSpanner() {
	        stack = new ArrayDeque<>();
	    }
	    
	    public int next(int price) {
			int span = 1;
			while (!stack.isEmpty() && stack.peek().key <= price) {
				pair prev = stack.pop();
				span += prev.value;
			}
			stack.push(new pair(price, span));
			return span;
	    }
	}
}
