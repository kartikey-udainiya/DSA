public static ArrayList<Integer> topoSortBFS(int V, int[][] edges) {

    // 1. Create the adjacency list
    ArrayList<ArrayList<Integer>> adj = new ArrayList<>();

    for (int i = 0; i < V; i++) {
        adj.add(new ArrayList<>());
    }

    // 2. Build adjacency list and calculate indegree
    int[] inDegree = new int[V];

    for (int[] edge : edges) {
        int from = edge[0];
        int to = edge[1];

        // Directed edge: from → to
        adj.get(from).add(to);

        // 'to' has one more incoming edge
        inDegree[to]++;
    }

    // 3. Add all nodes with no incoming edges
    //    These nodes can be processed first.
    Queue<Integer> queue = new LinkedList<>();

    for (int i = 0; i < V; i++) {
        if (inDegree[i] == 0) {
            queue.add(i);
        }
    }

    // 4. Perform BFS (Kahn's Algorithm)
    ArrayList<Integer> result = new ArrayList<>();

    while (!queue.isEmpty()) {

        int node = queue.poll();

        // Add the node to the topological ordering
        result.add(node);

        // Remove this node's outgoing edges
        for (int neighbor : adj.get(node)) {

            inDegree[neighbor]--;

            // All prerequisites of neighbor are now completed
            if (inDegree[neighbor] == 0) {
                queue.add(neighbor);
            }
        }
    }

    return result;
}