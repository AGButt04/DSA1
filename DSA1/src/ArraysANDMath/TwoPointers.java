package ArraysANDMath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class TwoPointers {
	public static void main(String[] args) {
		int[] nums = {1,1,2};
		for (int n : nums) {
			System.out.print(n + " ");
		}
		System.out.println();
		System.out.println(removeDups(nums));
	}
	
	public static int removeDups(int[] nums) {
		/*
		 * Leet-code 23
		 */
		int slow = 0;
		int fast = 0;
		while (fast < nums.length) {
			if (nums[slow] == nums[fast])
				fast++;
			else {
				slow++;
				nums[slow] = nums[fast];
				fast++;
			}
		}
		return slow + 1;
	}
	
	public static void moveZeros(int[] nums) {
		/*
		 * Leet-code 283
		 */
		int slow = 0;
		int fast = 0;
		while (fast < nums.length) {
			if (nums[fast] != 0) {
				int temp = nums[slow];
				nums[slow] = nums[fast];
				nums[fast] = temp;
				fast++; slow++;
			} else {
				fast++;
			}
		}
	}
	
	public static List<List<Integer>> ThreeSum(int[] nums) {
		/*
		 * Leet-code 15
		 */
		if (nums.length < 3)
			return new ArrayList<>();
		List<List<Integer>> triples = new ArrayList<>();
		Arrays.sort(nums); //Sort the array to avoid duplicates
		for (int i = 0; i < nums.length; i++) {
			// Check if nums[i] and nums[i-1] are duplicates, skip if yes
			if (i > 0 && nums[i] == nums[i-1])
				continue;
			int left = i+1;
			int right = nums.length-1;
			// inner loop to check all possible triplets
			while (left < right) {
				int check = nums[i] + nums[left] + nums[right];
				if (check == 0) {
					List<Integer> triple = Arrays.asList(nums[i], nums[left], nums[right]);
					triples.add(triple);
					/*
					 * When we have found the valid triplet, we add it and then check
					 * if there are any duplicates and skip them using two while loops
					 */
					while(left < right && nums[left] == nums[left+1]) left++;
					while (left < right && nums[right] == nums[right-1]) right--;
					left++; right--;
				} else if (check < 0) {
					left++;
				} else {
					right--;
				}
			}
		}
		return triples;
	}
	
	public static int maxArea(int[] height) {
		/*
		 * Leet-code 11
		 */
		int left = 0;
		int right = height.length-1;
		int max = 0;
		while (left < right) {
			int h = Math.min(height[left], height[right]);
			int w = right - left;
			int area = h * w;
			if (max < area)
				max = area;
			if (height[left] < height[right])
				left++;
			else
				right--;
		}
		
		return max;
	}
	
	public static int[] twoSum(int[] numbers, int target) {
		/*
		 * Leet-code 167
		 */
		int left = 0;
		int right = numbers.length-1;
		while (left < right) {
			int sum = numbers[left] + numbers[right];
			if (sum == target)
				return new int[]{left+1,right+1};
			else if (sum < target)
				left++;
			else
				right--;
		}
		return new int[]{};
	}
	
	public static int[] sortedSquares(int[] nums) {
		int[] squares = new int[nums.length];
		int sq = nums.length-1;
		int left = 0;
		int right = nums.length-1;
		while (left <= right) {
			int leftsq = (int) Math.pow(nums[left],2);
			int rightsq = (int) Math.pow(nums[right],2);
			if (leftsq > rightsq) {
				squares[sq] = leftsq;
				left++;
			} else {
				squares[sq] = rightsq;
				right--;
			}
			sq--;
		}
		return squares;
	}
	
	public static boolean backspaceCompare(String s, String t) {
		return cleanedString(s).equals(cleanedString(t));
	}
	
	public static String cleanedString(String s) {
		StringBuilder str = new StringBuilder();
		int check = 0;
		for (int i = s.length()-1; i >= 0; i--) {
			char c = s.charAt(i);
			if (c == '#') {
				check++;
				continue;
			}
			if (check > 0) {
				check--;
			} else
				str.append(c);
		}
		return str.toString();
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
