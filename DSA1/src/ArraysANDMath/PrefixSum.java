package ArraysANDMath;

import java.util.HashMap;

public class PrefixSum {

	public static void main(String[] args) {
		int[] nums = {9,9,6,0,6,6,6};
		int[] answer = (nums);
		for (int n : answer) {
			System.out.print(n + " ");
		}
		System.out.println();
		System.out.println(longestWPI(nums));
	}
	
	public static int longestWPI(int[] hours) {
		// HashMap to store the prefix sums and their first occurrence.
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0, -1);
		//Edge case as 0 prefix exists at -1 index;
		
		int prefix = 0;
		int max = 0;
		for (int i = 0; i < hours.length; i++) {
			// We add +1 for value > 8 and -1 for value < 8 to prefix
			if (hours[i] > 8)
				prefix += 1;
			else
				prefix += -1;
			
			/*
			 * If the prefix > 0 (meaning more tiring days) then we know the
			 * length is i + 1. Then we check if our map does not contain that value.
			 * If not we add it, then last condition is the trick, we look to see that if our map
			 * contains (prefix-1), something smaller because if it does, then the sub-array between 
			 * those two indexes are bound to have positive sum as our current prefix is greate.
			 */
			
			if (prefix > 0)
				max = i + 1;
			else if (!map.containsKey(prefix))
				map.put(prefix, i);
			
			if (map.containsKey(prefix - 1)) {
				int curr = i - (map.get(prefix-1));
				max = Math.max(max, curr);
			}
		}
		
		return max;
	}
	
	public static int numberOfSubarray(int[] nums, int k) {
		// HashMap to store the the count of each prefix sum
		// Key: prefix sum value, Value: number of times seen
		HashMap<Integer, Integer> map = new HashMap<>();
		
		// Initialize the map with the edge case
		map.put(0, 1); 
		// As prefix sum value of 0 always occurs
		
		// Change odd values ->> 1 || Even values to ->> 0
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] % 2 == 0)
				nums[i] = 0;
			else
				nums[i] = 1;
		}
		
		// Check if prefix value - k exists in the HashMap
		int prefix = 0, count = 0;
		for (int i = 0; i < nums.length; i++) {
			prefix += nums[i];
			int check = prefix - k;
			if (map.containsKey(check)) {
				count += map.get(check);
			}
			
			map.put(prefix, map.getOrDefault(prefix, 0) + 1);
		}
		
		return count;
	
	}
	
	public static int findMaxLength(int[] nums) {
	   // HashMap to store first occurrence of each prefix sum
	   // Key: prefix sum value, Value: index where first seen
	   HashMap<Integer, Integer> map = new HashMap<>();
	   
	   // Initialize with sum 0 at imaginary index -1
	   // Handles case when entire sub-array from start has equal 0s and 1s
	   map.put(0, -1);
	   
	   // Transform 0s to -1s so equal 0s and 1s will sum to 0
	   // This converts the problem to "find longest sub-array with sum = 0"
	   for (int i = 0; i < nums.length; i++) {
	       if (nums[i] == 0) nums[i] = -1;
	   }
	   
	   int maxLength = 0;
	   int prefixSum = 0;
	   
	   // Build prefix sum and check for equal sums (indicating balanced sub-array)
	   for (int i = 0; i < nums.length; i++) {
	       prefixSum += nums[i];
	       
	       if (map.containsKey(prefixSum)) {
	           // Found same prefix sum before - sub-array between has sum = 0
	           // Length = current_index - first_occurrence_index
	           int length = i - map.get(prefixSum);
	           maxLength = Math.max(maxLength, length);
	       } else {
	           // First time seeing this prefix sum - store current index
	           // We only store first occurrence to maximize sub-array length
	           map.put(prefixSum, i);
	       }
	   }
	   
	   return maxLength;
	}
	
	public static int[] runningSum(int[] nums) {
		for (int i = 1; i < nums.length; i++) {
			nums[i] += nums[i-1];
		}
		return nums;
	}
	
	public static int[] productExceptSelf(int[] nums) {
	   int len = nums.length;
	   int[] left = new int[len];   // Store products of all elements to the left
	   int[] right = new int[len];  // Store products of all elements to the right
	   int[] answer = new int[len]; // Final result array
	   
	   // Base cases: no elements to the left of index 0, no elements to the right of last index
	   left[0] = 1;
	   right[len-1] = 1;
	   
	   // Build left products array (left to right)
	   // left[i] = product of all elements to the left of index i
	   for (int i = 1; i < len; i++) {
	       left[i] = left[i-1] * nums[i-1];
	   }
	   
	   // Build right products array (right to left)  
	   // right[i] = product of all elements to the right of index i
	   for (int i = len-2; i >= 0; i--) {
	       right[i] = right[i+1] * nums[i+1];
	   }
	   
	   // Combine left and right products for final answer
	   // answer[i] = (product of left elements) * (product of right elements)
	   for (int i = 0; i < len; i++) {
	       answer[i] = left[i] * right[i];
	   }
	   
	   return answer;
	}
	
	static int[] array;
	
	public static void NumArray(int[] nums) {
		array = new int[nums.length];
		array[0] = nums[0];
		for (int i = 1; i < nums.length; i++) {
			array[i] = nums[i] + array[i-1];
		}
	}
	
	public static int sumRange(int left, int right) {
		if (left == 0)
			return array[right];
		
		return array[right] - array[left - 1];
	}
	
	public static int pivotIndex(int[] nums) {
		int total_sum = 0;
		for (int num : nums) {
			total_sum += num;
		}
		
		int left_sum = 0;
		for (int i = 0; i < nums.length; i++) {
			int right_sum = total_sum - nums[i] - left_sum;
			if (right_sum == left_sum)
				return i;
			left_sum += nums[i];
		}
		
		return -1;
	}
	
	public static int subarraySum(int[] nums, int k) {
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int i = 1; i < nums.length; i++) {
			nums[i] += nums[i-1];
		}
		map.put(0, 1);
		int count = 0;
		for (int n : nums) {
			int check = n - k;
			if (map.containsKey(check))
				count += map.get(check);
			map.put(n, map.getOrDefault(n, 0) + 1);
		}
		
		return count;
	}

}
