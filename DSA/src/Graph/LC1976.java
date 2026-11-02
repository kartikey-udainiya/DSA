/*
 * LeetCode 1976: Number of Ways to Arrive at Destination
 *
 * PROBLEM: Undirected weighted graph, n intersections (0..n-1).
 *          Count how many DIFFERENT paths take the SHORTEST possible time
 *          from node 0 to node n-1. Return count modulo 1e9+7.
 *
 * APPROACH: Dijkstra (finds shortest times) + DP (counts shortest paths).
 *
 *   Part 1: Dijkstra
 *   - Gives the shortest time to every node: totalTime[node].
 *   - Also gives the PROCESSING ORDER: nodes are popped in increasing
 *     distance from the source.
 *
 *   Part 2: DP on top of Dijkstra  (ways[] is the DP array)
 *   - State:      ways[v] = number of shortest paths from node 0 to v.
 *   - Base case:  ways[0] = 1 (one way to be at the start: stay there).
 *   - Transition: for every edge u -> v that lies on a shortest path
 *                 (dist[u] + w == dist[v]):   ways[v] += ways[u]
 *   - Same pattern as "count paths in a grid"
 *     (dp[i][j] = dp[i-1][j] + dp[i][j-1]): sum over predecessors.
 *     Here the predecessors are only the nodes on shortest-path edges,
 *     which form a DAG.
 *
 *   Why Dijkstra is needed for the DP:
 *   - DP needs subproblems solved BEFORE the ones that depend on them.
 *     In a grid, row-by-row order does that. In a weighted graph, the right
 *     order is "increasing distance from source" = Dijkstra's pop order.
 *   - Edge weights are positive (>= 1), so every predecessor u of v on a
 *     shortest path has dist[u] < dist[v]. So ways[u] is already FINAL when
 *     it gets added into ways[v].
 *
 *   Difference from classic DP:
 *   - Not a fixed-order table or memoization. The "table" is filled in the
 *     order the heap dictates, and ways[v] is RESET when a shorter route is
 *     found. That reset keeps only shortest-path counts.
 *
 *   When relaxing edge (node -> neighbor) with newTime = time + edgeTime:
 *     1) newTime <  totalTime[neighbor] -> STRICTLY BETTER route found.
 *          Replace the count: ways[neighbor] = ways[node]
 *          and push neighbor to the heap.
 *     2) newTime == totalTime[neighbor] -> ANOTHER route ties the best.
 *          Add the counts: ways[neighbor] += ways[node]
 *          (no push needed; neighbor is already in the heap).
 *     3) newTime >  totalTime[neighbor] -> useless, ignore.
 *
 * WHY long FOR TIME: total time can exceed int range (up to ~1e9 * 200).
 * WHY MOD: the number of paths can be astronomically large.
 *
 * Time:  O((V + E) log V)  (standard Dijkstra)
 * Space: O(V + E)
 *
 * SAME TRICK, OTHER GRAPHS: on an UNWEIGHTED graph use BFS instead of
 * Dijkstra (processing order = level by level); the ways[] DP is identical.
 */
class Solution {
    private static final int MOD = 1_000_000_007;

    public int countPaths(int n, int[][] roads) {

        // ---------- STEP I: Build undirected weighted adjacency list ----------
        // adj.get(u) = list of {neighbor, travelTime}
        ArrayList<ArrayList<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int time = road[2];
            adj.get(u).add(new int[]{v, time});
            adj.get(v).add(new int[]{u, time}); // undirected: add both directions
        }

        // ---------- STEP II: Initialize Dijkstra state + DP array ----------
        // totalTime[i] = shortest known time from 0 to i (long to avoid overflow)
        long[] totalTime = new long[n];
        Arrays.fill(totalTime, Long.MAX_VALUE);
        totalTime[0] = 0;

        // DP array: ways[i] = number of shortest paths from 0 to i (mod MOD)
        int[] ways = new int[n];
        ways[0] = 1; // DP base case

        // Min-heap ordered by time. Entries are {timeSoFar, node}
        PriorityQueue<long[]> minHeap = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        minHeap.add(new long[]{0, 0});

        // ---------- STEP III: Dijkstra with path counting (DP) ----------
        while (!minHeap.isEmpty()) {
            long[] top = minHeap.poll();
            long time = top[0];
            int node = (int) top[1];

            // Stale entry: a faster route to this node was already processed.
            // Also guarantees we never process (and count from) a node twice.
            if (time > totalTime[node]) {
                continue;
            }

            for (int[] neighbor : adj.get(node)) {
                int neighborNode = neighbor[0];
                int edgeTime = neighbor[1];
                long newTime = time + edgeTime;

                if (newTime < totalTime[neighborNode]) {
                    // Strictly faster route: RESET the DP value to this path's count
                    totalTime[neighborNode] = newTime;
                    ways[neighborNode] = ways[node];
                    minHeap.add(new long[]{newTime, neighborNode});
                } else if (newTime == totalTime[neighborNode]) {
                    // Equally fast route: DP transition, add predecessor's count
                    ways[neighborNode] = (ways[neighborNode] + ways[node]) % MOD;
                }
                // else: slower route, ignore
            }
        }

        // ---------- STEP IV: Answer ----------
        // Number of shortest paths from node 0 to node n-1
        return ways[n - 1];
    }
}