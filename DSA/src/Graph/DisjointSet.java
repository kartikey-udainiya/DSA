import java.util.ArrayList;
import java.util.List;

class DisjointSet {
    List<Integer> rank = new ArrayList<>();
    List<Integer> parent = new ArrayList<>();

    public DisjointSet(int n){
        for (int i = 0; i <= n; i++) {
            rank.add(0);
            parent.add(i);
        }
    }

    public int findPar(int node){
        if(node == parent.get(node)){
            return node;
        }
        int ultimateParent =  findPar(parent.get(node));
        parent.set(node,ultimateParent);
        return ultimateParent;
    }

    public void unionByRank(int u , int v){
        int ulp_u = findPar(u);
        int ulp_v = findPar(v);

        if(ulp_u == ulp_v) return;

        // u>v
        if(rank.get(ulp_u) > rank.get(ulp_v)) {
            parent.set(ulp_v,ulp_u);
        } else if (rank.get(ulp_u) < rank.get(ulp_v)) {
            parent.set(ulp_u,ulp_v);
        }else{
            parent.set(ulp_v,ulp_u);
            rank.set(ulp_u,rank.get(ulp_u)+1);
        }
    }
    public static void main(String[] args) {
        DisjointSet ds = new DisjointSet(7);

        ds.unionByRank(1, 2);
        ds.unionByRank(2, 3);
        ds.unionByRank(4, 5);
        ds.unionByRank(6, 7);
        ds.unionByRank(5, 6);

        // Test 1: Are 1 and 3 connected?
        System.out.println(ds.findPar(1) == ds.findPar(3));

        // Test 2: Are 1 and 7 connected?
        System.out.println(ds.findPar(1) == ds.findPar(7));

        // Test 3: Are 4 and 7 connected?
        System.out.println(ds.findPar(4) == ds.findPar(7));

        // Merge both components
        ds.unionByRank(3, 7);

        // Test 4: Are 1 and 7 connected now?
        System.out.println(ds.findPar(1) == ds.findPar(7));
    }
}

// claude's re written notes:
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * Disjoint Set (Union-Find)
 *
 * Idea: keep nodes in groups. Every group has one "ultimate parent" (the root).
 *   findPar(x)  -> tells which group x belongs to (returns the root)
 *   union(u, v) -> merges the groups of u and v
 * Two nodes are connected if and only if they have the same ultimate parent.
 *
 * Optimisations used:
 *   1) Path compression : after a find, every node on the path points directly to the root
 *   2) Union by rank    : attach the shorter tree under the taller tree
 * Together these make each operation almost O(1) (inverse Ackermann).
 *
 * Nodes are 1-indexed here (arrays have n + 1 slots), so valid nodes are 1..n.
 */
class DisjointSet {

    // rank[i]   = approximate height of the tree rooted at i (only meaningful for roots)
    List<Integer> rank = new ArrayList<>();

    // parent[i] = direct parent of i (a root is its own parent)
    List<Integer> parent = new ArrayList<>();

    // size[i]   = number of nodes in the group rooted at i (only meaningful for roots)
    List<Integer> size = new ArrayList<>();

    int n;            // number of real nodes (1..n)
    int components;   // current number of separate groups

    public DisjointSet(int n) {
        this.n = n;
        this.components = n;

        // i <= n so that both 0-indexed and 1-indexed graphs work
        for (int i = 0; i <= n; i++) {
            rank.add(0);     // every node starts as a tree of height 0
            parent.add(i);   // every node is its own parent (its own group)
            size.add(1);     // every group starts with exactly 1 node
        }
    }

    // ---------------------------------------------------------------
    // Find the ultimate parent (root) of a node, with path compression
    // ---------------------------------------------------------------
    public int findPar(int node) {

        // Base case: a node that is its own parent is the root
        if (node == parent.get(node)) {
            return node;
        }

        // Go up recursively to find the root...
        int ultimateParent = findPar(parent.get(node));

        // ...then point this node straight at the root (path compression),
        // so the next find on it takes O(1)
        parent.set(node, ultimateParent);

        return ultimateParent;
    }

    // ---------------------------------------------------------------
    // Union by rank: attach the lower-rank root under the higher-rank root
    // ---------------------------------------------------------------
    public void unionByRank(int u, int v) {
        int ulp_u = findPar(u);   // ultimate parent of u
        int ulp_v = findPar(v);   // ultimate parent of v

        // Same root means already in the same group, nothing to do
        if (ulp_u == ulp_v) return;

        if (rank.get(ulp_u) > rank.get(ulp_v)) {
            // u's tree is taller, so v's root goes under u's root
            parent.set(ulp_v, ulp_u);
            size.set(ulp_u, size.get(ulp_u) + size.get(ulp_v));

        } else if (rank.get(ulp_u) < rank.get(ulp_v)) {
            // v's tree is taller, so u's root goes under v's root
            parent.set(ulp_u, ulp_v);
            size.set(ulp_v, size.get(ulp_v) + size.get(ulp_u));

        } else {
            // Equal rank: pick either as the new root, and its rank grows by 1
            parent.set(ulp_v, ulp_u);
            size.set(ulp_u, size.get(ulp_u) + size.get(ulp_v));
            rank.set(ulp_u, rank.get(ulp_u) + 1);
        }

        components--;   // two groups became one
    }

