package Recursion;

import java.util.ArrayList;
import java.util.List;

public class Backtracking {
    public static void main(String[] args) {
        int n = 3;
        List<String> list = generateParenthesis(n);
        for (String s : list){
            System.out.println(s);
        }
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        /*
        Leet-code 39
         */
        List<List<Integer>> combinations = new ArrayList<>();
        backtrack(combinations, candidates, target, new ArrayList<>(), 0, 0);
        return combinations;
    }
    public void backtrack(List<List<Integer>> combinations, int[] candidates, int target,
                          ArrayList<Integer> current, int current_sum, int index) {
        if (current_sum == target) {
            combinations.add(new ArrayList<>(current));
            return;
        }
        if (current_sum > target)
            return;

        for (int i = index; i < candidates.length; i++) {
            int next = candidates[i];
            current.add(next);
            backtrack(combinations, candidates, target, current, current_sum + next, i);
            current.remove(current.size()-1);
        }

    }

    public List<List<Integer>> permute(int[] nums) {
        /*
        Leet-code 46 (Medium)
         */
        List<List<Integer>> permutations = new ArrayList<>();
        backtrack(permutations, new ArrayList<>(), nums);
        return permutations;
    }
    public void backtrack(List<List<Integer>> permutations, ArrayList<Integer> current, int[] nums) {
        if (current.size() == nums.length) {
            permutations.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            int curr = nums[i];
            if (!current.contains(curr)) {
                current.add(curr);
                backtrack(permutations, current, nums);
                current.remove(current.size()-1);
            }
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        /*
        Leet-code 78
        In this problem, we can use recursion and backtracking to
        generate all the possible subetts of the given set.
         */
        List<List<Integer>> subsets = new ArrayList<>();
        backtrack(subsets, nums,  0, new ArrayList<Integer>());
        return subsets;
    }
    public void backtrack(List<List<Integer>> subsets, int[] nums, int index, List<Integer> curr_set) {
        // Every state of the set is a valid subset, so we add it at each iteration.
        subsets.add(new ArrayList<>(curr_set));
        /*
        Then we go into the loop going over all the elements in the array.
        For a subset, we have two choices at each step, either add the current
        element to the set, or skip this element and keep on going.
         */
        for (int i = index; i < nums.length; i++) {
            curr_set.add(nums[i]); // Add the current element to our current set.
            backtrack(subsets, nums, i + 1, curr_set); // Recurse with this set to find other subsets.
            curr_set.remove(curr_set.size()-1); // remove the element added from the set for future processings.
        }
    }

    public static List<String> generateParenthesis(int n) {
        /*
        Leet-code 22
        This problem can be done by using recursion. The idea here is that
        there are two possible actions to take at a given state:
        Adding "(" to our string or ")" to our string.
        There are two conditions which make a string valid:
        1. The number of open parenthesis has to be less or equal to n.
        2. We can't add ")" if there is no opening "(".
        And satisfying these conditions, if we get to the length of
        n * 2, then that means we have a valid string of parenthesis.
         */
        List<String> res = new ArrayList<String>();
        backtrack(res, "", 0, 0, n);
        return res;
    }
    public static void backtrack(List<String> res, String str, int open, int close, int n) {
        if (str.length() == n * 2) {
            res.add(str);
            return;
        }
        // If "(" count is less than n, we can add the "(" to our string
        if (open < n) {
            backtrack(res, str + "(", open + 1, close, n);
        }
        // If ")" count is less than open, then we can add the "(" to our string
        if (close < open) {
            backtrack(res, str + ")", open, close + 1, n);
        }
    }
}
