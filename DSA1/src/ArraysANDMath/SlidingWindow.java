package ArraysANDMath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class SlidingWindow {

	public static void main(String[] args) {
		int[] nums = {1, 2, 1, 0, 1, 1, 0};
//		int len = lengthOfLongestSubstring("abcabcbb");
//		System.out.println(len);
		String s = "barfoofoobarthefoobarman";
		String[] words = {"bar", "foo", "the"};
		System.out.println(longestLength(nums, 4));
//		for (int max : maxes) {
//			System.out.print(max + " ");
//		}
//		System.out.println();
	}
	
	public static int findmaxAVG(int[] nums, int k) {
		/*
		 * 🧩 Problem:
		 * 	Given an array of integers nums and an integer k, 
		 * return the maximum average of any sub-array of length k.
		 */
		int maxSum = 0;
		int sum = 0;
		int left = 0;
		int right = 0;
		while (right < nums.length) {
			sum += nums[right];
			while (right - left + 1 > k) {
				sum -= nums[left];
				left++;
			}
			if (right - left + 1 == k)
				maxSum = Math.max(sum, maxSum);
			right++;
		}
		return maxSum / k;
	}
	
	
	
	public static int longestLength(int[] nums, int k) {
		/*
		 * 🧩 Problem:
		 * You are given an array of positive integers and an integer k.
		 * Return the length of the longest sub-array whose sum is less than or equal to k.
		 */
		int maxLength = 0;
		int left = 0;
		int right = 0;
		int sum = 0;
		while (right < nums.length) {
			sum += nums[right];
			
			while (sum > k) {
				int num = nums[left];
				sum -= num;
				left++;
			}
			maxLength = Math.max(maxLength, right - left + 1);
			right++;
		}
		return maxLength;
	}
	
	public static int longestSubarrayofOnes(int[] nums) {
		/*
		 * Leet-code 1493 (Medium)
		 */
		// Initializing maxLen to keep track of max length of sub-array
		// zero_count to count how many zeros have occurred
		// left pointer to shrink the window
		int maxLen = 0;
		int zero_count = 0;
		int left = 0;
		for (int right = 0; right < nums.length; right++) {
			// This would check if current element is zero and increment count
			if (nums[right] == 0)
				zero_count++;
			// Our condition is removing one element, so if more than 1 zeros
			// then window becomes invalid and we start shrinking it.
			while (zero_count > 1) {
				if (nums[left] == 0)
					zero_count--;
				left++;
			}
			// update the max length in each iteration
			// right - length will give us right answer for this problem as it is
			// guaranteed that we have to delete one element whether that's zeros or ones
			maxLen = Math.max(maxLen, right-left);
		}
		return maxLen;
	}
	
	public static int subarraysWithMostK(int[] nums, int k) {
		/*
		 * Helper function for leet-code 992
		 */
		/*
		 * This would store the integers and their counts in HashMap
		 * to track where we are, and what is their frequencies.
		 */
		HashMap<Integer, Integer> map = new HashMap<>();
		int left = 0, count = 0;
		for (int right = 0; right < nums.length; right++) {
			// We can put the element in the map with its updated count
			map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);
			// And as soon as window becomes invalid as more than k elements
			while (map.size() > k) {
				// We go into loop and clean the map removing
				// elements at left index (starting of window).
				int elem = nums[left];
				map.put(elem, map.get(elem) - 1);
				if (map.get(elem) == 0)
					map.remove(elem);
				// We keep shrinking window till we have a valid window with map's size <= k
				left++;
			}
			// Increment the count with the length of the sub-array that is
			// between the right and left indexes + 1.
			count += (right - left)  + 1;
		}
		// return the count
		return count;
	}
	
	public static int subarraysWithKDistinct(int[] nums, int k) {
		/*
		 * Leet-code 992 (Hard)
		 */
		
		/*
		 * Finding sub-arrays with exactly k elements mean, sub-arrays
		 * with at most k elements i.e all sub-arrays with elements less
		 * than k. We can subtract this value from sub-arrays with (k-1)
		 * sub-arrays, as that would subtract all sub-arrays where different 
		 * elements were 1,2,3,...(k-1).
		 * We can write a helper function for that that would find at-most K 
		 * elements, and we can just call it twice.
		 */
		int allcount = 0;
		allcount = subarraysWithMostK(nums, k) - subarraysWithMostK(nums, k - 1);
		return allcount;
	}
	
	public int longestSubarrayOptimized(int[] nums, int limit) {
		// Store INDICES in heaps, not values - this enables lazy deletion
	    // Max heap: indices sorted by their values in descending order
	    PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> nums[b] - nums[a]);
	    // Min heap: indices sorted by their values in ascending order  
	    PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> nums[a] - nums[b]);
	    
	    int left = 0;
	    int maxLength = 0;
	    
	    for (int right = 0; right < nums.length; right++) {
	        // Add current index to both heaps
	        maxHeap.offer(right);
	        minHeap.offer(right);
	        
	        // Check if current window violates the limit condition
	        // We need to clean invalid indices first before checking
	        while (true) {
	            // Remove indices that are outside current window from max heap
	            while (!maxHeap.isEmpty() && maxHeap.peek() < left) {
	                maxHeap.poll();
	            }
	            // Remove indices that are outside current window from min heap
	            while (!minHeap.isEmpty() && minHeap.peek() < left) {
	                minHeap.poll();
	            }
	            
	            // Now check if current window is valid
	            if (maxHeap.isEmpty() || minHeap.isEmpty()) break;
	            
	            int maxVal = nums[maxHeap.peek()];
	            int minVal = nums[minHeap.peek()];
	            
	            // If window is valid, break out of while loop
	            if (maxVal - minVal <= limit) {
	                break;
	            }
	            
	            // Window is invalid, shrink it by moving left pointer
	            left++;
	        }
	        
	        // Update maximum length found so far
	        maxLength = Math.max(maxLength, right - left + 1);
	    }
	    
	    return maxLength;
	}
	
	public static int longestSubarray(int[] nums, int limit) {
		/*
		 * Leet-code 1438 (Medium)
		 */
		// Min-heap to keep track of minimum value of the window
		// Max-heap to keep track of maximum value of the window
		PriorityQueue<Integer> minHeap = new PriorityQueue<>();
		PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

		// Left pointer to shrink the window
		// MaxLength to keep track of max length of each window
		int left = 0, maxLength = 0;
		
		for (int right = 0; right < nums.length; right++) {
			// Adding the current element into both heaps
			maxHeap.offer(nums[right]);
			minHeap.offer(nums[right]);
			
			// And check if the max and min values of the  
			// current window is more than the limit
			int check = maxHeap.peek() - minHeap.peek();
			
			// If yes, then we start shrinking the window from the
			// left side, removing invalid elements from the window
			while (check > limit) {
				minHeap.remove(nums[left]);
				maxHeap.remove(nums[left]);
				// After removing the element, we gotta check again to see
				// if the current window is still invalid or not. and move left++
				check = maxHeap.peek() - minHeap.peek();
				left++;
			}
			// Update maxLength at each iteration
			maxLength = Math.max(maxLength, (right-left) + 1);
		}
		// return length
		return maxLength;
	}
	
	public static int longestOnes(int[] nums, int k) {
		/*
		 * Leet-code 1004 (Medium)
		 */
		
		/*
		 * Initialize maxLength to keep track of max Length so far
		 * zero_count to count how many zeros in the current window
		 * left pointer to shrink window to the right to exclude zeros
		 */
		int maxLength = 0;
		int zero_count = 0;
		int left = 0;
		
		for (int right = 0; right < nums.length; right++) {
			// We check if we encountered a zero, then increment count
			if (nums[right] == 0)
				zero_count++;
			
			// As soon as our window becomes invalid as more zeros than
			// k, then we start shrinking the window to exclude those over zeros
			while (zero_count > k) {
				if (nums[left] == 0)
					zero_count--;
				left++;
			}
			
			// Find the max length at each iteration
			maxLength = Math.max(maxLength, (right-left)+1);
		}
		
		// return max
		return maxLength;
	}
	
	public static List<Integer> findSubstring(String s, String[] words) {
		List<Integer> indexes = new ArrayList<>();
		HashMap<String, Integer> wordMap = new HashMap<>();
		int wordLen = words[0].length();
		int totalLen = words.length * wordLen;

		// Build frequency map for target words
		for (String word : words) {
		   wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
		}
		
		// Try all possible starting offsets (0 to wordLen-1)
		for (int offset = 0; offset < wordLen; offset++) {
		   HashMap<String, Integer> window = new HashMap<>();
		   int left = offset;
		   
		   // Slide window by wordLen steps starting from this offset
		   for (int right = offset; right <= s.length() - wordLen; right += wordLen) {
			   
		       // Add word at right position to window
		       String rightWord = s.substring(right, right + wordLen);
		       window.put(rightWord, window.getOrDefault(rightWord, 0) + 1);  
		       
		       // If window is too big, shrink from left
		       if (right - left + wordLen > totalLen) {
		           String leftWord = s.substring(left, left + wordLen);
		           window.put(leftWord, window.get(leftWord) - 1);
		           if (window.get(leftWord) == 0) {
		               window.remove(leftWord);
		           }
		           left += wordLen;
		       }
		       
		       // Check if current window matches target
		       if (window.equals(wordMap)) {
		           indexes.add(left);
		       }
		   }
		}
		return indexes;
	}
	
	public static int[] maxSlidingWindow(int[] nums, int k) {
		/*
		 * Leet-code 239 (Hard)
		 */	
		// Initialize maxHeap to keep track of the max and its index
		// maxes array to return the desired array, and its index.
		int[] maxes = new int[nums.length - k + 1];
		PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[0] - a[0]);
		int ind = 0;
		// Loop through first k elements of and add them to heap with their index. 
		for (int i = 0; i < k; i++) {
			maxHeap.offer(new int[] {nums[i], i});
		}
		maxes[ind] = maxHeap.peek()[0];
		ind++;
		// Next loop to move window and adding max at each iteration
		for (int j = k; j < nums.length; j++) {
			int newElem = nums[j];
			int left = j - k + 1;
			maxHeap.offer(new int[] {newElem, j});
			// We have to clean the array until we have k elements 
			// so that it does not violate k property of the window
			while (!maxHeap.isEmpty() && maxHeap.peek()[1] < left) {
				maxHeap.poll();
			}
			maxes[ind] = maxHeap.peek()[0];
			ind++;
		}
		// RETURN MAXES
		return maxes;
	}
			
	
	public static String minWindow(String s, String t) {
		/*
		 * Leet-code 76 (Hard)
		 */
		// HashMaps to store the characters and their frequencies for both strings
		HashMap<Character, Integer> smap = new HashMap<>();
		HashMap<Character, Integer> tmap = new HashMap<>();
		
		// Populating tmap with the characters in t and their frequencies
		for (int i = 0; i < t.length(); i++) {
			char c = t.charAt(i);
			tmap.put(c, tmap.getOrDefault(c,0) + 1);
		}
		
		/*
		 * left and right pointers for the window sliding
		 * minLength to keep track of minimum window
		 * current-matches to keep track of how many t characters
		 * have we seen and does their frequencies match. 
		 * required-matches is just our tmap's size - the number of elements
		 * minStart and minEnd to keep track of minimum windows' start,end indexes
		 */
		int left = 0, right = 0;
		int minLength = Integer.MAX_VALUE;
		int currentmatches = 0;
		int requiredmatches = tmap.size();
		int minStart = 0, minEnd = 0;
		
		while (right < s.length()) {
			/*
			 * We update each character in smap, and check if current character
			 * is in tmap and has same frequency, if yes, we got one match.
			 */
			char ch = s.charAt(right);
			smap.put(ch, smap.getOrDefault(ch, 0) + 1);
			if (smap.containsKey(ch) && smap.get(ch).equals(tmap.get(ch)))
				currentmatches++;
			
			/*
			 * Main-logic loop: This loop will start running when the current-matches
			 * are equal or greater than tmap's size. And we keep on shrinking window until
			 * we have terminate the property which is tmap should be in smap.
			 */
			while (currentmatches >= requiredmatches) {
				int currLength = (right-left)+1;
				// If this window's length is smaller, update that and pointers
				if (minLength > currLength) {
					minStart = left;
					minEnd = right;
					minLength = currLength;
				}
				char c = s.charAt(left);
				// If this character is in smap and has same frequency, decrement current-matches.
				if (tmap.containsKey(c) && smap.get(c).equals(tmap.get(c))) {
					currentmatches--;
				}
				// Update smap and shrink the window by moving left.
				smap.put(c, smap.get(c) - 1);
				left++;
			}
			right++;
		}
		// return "" if we couldn't find one else substring from minStart and minEnd indexes.
		return minLength == Integer.MAX_VALUE ? "" : s.substring(minStart, minEnd+1);
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
