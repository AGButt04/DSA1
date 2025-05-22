package ArraysANDMath;

public class MathProblems {
	
	public static void main(String[] args) {
		int[] nums = {2,3,4,2,1};
		System.out.println(myPow(2,-2));
//		for (int i = 0; i < nums.length-1; i++) {
//			System.out.println("Hello");
//		}
	}
	
	public static double myPow(double x, int n) {
		double power = 1.0;
		int num = Math.abs(n);
		while (num > 0) {
			power *= x;
			num--;
		}
		if (n < 0)
			power = 1/power;
		return power;
	}
	
	public static int trailingZeroes(int x) {
		int count  = 0;
		while (x > 0) {
			x /= 5;
			count += x;
		}
		return count;
	}
	
	public static int sqrt(int x) {
		for (int i = 1; i <= x/2; i++) {
			int cal = i * i;
			if (cal == x) 
				return i;
			else if (cal > x)
				return i - 1;
		}
		return -1;
	}
	
	public static int jump(int[] nums) {
        int jumps = 0;
        int current = 0;
        int maxJump = 0;
        for (int i = 0; i < nums.length-1; i++) {
        	maxJump = Math.max(maxJump, nums[i] + i);
        	if (i == current) {
        		current = maxJump;
        		jumps++;
        	}
        }  
        return jumps;
    }
	
	public static boolean Jump(int[] nums) {
		int check = nums.length-1;
		for (int i = nums.length-1; i >= 0; i--) {
			if (i + nums[i] >= check)
				check = i;
		}
		return check == 0;
	}
	public static boolean isPalindrome(int x) {
		String num = String.valueOf(x);
		int left = 0;
		int right = num.length()-1;
		while (left < right) {
			if (num.charAt(left) == num.charAt(right)) {
				left++;
				right--;
			} else
				return false;
		}
		return true;
	}
	
	public static int[] plusOne(int[] digits) {
		for (int i = digits.length-1; i >= 0; i--) {
			if (digits[i] < 9) {
				digits[i] = digits[i] + 1;
				return digits;
			}
			digits[i] = 0;
		}
		
		digits = new int[digits.length+1];
		digits[0] = 1;
		return digits;
	}
}