    // ---------------------------------------------------------------
    // Union by size: attach the smaller group under the larger group.
    // An alternative to union by rank (use one or the other consistently).
    // ---------------------------------------------------------------
    public void unionBySize(int u, int v) {
        int ulp_u = findPar(u);
        int ulp_v = findPar(v);

        if (ulp_u == ulp_v) return;

        if (size.get(ulp_u) < size.get(ulp_v)) {
            // u's group is smaller, so it goes under v's root
            parent.set(ulp_u, ulp_v);
            size.set(ulp_v, size.get(ulp_v) + size.get(ulp_u));
        } else {
            // v's group is smaller or equal, so it goes under u's root
            parent.set(ulp_v, ulp_u);
            size.set(ulp_u, size.get(ulp_u) + size.get(ulp_v));
        }

        components--;
    }

    // ---------------------------------------------------------------
    // Are u and v in the same group?
    // ---------------------------------------------------------------
    public boolean isConnected(int u, int v) {
        return findPar(u) == findPar(v);
    }

    // ---------------------------------------------------------------
    // How many separate groups exist right now?
    // ---------------------------------------------------------------
    public int countComponents() {
        return components;
    }

    // ---------------------------------------------------------------
    // Size of the group that contains this node
    // ---------------------------------------------------------------
    public int getSize(int node) {
        return size.get(findPar(node));
    }

    // ---------------------------------------------------------------
    // Group structure: maps each root -> list of all nodes in that group.
    // e.g. {3=[1, 2, 3], 4=[4, 5, 6, 7]}
    // ---------------------------------------------------------------
    public Map<Integer, List<Integer>> getComponents() {
        Map<Integer, List<Integer>> groups = new LinkedHashMap<>();

        for (int i = 1; i <= n; i++) {
            int root = findPar(i);                       // which group is i in?
            groups.computeIfAbsent(root, k -> new ArrayList<>()).add(i);
        }
        return groups;
    }

    // ---------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------
    public static void main(String[] args) {
        DisjointSet ds = new DisjointSet(7);

        ds.unionByRank(1, 2);
        ds.unionByRank(2, 3);
        ds.unionByRank(4, 5);
        ds.unionByRank(6, 7);
        ds.unionByRank(5, 6);
        // Groups now: {1,2,3} and {4,5,6,7}

        // Test 1: Are 1 and 3 connected? -> true
        System.out.println(ds.findPar(1) == ds.findPar(3));

        // Test 2: Are 1 and 7 connected? -> false
        System.out.println(ds.findPar(1) == ds.findPar(7));

        // Test 3: Are 4 and 7 connected? -> true
        System.out.println(ds.findPar(4) == ds.findPar(7));

        // Group structure and counts before merging
        System.out.println("Components = " + ds.countComponents());   // 2
        System.out.println("Groups     = " + ds.getComponents());
        System.out.println("Size of 1's group = " + ds.getSize(1));   // 3

        // Merge both components
        ds.unionByRank(3, 7);

        // Test 4: Are 1 and 7 connected now? -> true
        System.out.println(ds.findPar(1) == ds.findPar(7));

        System.out.println("Components = " + ds.countComponents());   // 1
        System.out.println("Groups     = " + ds.getComponents());
    }
}




// 1 Questions : Minimum Edge Movements to Connect a Graph : https://www.geeksforgeeks.org/problems/connecting-the-graph/1


class Solution {

    public class DisjointSet {
        public int [] parent ;
        public int [] rank ;

        public DisjointSet(int n){
            this.parent = new int[n];
            this.rank = new int[n];

            for (int i = 0; i < n; i++) {
                rank[i]=0;
                parent[i]=i;
            }
        }

        public int findPar(int n){
            if(parent[n]==n){
                return n;
            }

            int ult_par = findPar(parent[n]);
            parent[n]=ult_par;
            return ult_par;
        }

        public void unionByRank(int u,int v){
            int ulp_u = findPar(u);
            int ulp_v = findPar(v);

            if(ulp_u==ulp_v)return;
            if(rank[ulp_u]==rank[ulp_v]){
                rank[ulp_u]+=1;
                parent[ulp_v]=ulp_u;
            } else if (rank[ulp_u]>rank[ulp_v]) {
                parent[ulp_v]=ulp_u;
            }else {
                parent[ulp_u] = ulp_v;
            }
        }
    }

    int minEdgesReq(int n, int[][] edges) {
        DisjointSet ds = new DisjointSet(n);
        int countExtra = 0;

        for(int []edge : edges){
            int u = edge[0];
            int v = edge[1];

            if(ds.findPar(u)==ds.findPar(v)){
                countExtra++;
            }else{
                ds.unionByRank(u,v);
            }
        }

        int count = 0 ;
        for(int i = 0 ; i < n ; i++){
            if(ds.parent[i]==i){
                count++;
            }
        }

        int ans = count-1;

        if(countExtra>=ans) return ans;

        return -1;

    }
}


