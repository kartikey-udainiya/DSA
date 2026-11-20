public List<List<Integer>> combinationSum(int[] nums, int target) {
    List<List<Integer>> list = new ArrayList<>();
    backtrack(0,0,target,list,nums,new ArrayList<>());

    return list;

}

public void backtrack(int index,int sum,int target, List<List<Integer>> list,int[]nums,List<Integer> l){
    if(sum==target){
        list.add(new ArrayList<>(l));
        return;
    }
    if(sum>target){
        return;
    }
    for (int i = index; i < nums.length; i++) {
        l.add(nums[i]);
        backtrack(i,sum+nums[i],target,list,nums,l);
        l.removeLast();
    }
}

/*
 * PROBLEM: Combination Sum (LeetCode 39)
 *
 * APPROACH: Backtracking
 *
 * TIME COMPLEXITY:
 * O(n^(T/m + 1)) - Loose upper-bound estimate.
 *
 * Where:
 * n = Number of candidates.
 * T = Target value.
 * m = Minimum candidate value.
 *
 * Explanation:
 * - At every recursion level, we can explore up to n choices.
 * - Maximum recursion depth is approximately T/m.
 * - Therefore, the recursion tree has an upper-bound estimate
 *   of O(n^(T/m + 1)).
 * - Copying valid combinations also takes time proportional
 *   to the size of each combination.
 *
 * AUXILIARY SPACE COMPLEXITY:
 * O(T/m)
 *
 * Explanation:
 * - Recursion stack can go up to T/m levels.
 * - Temporary ArrayList can store at most T/m elements.
 * - Excludes the space required to store the final result.
 *
 * OUTPUT SPACE:
 * Depends on the number of valid combinations and their sizes.
 *
 * IMPORTANT BACKTRACKING CONCEPTS:
 *
 * 1. temp.add(nums[i])
 *    -> Choose the current element.
 *
 * 2. backtrack(i, ...)
 *    -> Pass i to allow reusing the same element.
 *
 * 3. temp.remove(temp.size() - 1)
 *    -> Undo the previous choice (backtracking).
 *
 * 4. sum == target
 *    -> Valid combination found.
 *
 * 5. sum > target
 *    -> Stop exploring the current branch.
 *
 * 6. Start the loop from index instead of 0.
 *    -> Avoid duplicate combinations in different orders.
 */