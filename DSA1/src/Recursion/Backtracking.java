package Recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Backtracking {
    public static void main(String[] args) {
        int n = 3;
        List<String> list = generateParenthesis(n);
        for (String s : list){
            System.out.println(s);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Generated Parentheses: ");
        for (String s : list) {
            sb.append(s).append(", ");
        }
        System.out.println(sb.toString());

    }

    public List<List<String>> partition(String s) {
        /*
        Leet-code 131 (Medium)
        In this problem, we are finding all the partitions of the string
        s which are palindrome. All the partitions have to be palindromes.
        For this purpose, we can recursion and backtracking to generate all.
         */
        if  (s == null || s.length() == 0) return new ArrayList<>();
        List<List<String>> partitions = new ArrayList<>();
        backtrackPartition(partitions, new ArrayList<String>(), s, 0);
        return partitions;
    }
    public void backtrackPartition(List<List<String>> partitions, List<String> list, String s, int start){
        // If the start index has reached the length of the String s
        // that means we have a valid partitioning of the palindrome
        if (start == s.length()){
            partitions.add(new ArrayList<>(list));
            return;
        }
        /*
        The idea here is that we have to recurse for all of the partitions of the
        String s, of length 1, 2, ... , s.length. And we have to recurse of all substrings.
        We will start the for loop at the index start which will go till end == s.length()
         */
        for (int end = start+1; end < s.length(); end++){
            String substring = s.substring(start, end); // Get a substring from start to end.
            if (isPalindrome(substring)) { // Check, if it is a Palindrome, else continue.
                list.add(substring); // Add that string to our current running list we're working with.
                backtrackPartition(partitions, list, s, end); // Recurse with the modified current list
                list.remove(list.size()-1); // Remove the element added (backtrack) for other calls.
            }
        }
    }
    public boolean isPalindrome(String s){
        /*
        This is a simple palindrome checker for above question.
         */
        int left = 0;
        int right = s.length()-1;
        while (left < right){
            if (s.charAt(left) != s.charAt(right)){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        /*
        Leet-code 90 (Medium)
        This problem is the same as generating duplicates but we here
        have duplicate elements is the array given to deal with. We can
        still use the same backtracking approach with some conditions.
         */
        List<List<Integer>> subsets = new ArrayList<>();
        Arrays.sort(nums); // Sort the array for duplicate handling.
        backtrackSubsets(subsets, new ArrayList<Integer>(), nums, 0);
        return subsets;
    }
    public static void backtrackSubsets(List<List<Integer>> subsets, List<Integer> list, int[] nums, int index){
        // Add the subset to our subsets LIST as all combinations are valid
        subsets.add(new ArrayList<>(list));
        // Start the loop from the index given which will start from 0
        for (int i = index; i < nums.length; i++) {
            // Here, we check if the current element is the same as the previous
            // then we continue as arrays are sorted, all duplicates will be adjacent.
            if (i > index && nums[i] == nums[i - 1]) continue;

            list.add(nums[i]); // Add the current element of the array in the set
            backtrackSubsets(subsets, list, nums, i + 1); // Recurse with the new set and next index (next element)
            list.remove(list.size() - 1); // Remove the current element after exploring that path for future computations.
        }
    }

    public static boolean exist(char[][] board, String word) {
        /*
        Leet-code 79 (Medium)
        Here, we are finding if the word exists in our grid or not.
        This also can be done with BFS, but we are choosing backtracking.
        */
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // We will have to scan each element as the potential starting point
                // where the word could exists, and start recursion there. If our
                // backtrack method returns true, that means we found it, so we return true.
                if (backtrack(board, word, i, j, 0))
                    return true;
            }
        }
        // If we did not find it, return false at the end.
        return false;
    }
    public static boolean backtrack(char[][] board, String word, int i, int j, int index) {
        if (index == word.length())  // Base case
            return true; // If we reached the end of the word (Found it!)
        // Then we check the bounds, if the indexes got out of bounds, just return false.
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != word.charAt(index)) 
            return false;

        char temp = board[i][j]; // Here we are storing the current element to use again.
        board[i][j] = '#'; // Mark as visited so that it won't go down this path again.

        /*
        Then, the idea here is that there are 4 directions to check from a current
        index -> (left, right, up, down), and word could be in any of these directions.
        So, we recurse in four directions and store their result in one boolean variable
        which are connected by OR because if any of these calls return true, we return true (FOUND IN THAT DIRECTION)
         */
        boolean found = backtrack(board, word, i + 1, j, index + 1) ||
                        backtrack(board, word, i - 1, j, index + 1) ||
                        backtrack(board, word, i, j + 1, index + 1) ||
                        backtrack(board, word, i, j - 1, index + 1);

        board[i][j] = temp; // Restore original value
        return found; // Return if found it or not.
    }

    public List<String> letterCombinations(String digits) {
        /*
        Leet-code 17 (Medium)
        Here, we have to generate all letter combinations of the integers
        given as a string, and we can use a HashMap to store each word's
        mapping and can use StringBuilder to create mappings and check.
        */
        if (digits.equals("")) return new ArrayList<>();
        
        List<String> combinations = new ArrayList<>();
        HashMap<Character, String> mappings = map(); // Generate the mappings from the function below.
        backtrack(combinations, mappings, new StringBuilder(), digits, 0); // Populate the combinations
        return combinations;
    }
    public void backtrack(List<String> combinations, HashMap<Character, String> mappings,
                         StringBuilder current, String digits, int index) {
        // Base case: If our current mapping is the length of the
        // String digits, then we add it to our combinations.
        if (current.length() == digits.length()) {
            combinations.add(current.toString());
            return;
        }

        //Here, we will go into the loop for each digit in th digits array.
        for (int i = index; i < digits.length(); i++) {
            char ch = digits.charAt(i); // Get the digit at the current index.
            String mapping = mappings.get(ch); // Retrieve its mappings from the map.
            for (char c : mapping.toCharArray()) { // Go over all of its mappings
                current.append(c); // Append the character to our current Stringbuilder to create a new combo
                backtrack(combinations, mappings, current, digits, i+1); // Recurse forward with that combo to explore the path
                current.deleteCharAt(current.length()-1); // Delete that character to backtrack and for future.
            }
        }
    }
    public HashMap<Character, String> map() {
        HashMap<Character, String> mappings = new HashMap<>();
        mappings.put('2', "abc");
        mappings.put('3', "def");
        mappings.put('4', "ghi");
        mappings.put('5', "jkl");
        mappings.put('6', "mno");
        mappings.put('7', "pqrs");
        mappings.put('8', "tuv");
        mappings.put('9', "wxyz");
        return mappings;
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        /*
        Leet-code 39
        In this problem, generate all possible combinations from the
        candidates array that sum upto to the target value.
         */
        List<List<Integer>> combinations = new ArrayList<>();
        backtrackSum(combinations, candidates, target, new ArrayList<>(), 0, 0);
        return combinations;
    }
    public void backtrackSum(List<List<Integer>> combinations, int[] candidates, int target,
                          ArrayList<Integer> current, int current_sum, int index) {
        if (current_sum == target) { // If the current running sum == target
            combinations.add(new ArrayList<>(current)); // We add the current combination to our 2D List.
            return; // Return after adding.
        }
        if (current_sum > target) // If our current_sum became greater, then we don't recurse further.
            return;

        for (int i = index; i < candidates.length; i++) {
            /*
            For the duplication sake, we will start the loop from a start index which
            will only move forwards, that will make sure of no duplicates. We will add
            the current element to the combination and recurse further with the new
            running sum, and index will be i as any number can be used unlimited.
             */
            int next = candidates[i];
            current.add(next);
            backtrackSum(combinations, candidates, target, current, current_sum + next, i);
            // After the previous call returns we will remove the current element to backtrack.
            current.remove(current.size()-1);
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        /*
        Leet-code 46 (Medium)
        This problem is similar to the subsets or combinations problem, where
        we will generate all permutations which are just unique combinations.
         */
        List<List<Integer>> permutations = new ArrayList<>();
        backtrackPermute(permutations, new ArrayList<>(), nums);
        return permutations;
    }
    public void backtrackPermute(List<List<Integer>> permutations, ArrayList<Integer> current, int[] nums) {
        if (current.size() == nums.length) { // If current combination's size == length of array
            // We found the permutation and we add it to our 2D list.
            permutations.add(new ArrayList<>(current));
            return; // return after that.
        }

        // The inner process will be performed for each of the element in the array.
        for (int i = 0; i < nums.length; i++) {
            int curr = nums[i];
            if (!current.contains(curr)) {
                /*
                If our permutation does not include the current element,
                then we add it to the current one and recurse with the
                current element further, and after the calls returns,
                we have to remove the current element in order to backtrack
                 */
                current.add(curr);
                backtrackPermute(permutations, current, nums);
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
        backtrackSubs(subsets, nums,  0, new ArrayList<Integer>());
        return subsets;
    }
    public void backtrackSubs(List<List<Integer>> subsets, int[] nums, int index, List<Integer> curr_set) {
        // Every state of the set is a valid subset, so we add it at each iteration.
        subsets.add(new ArrayList<>(curr_set));
        /*
        Then we go into the loop going over all the elements in the array.
        For a subset, we have two choices at each step, either add the current
        element to the set, or skip this element and keep on going.
         */
        for (int i = index; i < nums.length; i++) {
            curr_set.add(nums[i]); // Add the current element to our current set.
            backtrackSubs(subsets, nums, i + 1, curr_set); // Recurse with this set to find other subsets.
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
        backtrackParenthesis(res, "", 0, 0, n);
        return res;
    }
    public static void backtrackParenthesis(List<String> res, String str, int open, int close, int n) {
        if (str.length() == n * 2) {
            res.add(str);
            return;
        }
        // If "(" count is less than n, we can add the "(" to our string
        if (open < n) {
            backtrackParenthesis(res, str + "(", open + 1, close, n);
        }
        // If ")" count is less than open, then we can add the "(" to our string
        if (close < open) {
            backtrackParenthesis(res, str + ")", open, close + 1, n);
        }
    }
}
