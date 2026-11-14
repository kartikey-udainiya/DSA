
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// ==========================================
// DISJOINT SET (UNION-FIND)
// ==========================================

class DisjointSet {

    int[] parent;
    int[] rank;

    // Initially, every node is its own parent.
    // Rank of every node is initially 0.
    public DisjointSet(int n) {

        parent = new int[n];
        rank = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    // Find the ultimate parent of a node.
    // Uses Path Compression.
    public int findPar(int node) {

        if (parent[node] == node) {
            return node;
        }

        int ultimateParent = findPar(parent[node]);

        // Directly connect node to its ultimate parent.
        parent[node] = ultimateParent;

        return ultimateParent;
    }

    // Merge two components using Union by Rank.
    public void unionByRank(int u, int v) {

        int ulp_u = findPar(u);
        int ulp_v = findPar(v);

        // Already belong to the same component.
        if (ulp_u == ulp_v) return;

        // Attach smaller rank below larger rank.
        if (rank[ulp_u] > rank[ulp_v]) {

            parent[ulp_v] = ulp_u;

        }
        else if (rank[ulp_u] < rank[ulp_v]) {

            parent[ulp_u] = ulp_v;

        }
        else {

            // Equal rank: choose one as parent.
            parent[ulp_v] = ulp_u;
            rank[ulp_u]++;
        }
    }
}


// ==========================================
// KRUSKAL'S ALGORITHM
// ==========================================

class Solution {

    // Adjacency list format:
    // adj.get(u).get(i).get(0) = neighbour
    // adj.get(u).get(i).get(1) = edge weight

    public int kruskalMST(
            int V,
            ArrayList<ArrayList<ArrayList<Integer>>> adj
    ) {

        // Step 1: Extract all edges.
        // Edge format: {weight, source, destination}

        List<int[]> edges = new ArrayList<>();

        for (int u = 0; u < V; u++) {

            for (int j = 0; j < adj.get(u).size(); j++) {

                int v = adj.get(u).get(j).get(0);
                int wt = adj.get(u).get(j).get(1);

                edges.add(new int[]{wt, u, v});
            }
        }

        // Step 2: Sort edges by ascending weight.

        Collections.sort(
                edges,
                (a, b) -> Integer.compare(a[0], b[0])
        );

        // Step 3: Initialize Disjoint Set for V vertices.

        DisjointSet ds = new DisjointSet(V);

        int mstWeight = 0;
        int edgesSelected = 0;

        // Step 4: Process edges in increasing order of weight.

        for (int[] edge : edges) {

            int wt = edge[0];
            int u = edge[1];
            int v = edge[2];

            // Different parents mean no cycle will be formed.
            if (ds.findPar(u) != ds.findPar(v)) {

                // Include edge in MST.
                mstWeight += wt;

                // Merge both components.
                ds.unionByRank(u, v);

                edgesSelected++;

                // MST contains exactly V-1 edges.
                if (edgesSelected == V - 1) {
                    break;
                }
            }
        }

        // A disconnected graph has no single MST.
        if (edgesSelected != V - 1) {
            return -1;
        }

        return mstWeight;
    }
}
