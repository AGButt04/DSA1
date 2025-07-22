package Stacks;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class MonotonicStack {

	public static void main(String[] args) {
		int[] nums = {2,3,3,1,2};
		int[] heights = {2,1,5,6,2,3};
		for (int n : nums) {
			System.out.print(n + " ");
		}
		System.out.println();
		String s = "(()";
		System.out.println(maxSumMinProduct(nums));
	}
	
	public static int maxSumMinProduct(int[] nums) {
		/*
		 * Leet-code 1856 (HARD)
		 */
		Deque<Integer> st = new ArrayDeque<>();
		long[] sums = new long[nums.length + 1];
		long minProduct = 0;
		
		for (int i = 0; i < nums.length; i++) {
			sums[i+1] = sums[i] + nums[i];
		}
		
		for (int i = 0; i < nums.length; i++) {
			int n = nums[i];
			while (!st.isEmpty() && nums[st.peek()] > n) {
				int idx = st.pop();
				int current = nums[idx]; //  This is the minimum of the range
				int right = i;
				int left = st.isEmpty()? -1 : st.peek();
				
				long sum = sums[right] - sums[left+1];
				long prod = sum * current;
				minProduct = Math.max(minProduct, prod);
			}
			st.push(i);
		}
		
		while (!st.isEmpty()) {
		    int idx = st.pop();
		    int current = nums[idx];
		    int right = nums.length;  // Use array length as right boundary
		    int left = st.isEmpty() ? -1 : st.peek();
		    
		    long sum = sums[right] - sums[left + 1];
		    long prod = sum * current;
		    minProduct = Math.max(minProduct, prod);
		}
		
		return (int) (minProduct % 1000000007);
	}
	
	public static int maxWidthRamp(int[] nums) {
		/*
		 * Leet-code 962 (Medium)
		 */
		/*
		 * Using two-pass approach where first pass will
		 * push the indexes in the decreasing order on the stack
		 * and second approach will calculate the width of the ramp.
		 */
		Deque<Integer> st = new ArrayDeque<>();
		int maxWidth = 0;
		
		for (int i = 0; i < nums.length; i++) {
			// If stack is empty (First iteration) OR the element
			// on the top of stack is less than current, push the index.
			if (st.isEmpty() || nums[st.peek()] > nums[i])
				st.push(i);
		}
		// This loop starts from the end and check the width of each number.
		for (int j = nums.length-1; j >= 0; j--) {
			/*
			 * This loop will keep popping the indexes off of the stack while
			 * the nums element at the top is less or equal to the current element.
			 */
			while (!st.isEmpty() && nums[st.peek()] <= nums[j]) {
				// We pop the index and that would be our width.
				int i = st.pop();
				maxWidth = Math.max(maxWidth, j - i);
			}
		}
		return maxWidth;
	}
	
	public static int longestValidParentheses(String s) {
		/*
		 * Leet-code 32 (Hard)
		 */
		/*
		 * Here, we are finding the length of the longest valid
		 * parenthesis substring, for that we have a Deque, that
		 * will store the indices of the unmatched brackets(barriers), 
		 * with the variable maxLength that tracks the max length found.
		 */
		Deque<Integer> st = new ArrayDeque<>();
		int maxLength = 0;
		for (int i = 0; i < s.length(); i++) {
			/*
			 * This loop check if the stack is empty and if the current 
			 * character is a closing parenthesis or not, if it is, then we
			 * see if the character that is at the top of the stack is opening
			 * bracket, and else we just push the index of the character because
			 * that means we haven't found a match of the unmatched character.
			 */
			char c = s.charAt(i);
			if (!st.isEmpty() &&  c == ')' && s.charAt(st.peek()) == '(') {
				/*
				 * We pop the index, because it is matched now, and to calculate
				 * the length of the string --> len = (current index - st.peek()),
				 * because the stack's top now have the previous unmatched character
				 * that is our boundary, from where our valid string starts from.
				 */
				st.pop();
				int left = -1;
				if (!st.isEmpty()) // If the stack is empty, we put -1 as the left boundary.
					left = st.peek();
				int len = i - left;
				maxLength = Math.max(maxLength, len);
			} else {
				st.push(i);
			}
		}
		// return the max length found so far.
		return maxLength;
	}
	
	public static int maximalRectangle(char[][] matrix) {
		/*
		 * Leet-code 85 (Hard)
		 */
		int[] heights = new int[matrix[0].length];
		int maxArea = 0;
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < heights.length; j++) {
				if (matrix[i][j] == '1')
					heights[j]++;
				else
					heights[j] = 0;
			}
			
			int area = largestRectangleArea(heights);
			maxArea = Math.max(maxArea, area);
		}
		return maxArea;
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
