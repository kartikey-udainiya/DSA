import java.util.*;

/*
 * ===================================================================
 *  NOTES: Accounts Merge (LeetCode 721) using Disjoint Set (Union-Find)
 * ===================================================================
 *
 * PROBLEM
 *   accounts[i] = [name, email1, email2, ...]
 *   Two accounts belong to the same person if they share ANY email.
 *   Merge such accounts. Output: [name, sorted emails...] for each person.
 *   (Same name does NOT mean same person. Only a shared email does.)
 *
 * KEY IDEA
 *   Treat each ACCOUNT INDEX (0..n-1) as a node.
 *   If two accounts share an email, union their indices.
 *   At the end, every group of indices = one real person.
 *
 * STEPS
 *   1. Walk through every email of every account.
 *        - email never seen before  -> map[email] = current account index
 *        - email already in map     -> union(map[email], current index)
 *   2. For every (email -> owner) in map, find the owner's ultimate parent
 *      and drop the email into that parent's bucket.
 *   3. For every non-empty bucket: sort emails, put the name in front.
 *
 * WHY IT WORKS
 *   The map remembers the FIRST account that owned each email. Any later
 *   account with the same email gets unioned with that first account, so
 *   chains like A-B and B-C automatically end up as one group A-B-C.
 *
 * DRY RUN (Example)
 *   0: John [johnsmith, john_newyork]
 *   1: John [johnsmith, john00]
 *   2: Mary [mary]
 *   3: John [johnnybravo]
 *
 *   johnsmith    -> map = 0
 *   john_newyork -> map = 0
 *   johnsmith (in account 1) already in map -> union(0, 1)   => {0,1}
 *   john00       -> map = 1
 *   mary         -> map = 2
 *   johnnybravo  -> map = 3
 *
 *   Ultimate parents: 0,1 -> 0 ; 2 -> 2 ; 3 -> 3
 *   Bucket 0 = [johnsmith, john_newyork, john00] -> sorted, name "John" added
 *   Bucket 2 = [mary]
 *   Bucket 3 = [johnnybravo]
 *
 * COMPLEXITY
 *   N = number of accounts, E = total number of emails
 *   Time  : O(E * alpha(N)) for the unions + O(E log E) for sorting emails
 *   Space : O(N + E)
 *
 * THINGS TO REMEMBER
 *   - The node is the account INDEX, not the email.
 *   - The name is taken from accounts.get(i).get(0) where i is the ROOT index.
 *   - Skip empty buckets (they belong to indices that were merged into others).
 *   - Loop emails from j = 1 (index 0 is the name).
 *   - Sort each bucket before adding the name.
 */
class Solution {

    // ---------------------------------------------------------------
    // Disjoint Set (0-indexed, array based)
    // ---------------------------------------------------------------
    class DisjointSet {

        int[] parent;   // parent[i] = direct parent of i (root is its own parent)
        int[] rank;     // rank[i]   = approximate tree height (meaningful for roots)

        public DisjointSet(int n) {
            this.parent = new int[n];
            this.rank = new int[n];

            for (int i = 0; i < n; i++) {
                rank[i] = 0;     // every tree starts with height 0
                parent[i] = i;   // every node starts as its own group
            }
        }

        // Find the ultimate parent (root) with path compression
        public int findPar(int n) {
            // Base case: a node that is its own parent is the root
            if (parent[n] == n) {
                return n;
            }

            // Go up to the root, then point this node straight at it
            int ult_par = findPar(parent[n]);
            parent[n] = ult_par;
            return ult_par;
        }

        // Merge the groups of u and v (attach the lower rank under the higher)
        public void unionByRank(int u, int v) {
            int ulp_u = findPar(u);
            int ulp_v = findPar(v);

            // Already in the same group, nothing to do
            if (ulp_u == ulp_v) return;

            if (rank[ulp_u] == rank[ulp_v]) {
                // Equal rank: pick u's root as the new root, its rank grows by 1
                rank[ulp_u] += 1;
                parent[ulp_v] = ulp_u;
            } else if (rank[ulp_u] > rank[ulp_v]) {
                // u's tree is taller, v's root goes under it
                parent[ulp_v] = ulp_u;
            } else {
                // v's tree is taller, u's root goes under it
                parent[ulp_u] = ulp_v;
            }
        }
    }

    // ---------------------------------------------------------------
    // Main logic
    // ---------------------------------------------------------------
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();
        DisjointSet ds = new DisjointSet(n);   // one node per account

        // email -> index of the first account that contained it
        HashMap<String, Integer> map = new HashMap<>();

        // STEP 1: connect accounts that share an email
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < accounts.get(i).size(); j++) {   // j = 1 skips the name
                String mail = accounts.get(i).get(j);

                if (map.containsKey(mail)) {
                    // Seen before in another account, so merge the two accounts
                    ds.unionByRank(map.get(mail), i);
                } else {
                    // First time we see this email, remember who owns it
                    map.put(mail, i);
                }
            }
        }

        // One empty bucket per account index; emails will be dropped in by root
        List<List<String>> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            res.add(new ArrayList<>());
        }

        // STEP 2: put each email into the bucket of its owner's ultimate parent
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String mail = entry.getKey();
            int owner = entry.getValue();
            int realOwner = ds.findPar(owner);   // root of the merged group

            res.get(realOwner).add(mail);
        }

        // STEP 3: build the final answer
        List<List<String>> ans = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            // Empty bucket means index i was merged into another root, so skip
            if (res.get(i).isEmpty()) continue;

            // Emails must be sorted in the output
            Collections.sort(res.get(i));

            List<String> temp = new ArrayList<>();

            // Name goes first (taken from the root account)
            temp.add(accounts.get(i).get(0));

            // Followed by the sorted emails
            temp.addAll(res.get(i));

            ans.add(temp);
        }

        return ans;
    }
}

// -------------------------------------------------------------------
// Demo: run this file to test. Leave this class out when pasting on LeetCode.
// -------------------------------------------------------------------
public class AccountsMerge {
    public static void main(String[] args) {
        List<List<String>> accounts = new ArrayList<>();
        accounts.add(Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com"));
        accounts.add(Arrays.asList("John", "johnsmith@mail.com", "john00@mail.com"));
        accounts.add(Arrays.asList("Mary", "mary@mail.com"));
        accounts.add(Arrays.asList("John", "johnnybravo@mail.com"));

        Solution sol = new Solution();
        List<List<String>> merged = sol.accountsMerge(accounts);

        // Expected:
        // [John, john00@mail.com, john_newyork@mail.com, johnsmith@mail.com]
        // [Mary, mary@mail.com]
        // [John, johnnybravo@mail.com]
        for (List<String> acc : merged) {
            System.out.println(acc);
        }
    }
}