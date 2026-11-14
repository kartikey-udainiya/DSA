import java.util.*;

/*
 * ===================================================================
 *  NOTES: Articulation Points (Cut Vertices) in an undirected graph
 *         (Tarjan's algorithm, same family as Bridges)
 * ===================================================================
 *
 * PROBLEM
 *   Find every node whose removal (with its edges) disconnects the graph
 *   (or splits a component into more pieces).
 *   GfG expects the answer sorted, and [-1] if there are none.
 *
 * KEY IDEA
 *   Same DFS as bridges, with the same two arrays:
 *
 *     time[node] = moment the node was first visited
 *     lowT[node] = earliest visit-time reachable from node's DFS subtree
 *                  (tree edges + at most one back edge)
 *
 *   A NON-ROOT node is an articulation point if it has a child 'it' with:
 *
 *        lowT[it] >= time[node]
 *
 *   Meaning: the child's subtree cannot climb ABOVE 'node'. Its best escape
 *   is 'node' itself, so removing 'node' cuts that subtree off.
 *
 *   The ROOT is special: it has nothing above it, so the condition above is
 *   meaningless. The root is an articulation point only if it has
 *   MORE THAN ONE child in the DFS tree.
 *
 * BRIDGES vs ARTICULATION POINTS (easy to mix up)
 *   Bridges             : lowT[it] >  time[node]   -> record the EDGE (node, it)
 *   Articulation points : lowT[it] >= time[node]   -> record the NODE 'node'
 *   (>= because reaching 'node' itself still doesn't save you if 'node' is deleted)
 *   Back edge update    : use time[it], NOT lowT[it], for articulation points.
 *
 * STEPS
 *   1. Build the adjacency list (both directions).
 *   2. DFS from every unvisited node (graph may be disconnected).
 *   3. On entering a node: time = lowT = timer++.
 *   4. For each neighbour 'it':
 *        - it == parent        -> skip
 *        - it not visited      -> child++, dfs(it), then
 *                                 lowT[node] = min(lowT[node], lowT[it])
 *                                 if lowT[it] >= time[node] and node is NOT root -> add node
 *        - it already visited  -> back edge,
 *                                 lowT[node] = min(lowT[node], time[it])
 *   5. After the loop: if node is the root and child > 1 -> add node.
 *   6. Use a Set (a node can be flagged by several children), then sort.
 *      If the set is empty, return [-1].
 *
 * DRY RUN
 *   n = 5, edges = [[0,1],[1,4],[4,3],[4,2],[2,3]]
 *
 *   0 - 1 - 4 - 3
 *           |   |
 *           +-2-+      (4, 3, 2 form a triangle)
 *
 *   dfs(0): time=low=0                       (root, parent = -1)
 *     dfs(1): time=low=1
 *       dfs(4): time=low=2
 *         dfs(3): time=low=3
 *           dfs(2): time=low=4
 *             4 is visited (back edge): low[2] = min(4, time[4]=2) = 2
 *           low[3] = min(3, 2) = 2
 *           low[2]=2 >= time[3]=3 ? NO
 *         low[4] = min(2, 2) = 2
 *         low[3]=2 >= time[4]=2 ? YES, 4 is not root -> add 4
 *       low[1] = min(1, 2) = 1
 *       low[4]=2 >= time[1]=1 ? YES, 1 is not root -> add 1
 *     low[0] = min(0, 1) = 0
 *     low[1]=1 >= time[0]=0 is true BUT 0 is the root -> use child count
 *   root 0 has only 1 child -> NOT an articulation point
 *
 *   Answer: [1, 4]
 *
 * COMPLEXITY
 *   Time  : O(V + E)
 *   Space : O(V + E) adjacency list + O(V) recursion stack
 *
 * THINGS TO REMEMBER
 *   - ROOT RULE: root is an articulation point only if it has 2+ DFS children.
 *     Skipping the root entirely (parent != -1 check alone) is a BUG: it misses
 *     cases like a star graph, where the centre is the root.
 *   - Condition is >= (bridges use >).
 *   - Back edge uses time[it] (bridges can get away with lowT[it]).
 *   - Use a Set because one node can be flagged several times.
 *   - Return sorted, and [-1] when there are none.
 *   - Count 'child' only for tree edges (the unvisited branch), not back edges.
 */
