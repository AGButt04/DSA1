package ArraysANDMath;

import java.util.HashMap;

public class SlidingWindow {

	public static void main(String[] args) {
		int[] nums = {2,3,1,2,4,3};
		int len = lengthOfLongestSubstring("abcabcbb");
		System.out.println(len);
	}
	
	public static int lengthOfLongestSubstring(String s) {
		/*
		 * Leet-code 3
		 */
		// Using sliding window with left/right pointers, and a HashMap
		// to keep track of the index where duplicate character occurred.
		int left = 0, right = 0, max = 0;
		HashMap<Character, Integer> map = new HashMap<>();
		
		// Loop until right pointer is smaller than length
		while (right < s.length()) {
			char c = s.charAt(right);
			/*
			 * First, check if this character is in HashMap, means
			 * duplicate, then we shrink the window by moving left
			 * pointer to the value of that character (index where it occurred LAST) 
			 * plus 1 so that it skips that character.
			 */
			if (map.containsKey(c))
				left = Math.max(left, map.get(c) + 1);
			
			/*
			 * We check max at each iteration to see if the window size got bigger
			 * put the character with its most recent index and move right++
			 */
			max = Math.max(max, (right-left)+1);
			map.put(c, right);
			right++;
		}
		
		return max;
	}
	
	public static int minSubarraylen(int[] nums, int target) {
		/*
		 * Leet-code 209
		 */
		
		// Initialize sum, min, and left/right pointers for sliding window
		int sum = 0, left = 0, right = 0, min = Integer.MAX_VALUE;
		
		// The loop will run till right is smaller than nums's length
		while (right < nums.length) {
			// Add right element to the sum, expanding window
			sum += nums[right];
			
			// If sum >= target, we gotta check if we can shrink the window
			// moving left pointer forward. If yes, update minimum length
			while (sum >= target) {
				min = Math.min(min, (right-left) + 1);
				sum -= nums[left];
				left++;
			}
			
			right++;
		}
		
		return min ==  Integer.MAX_VALUE? 0 : min;
	}
	
	public static double findMaxAverage(int[] nums, int k) {
		/*
		 * Leet-code 643
		 */
		// Calculate the first sum with length k.
		int sum = 0;
		for (int i = 0; i < k; i++) {
			sum += nums[i];
		}
		// Initialize max with first average.
		double max = sum / k;
		
		// Sliding window and update
		for (int i = 1; i <= nums.length-k; i++) {
			sum = sum - nums[i-1] + nums[i+k-1]; // The formula for calculating next sum
			max = Math.max(max, (double) sum/k); // Max of current and previous
		}
		
		return max;
	}

}
