package Recursion;

import java.util.HashMap;

public class DP {
    public static void main(String[] args) {
        System.out.println(fib_tabulation(5));
        System.out.println(fib_memo(5));
        System.out.println(climbStairs(5));
        System.out.println(climbStairs_Tab(5));
    }

    public static int rob_Tabulation(int[] nums) {
        // Leet-code 193 (Tabulation)
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int[] money = new int[nums.length];
        money[0] = nums[0];
        money[1] = Math.max(nums[1], nums[0]);
        for (int i = 2; i < nums.length; i++) {
            int current_rob = nums[i] + money[i - 2];
            int skip_current = money[i - 1];
            money[i] = Math.max(current_rob, skip_current);
        }
        return money[money.length - 1];
    }
    public static int rob(int[] nums) {
        /*
        Leet-code 193 (Memoized)
         */
        HashMap<Integer, Integer> memo = new HashMap<>();
        return rob(nums, 0, memo);
    }
    public static int rob(int[] nums, int index, HashMap<Integer, Integer> memo) {
        if (index >= nums.length) return 0;
        if (memo.containsKey(index)) return memo.get(index);

        // Option-1: Skip this house and go to next.
        int skipped = rob(nums, index+1, memo);
        // Option-2: Rob this house and expect best from 2 houses down
        int robbed = nums[index] + rob(nums, index+2, memo);

        int result = Math.max(skipped, robbed);
        memo.put(index, result);
        return result;
    }

    public static int climbStairs_Tab(int n) {
        // Leet-code 70 (Tabulation)
        int[] ways = new int[n+1];
        ways[0] = 1;
        ways[1] = 1;
        for (int i = 2; i <= n; i++) {
            ways[i] = ways[i-1] + ways[i-2];
        }
        return ways[n];
    }
    public static int climbStairs(int n) {
        // Leet-code 70 (Memoized)
        HashMap<Integer, Integer> memo = new HashMap<>();
        return climbStairs(n, memo);
    }
    public static int climbStairs(int n, HashMap<Integer, Integer> memo) {
        if (memo.containsKey(n)) return memo.get(n);
        if (n < 0) return 0;
        if (n == 0) return 1;

        int result = climbStairs(n-1, memo) + climbStairs(n-2, memo);
        memo.put(n, result);
        return result;
    }

    public static double fib_tabulation(int n) {
        // Fibonaaci with Tabulation
        double[] fiboncacci = new double[n+1];
        fiboncacci[0] = 0;
        fiboncacci[1] = 1;
        for (int i = 2; i <= n; i++) {
            fiboncacci[i] =  fiboncacci[i-1] + fiboncacci[i-2];
        }
        return fiboncacci[n];
    }
    public static double fib_memo(int n) {
        /*
        Memoized Fibonacci
         */
        HashMap<Integer, Double> map = new HashMap<>();
        map.put(0, 0.0); map.put(1, 1.0);
        return fib_memo(map, n);
    }
    public static double fib_memo(HashMap<Integer, Double> map, int n) {
        // Driver for fib memoized
        if (map.containsKey(n))
            return map.get(n);

        double result = fib_memo(map, n-1) + fib_memo(map, n-2);
        map.put(n, result);
        return result;
    }
    public static int fib(int n) {
        /*
        Leet-code 509 (Recursive Solution)
         */
        if (n == 0) return 0;
        if (n == 1) return 1;

        return fib(n-1) + fib(n-2);
    }


}
