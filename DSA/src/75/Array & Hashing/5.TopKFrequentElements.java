import java.util.*;

public class 5.TopKFrequent {

    /*
     * PROBLEM: TOP K FREQUENT ELEMENTS
     *
     * Given an integer array nums and an integer k,
     * return the k most frequent elements.
     *
     * Example:
     * Input:  nums = [1,1,1,2,2,3], k = 2
     * Output: [1,2]
     *
     * n = Total number of elements
     * m = Number of unique elements
     * k = Number of elements to return
     */


    // ============================================================
    // 1. BRUTE FORCE: NESTED LOOPS + SORTING
    // ============================================================

    /*
     * IDEA:
     * 1. Pick each element from the array.
     * 2. Traverse the entire array to count its frequency.
     * 3. Store element and frequency in HashMap.
     * 4. Sort unique elements based on frequency (descending).
     * 5. Return the first k elements.
     *
     * PROBLEM:
     * We repeatedly calculate the frequency of duplicate elements.
     *
     * TIME COMPLEXITY:
     * O(n^2 + m log m)
     * - Nested loops: O(n^2)
     * - Sorting unique elements: O(m log m)
     *
     * SPACE COMPLEXITY:
     * O(m)
     */

    public int[] bruteForce(int[] nums, int k) {

        HashMap<Integer, Integer> freq = new HashMap<>();

        // Count frequency of every element using nested loops
        for (int i = 0; i < nums.length; i++) {

            int count = 0;

            for (int j = 0; j < nums.length; j++) {

                if (nums[i] == nums[j]) {
                    count++;
                }
            }

            freq.put(nums[i], count);
        }

        // Get unique elements
        List<Integer> list = new ArrayList<>(freq.keySet());

        // Sort elements by frequency in descending order
        list.sort((a, b) -> freq.get(b) - freq.get(a));

        // Store top k frequent elements
        int[] res = new int[k];

        for (int i = 0; i < k; i++) {
            res[i] = list.get(i);
        }

        return res;
    }


    // ============================================================
    // 2. BETTER APPROACH: HASHMAP + SORTING
    // ============================================================

    /*
     * IDEA:
     * Instead of repeatedly counting frequencies using nested loops,
     * use HashMap to calculate frequency in a single traversal.
     *
     * 1. Traverse array and calculate frequency.
     * 2. Store element and frequency in HashMap.
     * 3. Get all unique elements.
     * 4. Sort them based on frequency (descending).
     * 5. Return first k elements.
     *
     * IMPROVEMENT:
     * Eliminates repeated frequency calculation.
     *
     * TIME COMPLEXITY:
     * O(n + m log m)
     * - HashMap: O(n)
     * - Sorting: O(m log m)
     *
     * SPACE COMPLEXITY:
     * O(m)
     */

    public int[] hashMapSorting(int[] nums, int k) {

        HashMap<Integer, Integer> map = new HashMap<>();

        // Step 1: Calculate frequency
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // Step 2: Get unique elements
        List<Integer> list = new ArrayList<>(map.keySet());

        // Step 3: Sort by frequency in descending order
        list.sort((a, b) -> map.get(b) - map.get(a));

        // Step 4: Extract top k elements
        int[] res = new int[k];

        for (int i = 0; i < k; i++) {
            res[i] = list.get(i);
        }

        return res;
    }


    // ============================================================
    // 3. OPTIMIZED APPROACH: HASHMAP + MIN HEAP
    // ============================================================

    /*
     * IDEA:
     * We only need k most frequent elements.
     * Why sort or store all m elements in a heap?
     *
     * Instead, maintain a Min Heap of size k.
     *
     * 1. Calculate frequency using HashMap.
     * 2. Create Min Heap based on frequency.
     * 3. Insert each unique element into the heap.
     * 4. If heap size exceeds k, remove the element
     *    having the smallest frequency.
     * 5. At the end, heap contains k most frequent elements.
     *
     * WHY MIN HEAP?
     * The root always contains the element with the smallest frequency.
     * If heap size exceeds k, remove that element.
     *
     * TIME COMPLEXITY:
     * O(n + m log k)
     * - Frequency counting: O(n)
     * - Heap operations: O(m log k)
     *
     * SPACE COMPLEXITY:
     * O(m + k) = O(m)
     */

    public int[] minHeap(int[] nums, int k) {

        // Step 1: Calculate frequency
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // Step 2: Create Min Heap based on frequency
        PriorityQueue<Integer> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(map.get(a), map.get(b))
        );

