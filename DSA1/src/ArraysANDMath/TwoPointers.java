package ArraysANDMath;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class TwoPointers {
	public static void main(String[] args) {
		int[] nums = {1,2,4,3};
		int indexes = maxArea(nums);
		System.out.println(indexes);
	}
	
	public static int maxArea(int[] height) {
		int area = 0;
		int left = 0;
		int right = height.length-1;
		while (left < right) {
			int cal = 0;
			if (height[left] >= height[right]) {
				cal =  height[right] * (right-left);
				right--;
			} else {
				cal =  height[left] * (right-left);
				left++;
			}
			if (area < cal)
				area = cal;
		}
		return area;
	}
	
	public static int[] TwoSum(int[] numbers, int target) {
		int left = 0;
		int right = numbers.length-1;
		while (right > left) {
			int sum = numbers[left] + numbers[right];
			if (sum == target)
				return new int[]{left+1,right+1};
			else if (sum > target)
				right--;
			else
				left++;
		}
		return new int[]{};
		
	}
	public static HashMap romanNums() {
		HashMap<Character, Integer> romans = new HashMap<>();
		romans.put('I', 1);romans.put('V', 5);romans.put('X', 10);romans.put('L', 50);
		romans.put('C', 100);romans.put('D', 500);romans.put('M', 1000);
		return romans;
	}
	public static int romantoInt(String s) {
		int sum = 0;
		HashMap<Character, Integer> romans = romanNums();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (i + 1 < s.length() && romans.get(c) < romans.get(s.charAt(i+1)))
				sum -= romans.get(c);
			else
				sum += romans.get(c);
		}
		return sum;
	}
	
	public static String removeOccurences(String s, String part) {
		Stack<Character> st = new Stack<>();
		StringBuilder Str = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			st.push(c);
			
			while (st.size() >= part.length()) {
				boolean match = true;
				for (int j = 0; j < part.length(); j++) {
					if (st.get(st.size()-part.length()+j) != part.charAt(j)) {
						match = false;
						break;
					}
				}
				if (match) {
					for (int k = 0; k < part.length(); k++)
						st.pop();
				} else {
					break;
				}
			}
		}
		while (!st.isEmpty())
			Str.append(st.pop());
		
		return Str.reverse().toString();
	}
	
	public static void factorial() {
		int index = 1;
		double sum = 0;
		while (index <= 10) {
			double factorial = 1;
			for (int i = 1; i < index; i++) {
				factorial *= i;
			}
			sum += 1/factorial;
			index++;
		}
		System.out.println(sum);
	}
	
	public static void reverse(int[] nums, int start, int end) {
		while (start < end) {
			int num = nums[start];
			nums[start] = nums[end];
			nums[end] = num;
			start++;
			end--;
		}
	}
	public static void rotateArray(int[] nums, int k) {
		reverse(nums,0,nums.length-1);
		reverse(nums,0,k-1);
		reverse(nums,k,nums.length-1);
	}
	
	public static int strStr(String haystack, String needle) {
		for (int i = 0, j = needle.length(); j <= haystack.length(); j++,i++) {
			if (haystack.substring(i, j).equals(needle))
				return i;
		}
		return -1;
	}
	
	public static int LastWord(String s) {
		String[] str = s.split(" ");
		int len = str[str.length-1].length();
		return len;
	}
	
	public static void moveZeros(int[] nums) {
		int index = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] != 0) {
				int temp = nums[i];
				nums[i] = nums[index];
				nums[index] = temp;
				index++;
			}
		}
	}
	
	public static int minimum(int[] nums) {
		int buy = 0;
		for (int i = 0; i < nums.length;i++) {
			if (nums[i] < nums[buy]) {
				buy = i;
			}
		}
		return buy;
	}
	
	public static int maxProfit2(int[] nums) {
		int buy = minimum(nums);
		if (buy == nums.length-1)
			return 0;
		int profit = 0;
		for (int i = buy+1; i < nums.length; i++) {
			if (nums[i] > nums[buy]) {
				profit += nums[i] - nums[buy];
				buy = i;
			} else {
				buy = i;
			}
		}
		return profit;
	}
	
	public static int maxProfit(int[] nums) {
		int buy = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] < nums[buy])
				buy = i;
		}
		if (buy == nums.length-1)
			return 0;
		
		int sell = 0;
		for (int i = buy+1; i < nums.length; i++) {
			if (nums[i] - nums[buy] > sell) {
				sell = nums[i] - nums[buy];
			}
		}
		return sell;
	}
}
