package ArraysANDMath;

import java.util.HashMap;

public class PrefixSum {

	public static void main(String[] args) {
		int[] nums = {1, 2, 3, 4, 5};
		NumArray(nums);
		for (int n : array) {
			System.out.print(n + " ");
		}
		System.out.println();
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
