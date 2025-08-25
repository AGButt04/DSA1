package Recursion;

import java.util.Arrays;
import java.util.HashMap;

public class DP {
    public static void main(String[] args) {
        System.out.println(fib_tabulation(5));
        System.out.println(fib_memo(5));
        System.out.println(climbStairs(5));
        System.out.println(climbStairs_Tab(5));
    }

    public static int maxSubarraySum(int[] nums) {
        /*
        Leet-code 53 (Medium)
        In this problem, we can use tabulation straight up recursive solution
        is going to be pretty critical. Here, we can make an dp array which will
        store the maximum subarray sums ending at the position i.
         */
        int[] maxSums  = new int[nums.length]; // Create a dp array where sums will be stored
        maxSums[0] = nums[0]; // Base case is the first element of the nums array
        int maxSum = nums[0]; // maxSum to keep track of the max of the dp array

        for (int i = 1; i < nums.length; i++) {
            /*
            The main logic is that at each position, we have two choices to
            make. One would to keep the running sum and just add the current
            element to it, or we can start the new running sum from nums[i].
            Of course, we will take the max value of both as that's the goal.
             */
            int prev_max_sum = maxSums[i - 1] +  nums[i]; // Add the nums[i] to previous max
            int next_max_sum = Math.max(prev_max_sum, nums[i]); // Max of current value alone and running sum

            // Put the bigger sum at the current index.
            maxSums[i] = next_max_sum;
            maxSum = Math.max(maxSum, next_max_sum); // Updating the maxSum at each iteration.
        }
        return maxSum;
    }

