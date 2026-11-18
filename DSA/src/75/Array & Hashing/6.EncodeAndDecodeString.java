import java.util.*;

public class Solution {

    /*
     * PROBLEM: ENCODE AND DECODE STRINGS
     *
     * Given a list of strings, encode them into a single string.
     * Later, decode that single string back into the original list.
     *
     * Example:
     * Input:  ["hello", "world"]
     * Output: ["hello", "world"]
     *
     * IMPORTANT:
     * The encoding must be reversible and support:
     * - Empty strings
     * - Special characters
     * - Numbers
     * - Delimiters inside strings
     */


    // ============================================================
    // 1. BRUTE FORCE: SIMPLE CONCATENATION WITH DELIMITER
    // ============================================================

    /*
     * IDEA:
     * 1. Join all strings using a special delimiter such as '#'.
     * 2. During decoding, split the encoded string using '#'.
     *
     * PROBLEM:
     * What if the original string itself contains '#'?
     *
     * Example:
     * Input: ["hello#world", "java"]
     *
     * Encoded: hello#world#java
     *
     * Decoding produces:
     * ["hello", "world", "java"]
     *
     * Original data is lost.
     *
     * TIME COMPLEXITY:
     * Encode: O(n + m)
     * Decode: O(n + m)
     *
     * SPACE COMPLEXITY:
     * O(n + m)
     *
     * This approach is not reliable for arbitrary strings.
     */

    public String encodeBruteForce(List<String> strs) {

        StringBuilder sb = new StringBuilder();

        for (String s : strs) {
            sb.append(s).append("#");
        }

        return sb.toString();
    }

    public List<String> decodeBruteForce(String str) {

        // Split using delimiter
        return Arrays.asList(str.split("#"));
    }


    // ============================================================
    // 2. BETTER APPROACH: CHARACTER SHIFTING + DELIMITER
    // ============================================================

    /*
     * IDEA:
     * Instead of directly storing characters, shift each character
     * by a fixed value and use a delimiter to separate strings.
     *
     * 1. Subtract key from every character during encoding.
     * 2. Append '~' after every original string.
     * 3. During decoding, add key to restore original characters.
     *
     * EXAMPLE:
     * key = 23
     *
     * Input: ["abc", "def"]
     *
     * Encoding:
     * a -> a - 23
     * b -> b - 23
     * c -> c - 23
     *
     * Add '~' after every string.
     *
     * Decoding:
     * Read encoded characters.
     * Add 23 to restore original characters.
     *
     * PROBLEM:
     * A shifted character can become '~'.
     * The decoder will incorrectly consider it a delimiter.
     *
     * This approach is not guaranteed to work for arbitrary input.
     *
     * TIME COMPLEXITY:
     * Encode: O(n + m)
     * Decode: O(n + m)
     *
     * SPACE COMPLEXITY:
     * O(n + m)
     */

    int key = 23;

    public String encodeCharacterShift(List<String> strs) {

        StringBuilder encoded = new StringBuilder();

        for (String s : strs) {

            // Shift every character by subtracting key
            for (int j = 0; j < s.length(); j++) {

                char ch = s.charAt(j);

                encoded.append((char) (ch - key));
            }

            // Delimiter to identify end of each string
            encoded.append("~");
        }

        return encoded.toString();
    }

    public List<String> decodeCharacterShift(String str) {

        List<String> res = new ArrayList<>();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {

            char ch = str.charAt(i);

            if (ch == '~') {

                // Current string is complete
                res.add(sb.toString());

                // Clear StringBuilder for next string
                sb.setLength(0);

            } else {

                // Restore original character
                sb.append((char) (ch + key));
            }
        }

        return res;
    }


    // ============================================================
    // 3. OPTIMIZED / RECOMMENDED: LENGTH PREFIX ENCODING
    // ============================================================

    /*
     * IDEA:
     * Instead of depending on a special delimiter to identify
     * the end of a string, store its length before the string.
     *
     * FORMAT:
     * length#string
     *
     * EXAMPLE:
     * Input: ["hello", "world", ""]
     *
     * Encoded:
     * 5#hello5#world0#
     *
     * DECODING:
     * 1. Read characters until '#' to get the length.
     * 2. Convert that length into an integer.
     * 3. Read exactly that many characters.
     * 4. Store the extracted string.
     * 5. Move pointer to the next encoded string.
     *
     * WHY IS THIS BETTER?
     *
     * The original string can contain:
     * - '#'
     * - '~'
     * - Numbers
     * - Special characters
     * - Empty strings
     *
     * No delimiter collision because we use the length
     * to determine exactly how many characters to read.
     *
     * TIME COMPLEXITY:
     * Encode: O(n + m)
     * Decode: O(n + m)
     *
     * SPACE COMPLEXITY:
     * O(n + m)
     *
     * This is the standard recommended interview solution.
     */

    public String encode(List<String> strs) {

        StringBuilder encoded = new StringBuilder();

        for (String s : strs) {

            // Step 1: Append length of current string
            encoded.append(s.length());

            // Step 2: Append separator
            encoded.append("#");

            // Step 3: Append actual string
            encoded.append(s);
        }

        return encoded.toString();
    }

    public List<String> decode(String str) {

        List<String> res = new ArrayList<>();

        // Pointer to traverse encoded string
        int i = 0;

        while (i < str.length()) {

            // Step 1: Find '#' to identify string length
            int j = i;

            while (str.charAt(j) != '#') {
                j++;
            }

            // Step 2: Extract length from encoded string
            int length = Integer.parseInt(str.substring(i, j));

            // Step 3: Extract actual string using its length
            String s = str.substring(j + 1, j + 1 + length);

            // Step 4: Add decoded string to result
            res.add(s);

            // Step 5: Move pointer to next encoded string
            i = j + 1 + length;
        }

        return res;
    }


    // ============================================================
    // 4. COMPLEXITY COMPARISON
    // ============================================================

    /*
     * Approach                 Encode       Decode
     *
     * Simple Delimiter         O(N)         O(N)
     *
     * Character Shifting       O(N)         O(N)
     *
     * Length Prefix            O(N)         O(N)
     *
     * N = Total encoded/input size, including metadata.
     *
     * Auxiliary space:
     * O(N) for the encoded or decoded output.
     */


    // ============================================================
    // 5. INTERVIEW TAKEAWAYS
    // ============================================================

    /*
     * 1. Brute Force:
     *    Concatenate strings using a delimiter.
     *    Problem: Delimiter may exist inside original strings.
     *
     * 2. Better:
     *    Shift characters and use a delimiter.
     *    Problem: Shifted characters may collide with delimiter.
     *
     * 3. Optimized:
     *    Use Length Prefix Encoding.
     *
     *    Store:
     *    length#string
     *
     *    Decode:
     *    Read length -> Extract exact number of characters.
     *
     * KEY CONCEPT:
     * Encoding and decoding must be reversible without ambiguity.
     *
     * INTERVIEW RECOMMENDATION:
     * Prefer Length Prefix Encoding.
     * It achieves linear time complexity and handles arbitrary
     * string contents without delimiter collisions.
     */
}