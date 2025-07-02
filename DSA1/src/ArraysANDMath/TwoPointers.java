package ArraysANDMath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class TwoPointers {
	public static void main(String[] args) {
		int[] nums = {2,0,2,1,1,0};
		sortColors2(nums);
		for (int n : nums) {
			System.out.print(n + " ");
		}
		System.out.println();
	}
	
	public static void swap(int[] nums, int left, int right) {
		int temp = nums[left];
		nums[left] = nums[right];
		nums[right] = temp;
	}
	
	public static void sortColors2(int[] nums) {
		/*
		 * Leet-code 75
		 * Using Dutch National Flag technique
		 */
		int left = 0;
		int current = 0;
		int right = nums.length-1;
		/*
		 * Left points at where zero should go,
		 * right points at where 2s should go, and
		 * current where 1s should go, at the place
		 */
		while (current <= right) {
			/*
			 * We check if current element == 0. If yes, we swap with
			 * the element at left and if element == 2, swap with element
			 * at right index. And move pointers accordingly.
			 */
			if (nums[current] == 0) {
				swap(nums, left, current);
				left++; current++;
			} else if (nums[current] == 1) {
				current++;
			} else {
				swap(nums, right, current);
				right--;
			}	
		}
	}
	
	public static void sortColors(int[] nums) {
		int left = 0;
		int right = nums.length-1;
		while (left < right) {
			if (nums[left] == nums[right]) {
				left++;
				right--;
			} else if (nums[left] > nums[right]) {
				swap(nums, left, right);
				right--;
			} else {
				left++;
			}
		}
	}
	
	public static int numRescueBoats(int[] people, int limit) {
		/*
		 * Leet-code 881
		 */
		// Using greedy approach, sorting the array first.
		Arrays.sort(people);
		// Initialize the pointers and boats.
		int boats = 0;
		int left = 0;
		int right = people.length-1;
		/*
		 * We should check if our pointers sum is smaller or greater than limit.
		 * If smaller, then means we can fit these two people. boats++, and both pointers move.
		 * If no, right needs its own boat, so boats++ and right--;
		 */
		while (left <= right) {
			int sum = people[left] + people[right];
			if (sum <= limit) {
				boats++;
				left++; right--;
			} else
				boats++; right--;
		}
		return boats;
	}
	
	public static int removeDuplicates(int[] nums) {
		/*
		 * Leet-code 80
		 */
		// Edge case: If array length is smaller equal to 2.
		if (nums.length <= 2)
			return nums.length;
		/*
		 * Starting pointers with 2 index. Check if the fast pointer
		 * is not equal to slow pointer, copy and move slow++
		 */
		int slow = 2;
		int fast = 2;
		while (fast < nums.length) {
			if (nums[fast] != nums[slow-2]) {
				nums[slow] = nums[fast];
				slow++;
			}
			fast++;
		}
		return slow;
	}
	
	public static int threeSum(int[] nums, int target) {
		Arrays.sort(nums);
		int closest = nums[0] + nums[1] + nums[2];
		for (int i = 0; i < nums.length-1; i++) {
			int left = i+1;
			int right = nums.length-1;
			while (left < right) {
				int sum = nums[i] + nums[left] + nums[right];
				int check = Math.abs(sum - target);
				if (sum == target)
					return sum;
				else if (sum < target)
					left++;
				else
					right--;
				
				if (Math.abs(closest - target) > check)
					closest = sum;
			}
		}
		
		return closest;
	}
	
	public static boolean bs_compare(String s, String t) {
		return backSpace(s).equals(backSpace(t));
	}
	
	public static String backSpace(String s) {
		StringBuilder str = new StringBuilder();
		int backspaceCount = 0;
		for (int i = s.length()-1; i >= 0; i--) {
			char c = s.charAt(i);
			
			if (c == '#') {
				backspaceCount++;
			} else {
				if (backspaceCount > 0) {
					backspaceCount--;
				} else {
					str.append(c);
				}
			}
		}
		return str.reverse().toString();
	}
	
	public static int[] sortedSqs(int[] nums) {
		/*
		 * Leet-code 977
		 */
		int[] squares = new int[nums.length];
		int left = 0, last = nums.length-1;
		int right = last;
		int index = last;
		while (left <= right) {
			int leftsq = (int) Math.pow(nums[left], 2);
			int rightsq = (int) Math.pow(nums[right], 2);
			if (leftsq > rightsq) {
				squares[index] = leftsq;
				left++;
			} else {
				squares[index] = rightsq;
				right--;
			}
			index--;
		}
		return squares;
	}
	
	public static int trap(int[] height) {
		/*
		 * Leet-code 42 (Hard)
		 * We could use two pointers for this which will keep
		 * track of max values from left side and right side and 
		 * left and right indexes.
		 */
		int left = 0, right = height.length-1;
		int leftmax = 0, rightmax = 0, water = 0;
		while (left < right) {
			/*
			 * We have to update the left-max and right-max at the
			 * beginning of the loop to do correct calculations.
			 */
			if (height[left] > leftmax)
				leftmax = height[left];
			if (height[right] > rightmax)
				rightmax = height[right];
			/*
			 * Then we check which side is smaller and calculate the water
			 * in between left and right max values and move the smaller pointer
			 * as it will try to find a bigger value to decrease a limiting factor.
			 */
			if (height[left] < height[right]) {
				int check = Math.min(rightmax, leftmax) - height[left];
				if (check >= 0)
					water += check;
				left++;
			}
			else {
				int check = Math.min(rightmax, leftmax) - height[right];
				if (check >= 0)
					water += check;
				right--;
			}
		}
		
		return water;
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
