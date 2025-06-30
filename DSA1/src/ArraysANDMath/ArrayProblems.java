package ArraysANDMath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class ArrayProblems {

	public static void main(String[] args) {
//		String s = "abcd";
//		String t = "pq";
//		System.out.println(mergeAlternately(s,t));
//		int[][] grid = {
//				{3,1,2,2},
//				{1,4,4,5},
//				{2,4,2,2},
//				{2,4,2,2}};
//		System.out.println(equalPairs2(grid));
//		for (boolean b : result) {
//			System.out.print(b + ",");
//		}
//		System.out.println("]");
		
		String[] strs = {"flower","flow","flight"};
		System.out.println(longestCommonPrefix(strs));
	}
	
	public static String longestCommonPrefix(String[] strs) {
		return "";
	}
	
	public static int equalPairs2(int[][] grid) {
		int pairs = 0, len = grid.length;
		HashMap<List<Integer>, Integer> Rmap = new HashMap<>();
		for (int i = 0; i < len; i++) {
			List<Integer> arr = new ArrayList<>();
			for (int j = 0; j < len; j++) {
				arr.add(grid[i][j]);
			}
			Rmap.put(arr, Rmap.getOrDefault(arr, 0) + 1);
		}
		System.out.println(Rmap);
		for (int k = 0; k < len; k++) {
			List<Integer> arr1 = new ArrayList<>();
			for (int l = 0; l < len; l++) {
				arr1.add(grid[l][k]);
			}
			if (Rmap.containsKey(arr1))
				pairs += Rmap.get(arr1);
		}
		
		return pairs;
	}
	
	public static int equalPairs(int[][] grid) {
		int pairs = 0;
		int i,j,k;
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid.length; j++) {
				for (k = 0; k < grid.length; k++) {
					if (grid[i][k] != grid[k][j]) {
						break;
					}
				}
				if (k == grid.length)
					pairs++;
			}
		}
		return pairs;
	}
	
	public static String mergeAlternately(String word1, String word2) {
		StringBuilder str = new StringBuilder();
		int count = word1.length() + word2.length();
		int i = 0, w1 = 0, w2 = 0;
		while (i < count) {
			if (w1 < word1.length())
				str.append(word1.charAt(w1));
			if (w2 < word2.length())
				str.append(word2.charAt(w2));
			w1++;w2++;i++;
		}
		return str.toString();
	}
	
	public static String gcdOfStrings(String str1, String str2) {
		if (!(str1 + str2).equals(str2 + str1))
			return "";
		int Gcd = GDC(str2.length(), str1.length());
		String str = str1.substring(0, Gcd);
		return str;
	}
	
	private static int GDC(int num1, int num2) {
		while (num2 != 0) {
			int gcd = num1 % num2;
			num1 = num2;
			num2 = gcd;
		}
		return num1;
	}

	public static List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
		Boolean[] result = new Boolean[candies.length];
		int max = 0;
		for (int i = 0; i < candies.length; i++) {
			max = Math.max(max, candies[i]);
		}
		
		for (int i = 0; i < candies.length; i++) {
			result[i] = (candies[i] + extraCandies >= max)? true : false;
		}
		return Arrays.asList(result);
	}
	public static boolean Palindrome(String s) {
		s = s.replaceAll("\\p{Punct}", "");
		s = s.toLowerCase();
		s = s.replaceAll(" ", "");
		int right = s.length()-1;
		int left = 0;
		while (left < right) {
			if (s.charAt(left) == s.charAt(right)) {
				right--;
				left++;
			} else {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isSubsequence(String s, String t) {
		if (s.isEmpty())
			return true;
		if (!s.isEmpty() && t.isEmpty())
			return false;
		int S = 0;
		int T = 0;
		while (T < t.length()) {
			if (s.charAt(S) == t.charAt(T)) {
				S++;
				T++;
			} else
				T++;
			if (S == s.length())
				return true;
		}
		return false;
	}
	
	public static int majorityElem(int[] nums) {
		HashMap<Integer,Integer> numT = new HashMap<>();
		for (int num : nums) {
			numT.put(num, numT.getOrDefault(num, 0) + 1);
			if (numT.get(num) > (nums.length)/2)
				return num;
		}
		return -1;
	}
	
	public static int remove3Duplicates(int[] nums) {
		int index = 1;
        int count = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i-1]) {
                nums[index] = nums[i];
                index++;
                count = 0;
            } else {
                count++;
                if (count < 2) {
                    nums[index] = nums[i];
                    index++;
                }
            }
        }
        return index;
	}
	
	public static String validPath(String s) {
		String[] str = s.split("/");
		Stack<String> stack = new Stack<>();
		for (String st : str) {
			if (st.equals(".") || st.isEmpty()) {
				continue;
			} else if (st.equals("..")) {
				if (!stack.isEmpty())
					stack.pop();
			} else {
				stack.push(st);
			}
		}
		return "/" + String.join("/", stack);
	}
	
	public static List<List<Integer>> Triples() {
		List<List<Integer>> triples = new ArrayList<>();
		int[] nums = {-1,0,1,2,-1,-4};
		Arrays.sort(nums);
		for (int i = 1; i < nums.length-2; i++) {
			int left = i;
			int right = nums.length-1;
			while (left < right) {
				int sum = nums[i-1] + nums[left] + nums[right];
				if (sum == 0) {
					List L = Arrays.asList(nums[i-1],nums[left],nums[right]);
					if (!triples.contains(L))
						triples.add(L);
					left++;
					right--;
				} else if (sum < 0) {
					left++;
				} else if (sum > 0) {
					right--;
				}
			}
		}
		return triples;
	}
}
