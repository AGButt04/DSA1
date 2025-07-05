package ArraysANDMath;

import java.util.HashMap;

public class SlidingWindow {

	public static void main(String[] args) {
		int[] nums = {2,3,1,2,4,3};
//		int len = lengthOfLongestSubstring("abcabcbb");
//		System.out.println(len);
		System.out.println(checkInclusion("ab", "eidbaooo"));
	}
	
	public static boolean checkInclusion(String s1, String s2) {
		/*
		 * Leet-code 567
		 */
		// HashMap to store character frequencies of s1 (target pattern)
		HashMap<Character, Integer> map1 = new HashMap<>();
		// HashMap to store character frequencies of current window in s2
		HashMap<Character, Integer> map2 = new HashMap<>();

		// Build frequency map for s1 - this is our target pattern
		for (int i = 0; i < s1.length(); i++) {
		   char c = s1.charAt(i);
		   map1.put(c, map1.getOrDefault(c, 0) + 1);
		}

		// Initialize first window in s2 with same size as s1
		for (int i = 0; i < s1.length(); i++) {
		   char c = s2.charAt(i);
		   map2.put(c, map2.getOrDefault(c, 0) + 1);
		}

		// Left pointer for sliding window (tracks character leaving the window)
		int left = 0;

		// Slide window through rest of s2, checking each window
		for (int i = s1.length(); i < s2.length(); i++) {
		   // Check if current window matches s1's character frequencies
		   if (map1.equals(map2))
		       return true;
		   
		   // Character leaving the window (left side)
		   char remove = s2.charAt(left);
		   // Character entering the window (right side)
		   char add = s2.charAt(i);
		   
		   // Remove character from left side of window
		   map2.put(remove, map2.getOrDefault(remove, 0) - 1);
		   
		   // Clean up: remove entry if count becomes zero
		   if (map2.get(remove) == 0)
		       map2.remove(remove);
		   
		   // Add character to right side of window
		   map2.put(add, map2.getOrDefault(add, 0) + 1);
		   
		   // Move left pointer forward to maintain window size
		   left++;
		}

		// Check the final window (edge case fix)
		return map1.equals(map2);
	}
	
	public static int characterReplacement(String s, int k) {
		/*
		 * Leet-code 424
		 */
		/*
		 *  Initialize HashMap to keep track of the frequency of each character.
		 *  left and right pointers for sliding window movement
		 *  max_freq and max_len to keep track of appropriate elements
		 */
		HashMap<Character, Integer> freq = new HashMap<>();
		int left = 0, right = 0, max_freq = 0, max_len = 0;
		
		while (right < s.length()) {
			char c = s.charAt(right);
			
			freq.put(c, freq.getOrDefault(c, 0) + 1);
			max_freq = Math.max(max_freq, freq.get(c));
			// Checking if out current window has more different characters
			// than out k value. If yes, then we have to shrink.
			if ((right-left)+1 - max_freq > k) {
				char lc = s.charAt(left);
				freq.put(lc, freq.getOrDefault(lc, 0) - 1);
				left++;
			}
			
			max_len = Math.max(max_len, (right-left)+1);
			right++;
		}
		return max_len;
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