        // Step 3: Maintain only k elements in heap
        for (int num : map.keySet()) {

            pq.add(num);

            // Remove element with smallest frequency
            if (pq.size() > k) {
                pq.poll();
            }
        }

        // Step 4: Extract remaining k elements
        int[] res = new int[k];

        for (int i = 0; i < k; i++) {
            res[i] = pq.poll();
        }

        return res;
    }


    // ============================================================
    // 4. ALTERNATIVE APPROACH: HASHMAP + MAX HEAP
    // ============================================================

    /*
     * IDEA:
     * 1. Calculate frequency using HashMap.
     * 2. Create Max Heap based on frequency.
     * 3. Insert all unique elements into heap.
     * 4. Extract top k elements.
     *
     * WHY MAX HEAP?
     * The root always contains the element with the highest frequency.
     *
     * DIFFERENCE FROM MIN HEAP:
     * - Min Heap: Maintains only k elements.
     * - Max Heap: Stores all m unique elements.
     *
     * TIME COMPLEXITY:
     * O(n + m log m + k log m)
     *
     * SPACE COMPLEXITY:
     * O(m)
     */

    public int[] maxHeap(int[] nums, int k) {

        // Step 1: Calculate frequency
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // Step 2: Create Max Heap based on frequency
        PriorityQueue<Integer> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(map.get(b), map.get(a))
        );

        // Step 3: Insert all unique elements
        for (int num : map.keySet()) {
            pq.add(num);
        }

        // Step 4: Extract top k elements
        int[] res = new int[k];

        for (int i = 0; i < k; i++) {
            res[i] = pq.poll();
        }

        return res;
    }


    // ============================================================
    // 5. MOST OPTIMIZED APPROACH: BUCKET SORT
    // ============================================================

    /*
     * IDEA:
     * Maximum frequency of any element cannot exceed nums.length.
     *
     * So, create buckets based on frequency.
     *
     * - Bucket index = Frequency
     * - Bucket value = Elements having that frequency
     *
     * Example:
     * nums = [1,1,1,2,2,3]
     *
     * Frequency:
     * 1 -> 3
     * 2 -> 2
     * 3 -> 1
     *
     * Buckets:
     * bucket[3] = [1]
     * bucket[2] = [2]
     * bucket[1] = [3]
     *
     * Traverse buckets from highest frequency to lowest.
     * Collect elements until k elements are found.
     *
     * TIME COMPLEXITY:
     * O(n)
     *
     * SPACE COMPLEXITY:
     * O(n)
     */

    public int[] bucketSort(int[] nums, int k) {

        // Step 1: Calculate frequency
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // Step 2: Create frequency buckets
        // Maximum possible frequency = nums.length
        ArrayList<Integer>[] bucket =
                new ArrayList[nums.length + 1];

        // Place elements into their respective frequency buckets
        for (int num : map.keySet()) {

            int freq = map.get(num);

            if (bucket[freq] == null) {
                bucket[freq] = new ArrayList<>();
            }

            bucket[freq].add(num);
        }

        // Step 3: Collect top k frequent elements
        int[] res = new int[k];
        int index = 0;

        // Start from highest frequency and move downwards
        for (int i = bucket.length - 1; i >= 0 && index < k; i--) {

            if (bucket[i] != null) {

                for (int num : bucket[i]) {

                    res[index++] = num;

                    if (index == k) {
                        break;
                    }
                }
            }
        }

        return res;
    }


    // ============================================================
    // 6. COMPLEXITY COMPARISON
    // ============================================================

    /*
     * Approach                  Time Complexity       Space
     *
     * Brute Force + Sorting     O(n^2 + m log m)      O(m)
     *
     * HashMap + Sorting         O(n + m log m)        O(m)
     *
     * HashMap + Max Heap        O(n + m log m)        O(m)
     *
     * HashMap + Min Heap        O(n + m log k)        O(m)
     *
     * Bucket Sort               O(n)                  O(n)
     *
     *
     * INTERVIEW TAKEAWAYS:
     *
     * 1. Brute Force:
     *    Count frequency using nested loops.
     *
     * 2. First Optimization:
     *    Use HashMap to calculate frequency in one traversal.
     *
     * 3. Second Optimization:
     *    Use sorting to arrange elements by frequency.
     *
     * 4. Third Optimization:
     *    Use Min Heap of size k to avoid sorting all unique elements.
     *
     * 5. Further Optimization:
     *    Use Bucket Sort to achieve O(n) time.
     *
     * RECOMMENDATION:
     * Understand HashMap + Sorting first, then Min Heap,
     * and finally Bucket Sort.
     *
     * For interviews, focus on why Min Heap maintains only k elements.
     */
}