class Solution {

    static ArrayList<Integer> articulationPoints(int n, int[][] connections) {

        // Adjacency list: adj.get(u) = all neighbours of u
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        // Undirected graph, so store the edge in both directions
        for (int[] edges : connections) {
            int u = edges[0];
            int v = edges[1];

            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int[] time = new int[n];      // discovery time of each node
        int[] lowT = new int[n];      // lowest discovery time reachable from each node
        int[] visited = new int[n];   // 0 = not visited, 1 = visited
        Set<Integer> res = new HashSet<>();   // articulation points (Set avoids duplicates)
        int[] timer = {0};            // shared counter (array so recursion can modify it)

        // Start a DFS from every unvisited node (handles disconnected graphs)
        for (int i = 0; i < n; i++) {
            if (visited[i] == 0) {
                dfs(i, -1, visited, adj, time, lowT, res, timer);   // -1 = no parent (root)
            }
        }

        ArrayList<Integer> answer = new ArrayList<>(res);
        Collections.sort(answer);   // HashSet order is not guaranteed, so sort

        // Problem convention: no articulation points -> [-1]
        if (answer.isEmpty()) answer.add(-1);

        return answer;
    }

    // ---------------------------------------------------------------
    // DFS that computes time[] and lowT[] and records articulation points
    // ---------------------------------------------------------------
    public static void dfs(int node, int parent, int[] visited,
                           ArrayList<ArrayList<Integer>> connections,
                           int[] time, int[] lowT,
                           Set<Integer> res, int[] timer) {

        visited[node] = 1;

        // First visit: discovery time and low value both start as the current timer
        time[node] = lowT[node] = timer[0]++;

        int child = 0;   // number of DFS-tree children (needed for the root rule)

        for (int it : connections.get(node)) {

            // Don't walk back along the edge we just came from
            if (it == parent) continue;

            if (visited[it] == 0) {
                // Tree edge: explore the neighbour first
                dfs(it, node, visited, connections, time, lowT, res, timer);

                // Child may have found a route to an earlier node, so inherit its low value
                lowT[node] = Math.min(lowT[it], lowT[node]);

                // Articulation condition (non-root only): the child's subtree cannot
                // climb above 'node', so removing 'node' would cut it off
                if (lowT[it] >= time[node] && parent != -1) {
                    res.add(node);
                }

                child++;   // counts this branch as a DFS child
            } else {
                // Back edge to an already visited node (part of a cycle).
                // Use time[it] here, not lowT[it]
                lowT[node] = Math.min(lowT[node], time[it]);
            }
        }

        // Root rule: root is an articulation point only if it has more than one child
        if (parent == -1 && child > 1) {
            res.add(node);
        }
    }
}

// -------------------------------------------------------------------
// Demo: run this file to test. Leave this class out when pasting on GfG.
// -------------------------------------------------------------------
public class ArticulationPoints {
    public static void main(String[] args) {
        int n = 5;
        int[][] connections = {{0, 1}, {1, 4}, {4, 3}, {4, 2}, {2, 3}};

        // Expected: [1, 4]
        System.out.println(Solution.articulationPoints(n, connections));

        // Star graph: root 0 has 3 children, so 0 must be reported (tests the root rule)
        int[][] star = {{0, 1}, {0, 2}, {0, 3}};

        // Expected: [0]
        System.out.println(Solution.articulationPoints(4, star));

        // A single cycle has no articulation points
        int[][] cycle = {{0, 1}, {1, 2}, {2, 0}};

        // Expected: [-1]
        System.out.println(Solution.articulationPoints(3, cycle));
    }
}