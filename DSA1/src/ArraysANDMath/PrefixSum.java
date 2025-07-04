package ArraysANDMath;

import java.util.HashMap;

public class PrefixSum {

	public static void main(String[] args) {
		int[] nums = {23, 2, 6, 4, 7};
		int[] answer = (nums);
		for (int n : answer) {
			System.out.print(n + " ");
		}
		System.out.println();
		System.out.println(checkSubarraySum(nums, 6));
	}
	
	public static boolean checkSubarraySum(int[] nums, int k) {
		// HashMap to store the remainder and the index where it first occurred
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0, -1); // Edge case as sum of zero exists at -1 index, meaning if prefixSum == k
		
		int prefix = 0;
		for (int i = 0; i < nums.length; i++) {
			/*
			 * We have to check if our remainder exists in HashMap. If it does, 
			 * that means the sub-array between the index of that rem and current
			 * is divisible by 6. Now we have to check if their length is >= 2.
			 * If yes, then we can return true.
			 */
			prefix += nums[i];
			int rem = prefix % k;
			if (rem < 0)
				rem += k;
			if (map.containsKey(rem)) {
				int len = i - map.get(rem);
				if (len >= 2)
					return true;
			} else
				map.put(rem, i);
		}
		
		return false;
	}
	
	private static int count = 0;
	
	public static void PathSumDFS(TreeNode root, HashMap<Integer, Integer> map, int prefix, int target) {
		if (root == null) return;
		
		/* 
		 * Check if prefix - target exists in our HashMap. If yes,
		 * then increment the count as target can be reached.
		 */
		prefix += root.val;
		if (map.containsKey(prefix - target)) {
			count += map.get(prefix - target);
		}
		
		// Have to put prefix in our map, and its count
		map.put(prefix, map.getOrDefault(prefix, 0) + 1);
		
		// Two recursive calls to explore left and right sub-trees
		PathSumDFS(root.left, map, prefix, target);
		PathSumDFS(root.right, map, prefix, target);
		
		/*
		 * After that, we have to backtrack and leave the situation as it was,
		 * by decrementing the prefix frequency and potentially removing the new prefix.
		 * Then have to update the prefix by subtracting the current node's value.
		 */
		map.put(prefix, map.get(prefix) - 1);
		if (map.get(prefix) == 0)
			map.remove(prefix);
		
		prefix -= root.val;
	}
	
	public static int pathSum(TreeNode root, int targetSum) {
		/*
		 * Initialize everything: HashMap to keep count of prefixes and
		 * prefix starting from zero. Have to initialize the map with {0 : 1}.
		 * The method call will update the class variable count and returns.
		 */
		int prefix = 0;
		count = 0;
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0, 1);
		PathSumDFS(root, map, prefix, targetSum);
		return count;
	}
	
	public static int subarraysDivByK(int[] nums, int k) {
		// HashMap to store the remainder that occurs and their counts.
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0, 1);
		// Edge case as zero remainder exists
		
		int count = 0;
		int prefix = 0;
		for (int i = 0; i < nums.length; i++) {
			prefix += nums[i];
			int rem = ((prefix % k) + k) % k;
			/*
			 * We should check if our remainder exists in map, if it
			 * does then we can increment the count by the count of that
			 * remainder.
			 */
			if (map.containsKey(rem))
				count += map.get(rem);
			
			// Updating the the count of the remainder
			map.put(rem, map.getOrDefault(rem, 0) + 1);
		}
		
		return count;
	}
	
	public static int numSubarrayswithSum(int[] nums, int goal) {
		// HashMap to store the prefix sum and count of its occurrence
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0, 1);
		// Edge case as zero prefix sum always exists
		
		int max = 0;
		int prefix = 0;
		for (int i = 0; i < nums.length; i++) {
			prefix += nums[i];
			/*
			 * We will see if the prefix-goal exists in the HashMap then 
			 * we will increment the max with the value of that calculation.
			 * The idea is if the current number at current index exists, and 
			 * current number - goal also exists in the map meaning can be made then we know our goal exists.
			 */
			if (map.containsKey(prefix-goal))
				max += map.get(prefix-goal);
			
			// Update the prefix's value if exists again.
			map.put(prefix, map.getOrDefault(prefix, 0) + 1);
		}
		
		return max;
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
	
	public class TreeNode {
		 int val;
		 TreeNode left;
		 TreeNode right;
		 TreeNode() {}
		 TreeNode(int val) { this.val = val; }
		 TreeNode(int val, TreeNode left, TreeNode right) {
			 this.val = val;
			 this.left = left;
			 this.right = right;
		 }
	}

}
