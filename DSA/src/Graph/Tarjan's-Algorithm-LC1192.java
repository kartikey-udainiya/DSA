import java.util.*;

/*
 * ===================================================================
 *  NOTES: Critical Connections in a Network (LeetCode 1192)
 *         = Finding BRIDGES in an undirected graph (Tarjan's algorithm)
 * ===================================================================
 *
 * PROBLEM
 *   n servers (0..n-1) and undirected connections [u, v].
 *   A connection is CRITICAL (a bridge) if removing it disconnects the graph.
 *   Return all critical connections.
 *
 * KEY IDEA
 *   Run one DFS and keep two numbers for every node:
 *
 *     time[node] = the moment (timer value) the node was first visited
 *     lowT[node] = the EARLIEST visit-time reachable from this node's DFS
 *                  subtree, using tree edges plus at most one back edge
 *
 *   An edge (node -> it) is a BRIDGE if:
 *
 *        lowT[it] > time[node]
 *
 *   Meaning: nothing in the subtree of 'it' can reach 'node' or any ancestor
 *   of 'node' through another route. The ONLY way back is this very edge,
 *   so cutting it disconnects 'it'.
 *
 * STEPS
 *   1. Build the adjacency list (add both directions, graph is undirected).
 *   2. DFS from every unvisited node (graph may have several components).
 *   3. On entering a node: set time = lowT = timer++.
 *   4. For each neighbour 'it':
 *        - it == parent        -> skip (don't go back on the edge we came from)
 *        - it not visited      -> dfs(it), then
 *                                 lowT[node] = min(lowT[node], lowT[it])
 *                                 if lowT[it] > time[node] -> BRIDGE
 *        - it already visited  -> it is a back edge,
 *                                 lowT[node] = min(lowT[node], lowT[it])
 *
 * DRY RUN
 *   n = 4, connections = [[0,1],[1,2],[2,0],[1,3]]
 *   (0,1,2 form a cycle, 3 hangs off node 1)
 *
 *   dfs(0): time=low=0
 *     dfs(1): time=low=1
 *       skip parent 0
 *       dfs(2): time=low=2
 *         skip parent 1
 *         0 is visited -> back edge, low[2] = min(2, 0) = 0
 *       back in 1: low[1] = min(1, 0) = 0
 *                  bridge? low[2]=0 > time[1]=1 ? NO
 *       dfs(3): time=low=3
 *         skip parent 1
 *       back in 1: low[1] = min(0, 3) = 0
 *                  bridge? low[3]=3 > time[1]=1 ? YES -> [1,3]
 *     back in 0: low[0] = min(0, 0) = 0
 *                bridge? low[1]=0 > time[0]=0 ? NO
 *
 *   Answer: [[1, 3]]
 *
 * COMPLEXITY
 *   Time  : O(V + E), each node and edge is processed once
 *   Space : O(V + E) for the adjacency list, plus O(V) recursion stack
 *
 * THINGS TO REMEMBER
 *   - Bridge condition uses a STRICT greater-than: lowT[it] > time[node]
 *     (equal means a back edge reaches node itself, so there is a cycle).
 *   - Skip the parent so the edge we came through isn't treated as a back edge.
 *     (This is fine here because LeetCode guarantees no duplicate edges.)
 *   - Update lowT[node] AFTER the recursive call returns, before the check.
 *   - timer is an int[1] so every recursive call shares the same counter.
 *   - Loop DFS over all nodes for disconnected graphs.
 *   - For a back edge, using time[it] is the textbook version. Using lowT[it]
 *     (as below) also works for bridges, but not for articulation points.
 */
public class CriticalConnections {

    // ---------------------------------------------------------------
    // Builds the graph and starts the DFS
    // ---------------------------------------------------------------
    public static List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {

        // Adjacency list: adj.get(u) = all neighbours of u
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        // Undirected graph, so store the edge in both directions
        for (List<Integer> edges : connections) {
            int u = edges.get(0);
            int v = edges.get(1);

            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int[] time = new int[n];      // discovery time of each node
        int[] lowT = new int[n];      // lowest discovery time reachable from each node
        int[] visited = new int[n];   // 0 = not visited, 1 = visited
        List<List<Integer>> res = new ArrayList<>();   // bridges found
        int[] timer = {0};            // shared counter (array so recursion can modify it)

        // Start a DFS from every unvisited node (handles disconnected graphs)
        for (int i = 0; i < n; i++) {
            if (visited[i] == 0) {
                dfs(i, -1, visited, adj, time, lowT, res, timer);   // -1 = no parent
            }
        }
        return res;
    }

    // ---------------------------------------------------------------
    // DFS that computes time[] and lowT[] and records bridges
    // ---------------------------------------------------------------
    public static void dfs(int node, int parent, int[] visited,
                           ArrayList<ArrayList<Integer>> connections,
                           int[] time, int[] lowT,
                           List<List<Integer>> res, int[] timer) {

        visited[node] = 1;

        // First visit: discovery time and low value both start as the current timer
        time[node] = lowT[node] = timer[0]++;

        for (int it : connections.get(node)) {

            // Don't walk back along the edge we just came from
            if (it == parent) continue;

            if (visited[it] == 0) {
                // Tree edge: explore the neighbour first
                dfs(it, node, visited, connections, time, lowT, res, timer);

                // Child may have found a route to an earlier node, so inherit its low value
                lowT[node] = Math.min(lowT[it], lowT[node]);

                // Bridge condition: the child's subtree cannot reach 'node' or above
                // by any other route, so this edge is the only link
                if (lowT[it] > time[node]) {
                    res.add(new ArrayList<>(Arrays.asList(node, it)));
                }
            } else {
                // Back edge to an already visited node (part of a cycle),
                // so 'node' can reach that earlier node
                lowT[node] = Math.min(lowT[node], lowT[it]);
            }
        }
    }

    // ---------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------
    public static void main(String[] args) {
        int n = 4;
        List<List<Integer>> connections = new ArrayList<>();
        connections.add(Arrays.asList(0, 1));
        connections.add(Arrays.asList(1, 2));
        connections.add(Arrays.asList(2, 0));
        connections.add(Arrays.asList(1, 3));

        // Expected: [[1, 3]]
        System.out.println(criticalConnections(n, connections));
    }
}