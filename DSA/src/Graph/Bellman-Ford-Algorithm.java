/*
 * Bellman-Ford Algorithm (GfG: Bellman-Ford)
 *
 * PROBLEM: Directed weighted graph with V nodes (0..V-1), edges {u, v, wt}.
 *          Weights can be NEGATIVE. Find the shortest distance from src to
 *          every node. If a negative cycle is reachable from src, return [-1].
 *
 * APPROACH: Relax ALL edges, repeat V-1 times.
 *   - Relaxing an edge u -> v with weight wt means:
 *         if dist[u] + wt < dist[v]  ->  dist[v] = dist[u] + wt
 *   - Why V-1 passes? A shortest path (without cycles) has at most V-1 edges.
 *     After pass i, every node whose shortest path uses <= i edges is correct.
 *     So after V-1 passes, all shortest distances are final.
 *   - Why not Dijkstra? Dijkstra assumes non-negative weights. A negative
 *     edge can make an already "finalized" node cheaper later. Bellman-Ford
 *     doesn't finalize anything early, so negative edges are fine.
 *
 * NEGATIVE CYCLE DETECTION:
 *   - Do ONE more pass (the V-th pass). If any edge can still be relaxed,
 *     distances keep shrinking forever -> a negative cycle exists
 *     -> return [-1].
 *   - If no edge relaxes, there is no negative cycle.
 *
 * WHY THE dist[u] != INF CHECK:
 *   - INF means "not reachable yet". Without the check, INF + negative
 *     weight would look like a valid improvement (and INF + positive
 *     could overflow). We only relax from nodes we have actually reached.
 *
 * UNREACHABLE NODES: stay at INF (1e8) in the answer, as the problem expects.
 *
 * Time:  O(V * E)
 * Space: O(V)
 *
 * Compare: Dijkstra is faster, O((V+E) log V), but fails with negative weights.
 */
class Solution {
    private static final int INF = (int) 1e8; // "not reachable yet"

    public ArrayList<Integer> bellmanFord(int V, int[][] edges, int src) {
        ArrayList<Integer> result = new ArrayList<>();

        // ---------- STEP I: Initialize distances ----------
        // dist[i] = shortest known distance from src to i
        int[] dist = new int[V];
        Arrays.fill(dist, INF);
        dist[src] = 0; // distance to the source itself is 0

        // ---------- STEP II: Relax all edges V-1 times ----------
        for (int i = 0; i < V - 1; i++) {
            boolean changed = false;

            for (int[] edge : edges) {
                int u = edge[0];
                int v = edge[1];
                int wt = edge[2];

                // Only relax from reachable nodes, and only if it improves dist[v]
                if (dist[u] != INF && dist[u] + wt < dist[v]) {
                    dist[v] = dist[u] + wt;
                    changed = true;
                }
            }

            // Early exit: nothing changed in a full pass, so nothing ever will
            if (!changed) {
                break;
            }
        }

        // ---------- STEP III: Negative cycle check (the V-th pass) ----------
        // If any edge can STILL be relaxed after V-1 passes, a negative cycle exists
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int wt = edge[2];

            if (dist[u] != INF && dist[u] + wt < dist[v]) {
                result.add(-1);
                return result;
            }
        }

        // ---------- STEP IV: Answer ----------
        // No negative cycle: return the shortest distance to every node
        for (int i = 0; i < V; i++) {
            result.add(dist[i]);
        }

        return result;
    }
}

/*
 * NOTE: Why a loop and not a queue? (SPFA = queue-based Bellman-Ford)
 *
 * IDEA: Instead of relaxing ALL edges every pass, keep a queue starting
 *       from src. Pop a node u, relax only ITS outgoing edges, and push a
 *       neighbor v only when dist[v] improved (and it isn't already queued).
 *       This is called SPFA (Shortest Path Faster Algorithm).
 *
 * WHY THE PLAIN LOOP IS THE DEFAULT:
 *   1) Simple + guaranteed bound: always O(V*E), no surprises.
 *   2) Negative cycle detection is trivial: just do one more pass (V-th pass).
 *      With a queue, a negative cycle keeps re-adding nodes FOREVER, so you
 *      must detect it yourself:
 *          - track pathLen[v] = number of edges in v's best path, and if
 *            pathLen[v] >= V -> negative cycle, OR
 *          - count how many times each node enters the queue (>= V -> cycle).
 *   3) SPFA worst case is ALSO O(V*E). It is usually much faster in practice
 *      (skips edges that can't improve anything), but bad inputs can slow it.
 *   4) The loop matches the proof: "after pass i, all shortest paths using
 *      <= i edges are correct". That is why V-1 passes + 1 check pass works.
 *
 * SPFA DETAILS TO REMEMBER:
 *   - Needs an adjacency list (to relax only the popped node's edges).
 *   - Keep inQueue[] so a node is never in the queue twice at once.
 *   - No "dist[u] != INF" check needed: only reached nodes enter the queue.
 *
 * INTERVIEW TIP: Write the loop version by default. Mention SPFA as the
 *   practical optimization, and mention the extra cycle-detection it needs.
 */