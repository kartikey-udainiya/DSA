
import java.util.*;

public class GroupAnagrams {

    // ==========================================
    // APPROACH 1: BRUTE FORCE
    // Sorting + Nested Loops + Visited Array
    // ==========================================

    public static List<List<String>> bruteForce(String[] strs) {

        int m = strs.length;

        // Store sorted versions of all strings
        String[] sortedStrings = new String[m];

        for (int i = 0; i < m; i++) {
            char[] ch = strs[i].toCharArray();

            Arrays.sort(ch);

            sortedStrings[i] = new String(ch);
        }

        List<List<String>> ans = new ArrayList<>();

        // Track already grouped strings
        boolean[] visited = new boolean[m];

        for (int i = 0; i < m; i++) {

            if (visited[i]) continue;

            List<String> group = new ArrayList<>();

            // Compare current string with remaining strings
            for (int j = i; j < m; j++) {

                if (!visited[j] &&
                        sortedStrings[i].equals(sortedStrings[j])) {

                    group.add(strs[j]);
                    visited[j] = true;
                }
            }

            ans.add(group);
        }

        return ans;
    }

    // Time Complexity: O(m * n log n + m²)
    // Sorting each string: O(n log n)
    // Nested loops: O(m²)
    //
    // Auxiliary Space: O(m * n)
    // Sorted strings + visited array
    // Output Space: O(m * n)


    // ==========================================
    // APPROACH 2: OPTIMIZED
    // HashMap + Character Frequency
    // ==========================================

    public static List<List<String>> optimized(String[] strs) {

        // Key = Character frequency
        // Value = List of anagrams
        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {

            // Frequency array for 26 lowercase letters
            int[] count = new int[26];

            // Count occurrences of each character
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }

            // Convert frequency array into a unique key
            String key = Arrays.toString(count);

            // Create a new group if key doesn't exist
            map.putIfAbsent(key, new ArrayList<>());

            // Add original string to its anagram group
            map.get(key).add(s);
        }

        // Return all grouped anagrams
        return new ArrayList<>(map.values());
    }

    // Time Complexity: O(m * n)
    // m = Number of strings
    // n = Maximum length of a string
    //
    // Character counting: O(n)
    // Creating key: O(26) = O(1)
    // HashMap insertion: O(1) average
    //
    // Auxiliary Space: O(m)
    // Output Space: O(m * n)


    // ==========================================
    // MAIN METHOD
    // ==========================================

    public static void main(String[] args) {

        String[] strs = {
                "eat", "tea", "tan", "ate", "nat", "bat"
        };

        System.out.println("Brute Force:");
        System.out.println(bruteForce(strs));

        System.out.println("Optimized:");
        System.out.println(optimized(strs));
    }
}
