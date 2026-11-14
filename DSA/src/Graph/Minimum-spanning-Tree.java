import java.util.*;

/*
 * Prim's Algorithm (Minimum Spanning Tree)
 *
 * Idea: start from node 0 and keep growing the tree. At every step, pick the
 * cheapest edge that connects a node already in the tree to a node outside it.
 * A min-heap (PriorityQueue) gives us that cheapest edge quickly.
 *
 * Input format (GfG style):
 *   adj.get(u) = list of edges from u, each edge is [v, weight]
 *
 * Time:  O(E log E)
 * Space: O(V + E)
 */
class Solution {

    // ---------------------------------------------------------------
    // Version 1: returns only the total weight of the MST
    // ---------------------------------------------------------------
    public int primMST(ArrayList<ArrayList<ArrayList<Integer>>> adj) {

        // Min-heap ordered by edge weight (index 0 of the array).
        // Each entry: {weight, node}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));

        // visited[i] == 1 means node i is already part of the MST
        int[] visited = new int[adj.size()];

        // Start from node 0 with cost 0 (the first node costs nothing to add)
        pq.add(new int[]{0, 0});

        int sum = 0; // total weight of the MST

        while (!pq.isEmpty()) {
            int[] edge = pq.poll();   // cheapest edge currently available
            int wt = edge[0];         // weight of this edge
            int node = edge[1];       // node this edge leads to

            // This node was already added through a cheaper edge, so skip.
            if (visited[node] == 1) continue;

            // Add the node to the MST and add the edge's cost to the total.
            visited[node] = 1;
            sum += wt;

            // Push all edges leading from this node to unvisited neighbours.
            for (int i = 0; i < adj.get(node).size(); i++) {
                int adjNode = adj.get(node).get(i).get(0); // neighbour
                int adjWt = adj.get(node).get(i).get(1);   // edge weight

                if (visited[adjNode] != 1) {
                    pq.add(new int[]{adjWt, adjNode});
                }
            }
        }
        return sum;
    }

    // ---------------------------------------------------------------
    // Version 2: returns the MST structure as a list of edges.
    // Each edge is {parent, node, weight}.
    // The only change: we also store the PARENT in the heap entry, so
    // when a node is finally accepted we know which edge brought it in.
    // ---------------------------------------------------------------
    public List<int[]> primMSTEdges(ArrayList<ArrayList<ArrayList<Integer>>> adj) {

        // Each entry: {weight, node, parent}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        int[] visited = new int[adj.size()];

        List<int[]> mstEdges = new ArrayList<>(); // edges that make up the MST

        // Start node 0 has no parent, so parent = -1
        pq.add(new int[]{0, 0, -1});

        while (!pq.isEmpty()) {
            int[] edge = pq.poll();
            int wt = edge[0];
            int node = edge[1];
            int parent = edge[2];

            if (visited[node] == 1) continue;
            visited[node] = 1;

            // parent == -1 only for the start node, which has no incoming edge.
            // Every other accepted node contributes exactly one MST edge.
            if (parent != -1) {
                mstEdges.add(new int[]{parent, node, wt});
            }

            for (int i = 0; i < adj.get(node).size(); i++) {
                int adjNode = adj.get(node).get(i).get(0);
                int adjWt = adj.get(node).get(i).get(1);

                if (visited[adjNode] != 1) {
                    // remember that 'node' is the parent of 'adjNode' via this edge
                    pq.add(new int[]{adjWt, adjNode, node});
                }
            }
        }
        return mstEdges;
    }

    // ---------------------------------------------------------------
    // Version 3 (optional): builds the MST as an adjacency list, so you can
    // traverse it like a normal graph. Each entry is {neighbour, weight}.
    // ---------------------------------------------------------------
    public ArrayList<ArrayList<int[]>> buildMSTAdjList(ArrayList<ArrayList<ArrayList<Integer>>> adj) {
        int n = adj.size();
        ArrayList<ArrayList<int[]>> tree = new ArrayList<>();
        for (int i = 0; i < n; i++) tree.add(new ArrayList<>());

        for (int[] e : primMSTEdges(adj)) {
            int u = e[0], v = e[1], w = e[2];
            tree.get(u).add(new int[]{v, w}); // MST is undirected,
            tree.get(v).add(new int[]{u, w}); // so add both directions
        }
        return tree;
    }
}

// -------------------------------------------------------------------
// Demo: copy this class too if you want to run it locally
// -------------------------------------------------------------------
public class Main {

    // helper: adds an undirected edge u - v with weight w
    static void addEdge(ArrayList<ArrayList<ArrayList<Integer>>> adj, int u, int v, int w) {
        adj.get(u).add(new ArrayList<>(Arrays.asList(v, w)));
        adj.get(v).add(new ArrayList<>(Arrays.asList(u, w)));
    }

    public static void main(String[] args) {
        int n = 4;
        ArrayList<ArrayList<ArrayList<Integer>>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        addEdge(adj, 0, 1, 1);
        addEdge(adj, 1, 2, 2);
        addEdge(adj, 0, 2, 4);
        addEdge(adj, 2, 3, 3);

        Solution sol = new Solution();

        // Total weight
        System.out.println("MST weight = " + sol.primMST(adj)); // 6

        // MST edges: parent -> node (weight)
        for (int[] e : sol.primMSTEdges(adj)) {
            System.out.println(e[0] + " - " + e[1] + " (w=" + e[2] + ")");
        }

        // MST as adjacency list
        ArrayList<ArrayList<int[]>> tree = sol.buildMSTAdjList(adj);
        for (int i = 0; i < n; i++) {
            System.out.print(i + " -> ");
            for (int[] nb : tree.get(i)) System.out.print("(" + nb[0] + ", w=" + nb[1] + ") ");
            System.out.println();
        }
    }
}