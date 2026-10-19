public static int[] dijkstra(int V, int[][] edges, int src) {

    // Step 1: Create weighted adjacency list
    ArrayList<ArrayList<int[]>> adj = new ArrayList<>();

    for (int i = 0; i < V; i++) {
        adj.add(new ArrayList<>());
    }

    for (int[] edge : edges) {

        int u = edge[0];
        int v = edge[1];
        int wt = edge[2];

        adj.get(u).add(new int[]{v, wt});
        adj.get(v).add(new int[]{u, wt});
    }


    // Step 2: Distance array
    int[] dist = new int[V];

    Arrays.fill(dist, Integer.MAX_VALUE);

    dist[src] = 0;


    // Step 3: TreeSet
    // Stores (distance, node)
    TreeSet<Pair> set = new TreeSet<>(
            (a, b) -> {
                if (a.distance != b.distance) {
                    return Integer.compare(a.distance, b.distance);
                }

                return Integer.compare(a.node, b.node);
            }
    );

    set.add(new Pair(0, src));


    // Step 4: Dijkstra
    while (!set.isEmpty()) {

        // Get pair with smallest distance
        Pair current = set.pollFirst();

        int distance = current.distance;
        int node = current.node;


        // Step 5: Explore neighbors
        for (int[] neighbor : adj.get(node)) {

            int neighborNode = neighbor[0];
            int weight = neighbor[1];

            // Relaxation
            if (distance + weight < dist[neighborNode]) {

                // Remove old pair if it exists
                if (dist[neighborNode] != Integer.MAX_VALUE) {
                    set.remove(
                            new Pair(dist[neighborNode], neighborNode)
                    );
                }

                // Update distance
                dist[neighborNode] =
                        distance + weight;

                // Add new pair
                set.add(
                        new Pair(dist[neighborNode], neighborNode)
                );
            }
        }
    }

    return dist;
}