    public int longestCommonSubsequence(String text1, String text2) {
        /*
        Leet-code 1143 (Medium)
         */
        int m = text1.length(), n = text2.length();
        int[][] LCS = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) LCS[i][0] = 0;
        for (int j = 0; j <= n; j++) LCS[0][j] = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i-1) == text2.charAt(j-1)) {
                    LCS[i][j] = LCS[i-1][j-1] + 1;
                } else {
                    int max_LCS = Math.max(LCS[i-1][j], LCS[i][j-1]);
                    LCS[i][j] = max_LCS;
                }
            }
        }

        return LCS[m][n];
    }

    public static int uniquePaths_tabulation(int m, int n) {
        /*
        Leet-code 62 (Tabulation)
        In this problem, we are using tabulation to solve the uniquePaths in
        a (m x n) grid. The idea is we will use a m x n grid to compute small
        solutions step-by-step and eventually, we will make up the final solution.
         */
        // Paths grid where each cell will have the unique paths that
        // you can find in (i x j) grid, and eventually we will get (m x n).
        int[][] grid = new int[m][n];

        // The idea here is that we will fill the first column and first
        // row of the grid with ones, as there is only one way to traverse
        // the grids which either have 1 row or 1 column.
        for (int i = 0; i < m; i++)
            grid[i][0] = 1;
        for (int j = 0; j < n; j++)
            grid[0][j] = 1;

        // Scanning through the paths grid to fill in the remaining values.
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                /*
                Then the main logic says that the uniquePaths for any cells is the sum
                of the unique paths from its upper cell and left cell, because the problem
                says that you can only move right and down, so we will only sum those two cells.
                 */
                int sum = grid[i-1][j] + grid[i][j-1];
                grid[i][j] = sum; // Place the sum at that cell
            }
        }
        // Return the last index of the grid as that will have the unique paths for (m x n) grid.
        return grid[m-1][n-1];
    }

    public int uniquePaths(int m, int n) {
        /*
        Leet-code 62 (Memoization)
        This is the slow version but faster than the brute force recursion solution,
        as here we have a memo object which is our hashmap that will store the computations.
         */
        return uniquePaths(m, n, new HashMap<String, Integer>());
    }
    public int uniquePaths(int m, int n, HashMap<String, Integer> memo) {
        // We will store the String key in the map -> mapped to its unique paths
        String curr = m + "," + n;
        // First, we see if our current dimensions exist in our map or not.
        if (memo.containsKey(curr)) return memo.get(curr);
        // Base case: If the row and col == 1, then return 1
        // because there is only 1 way to traverse 1 x 1 grid
        if (m == 1 && n == 1) return 1;
        // If the dimension go beyond 0, then just return 0 invalid dimensions.
        if (m <= 0 || n <= 0) return 0;

        /*
        The idea here is that we can only go down and right, and we know that
        going right decreases the columns by 1 and going down decreases the
        rows by one, so that is what we will do and change the dimensions to
        eventually get to our base case which is 1 x 1 grid.
         */
        int right = uniquePaths(m, n - 1, memo);
        int down = uniquePaths(m - 1, n, memo);

        int result = right + down; // Add the results
        memo.put(curr, result); // Put the results with the key
        return result; // return the result.
    }

    public int coinChange_Tabulation(int[] coins, int amount) {
        /*
        Leet-code 322 (Tabulation)
        In this problem, we are using tabulation to solve this problem where we
        are trying to make the amount given with the coins given to us.
         */
        if (amount == 0) return 0; // Edge case, if amount == 0, just return 0

        /*
        Here, we will make a min_coins array which will be amount+1 in length and
        we will start filling it up till the amount index which will eventually
        have the answer to the amount given, and each index in the array will have
        the minimum coins that will sum up to that amount (index).
         */
        int[] min_coins = new int[amount+1];
        Arrays.fill(min_coins, amount+1); // We will fill array with (amount+1) as default
        min_coins[0] = 0; // We will put 0 at first index as 0 coins are needed to make 0.

        // Filling up the array by starting from the 1st index.
        for (int i = 1; i <= amount; i++) {
            // For each index, we will go over all the coins to get the
            // minimum coins needed to make this i amount, which will help.
            for (int coin : coins) {
                // Remaining amount left after subtracting this current coin from i amount.
                int remaining_amount = i - coin;
                // If the remaining is -ve, then don't do anything.
                if (remaining_amount >= 0) {
                    // Then the next minimum would be the minimum of the current minimum that
                    // is at the index i in array, and minimum we calculated which is
                    // min_coins[remaining_amount]: Get the minimum coins for this element from array
                    // Add one to it because that will include the current coin we're considering.
                    int next_min = Math.min(min_coins[i], min_coins[remaining_amount]+1);
                    min_coins[i] = next_min; // Put the minimum of these at the current index.
                }
            }
        }
        // Return the min_coins[amount] if its is not == to amount+1, which
        // was default, if that did not change that means, we couldn't find
        // the solution, so return -1 in that case.
        return min_coins[amount] == amount+1? -1 : min_coins[amount];
    }

    public static int coinChange(int[] coins, int amount) {
        /*
        Leet-code 322 (Memoized)
        This is the memoized version of the coinChange which is implemented
        on the same concept of going over all the coins and find the minimum.
         */
        int result = coinChangeHelper(coins, amount, new HashMap<Integer, Integer>());
        return result == Integer.MAX_VALUE? -1 : result;
    }
    public static int coinChangeHelper(int[] coins, int amount, HashMap<Integer, Integer> memo) {
        if (amount == 0) return 0; // If we found it return 0.
        if (amount < 0) return Integer.MAX_VALUE; // If went beyond, return Integer.MAX_VALUE.
        if (memo.containsKey(amount)) return memo.get(amount); // Added memoization for faster solution.

        int min_coins = Integer.MAX_VALUE; // Default min_coins.
        for (int coin : coins) { // Going over all the coins
            // Recursively call the method on amount-coin to get the coins for it.
            int curr_coins = coinChangeHelper(coins, amount - coin, memo);
            if (curr_coins != Integer.MAX_VALUE) {
                // If it found it and did not return Integer.MAX_VALUE, the we can
                // update our min_coins for this iteration and add 1 to it as current coin.
                min_coins = Math.min(curr_coins + 1, min_coins);
            }
        }
        // Put the solution in the map with amount as the key.
        memo.put(amount, min_coins);
        return min_coins; // return the solution.
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
