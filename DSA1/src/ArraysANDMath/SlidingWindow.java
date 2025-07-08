package ArraysANDMath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class SlidingWindow {

	public static void main(String[] args) {
		int[] nums = {1,3,-1,-3,5,3,6,7};
//		int len = lengthOfLongestSubstring("abcabcbb");
//		System.out.println(len);
		String s = "barfoofoobarthefoobarman";
		String[] words = {"bar", "foo", "the"};
		List<Integer> maxes = findSubstring(s, words);
		for (int max : maxes) {
			System.out.print(max + " ");
		}
		System.out.println();
	}
	
	public static int longestOnes(int[] nums, int k) {
		
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
