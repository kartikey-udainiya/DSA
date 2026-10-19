
public static int shortestPath(int V, int[][] edges, int src, int dest) {

    // Step 1: Create adjacency list
    ArrayList<ArrayList<Integer>> adj = new ArrayList<>();

    for (int i = 0; i < V; i++) {
        adj.add(new ArrayList<>());
    }

    // Graph is undirected, so add both directions
    for (int[] edge : edges) {

        int u = edge[0];
        int v = edge[1];

        adj.get(u).add(v);
        adj.get(v).add(u);
    }


    // Step 2: Create distance array
    //
    // distance[i] = shortest distance from src to node i
    //
    // -1 means the node has not been visited yet.
    int[] distance = new int[V];
    Arrays.fill(distance, -1);


    // Step 3: BFS
    Queue<Integer> q = new LinkedList<>();

    // Source is at distance 0 from itself
    distance[src] = 0;
    q.add(src);


    while (!q.isEmpty()) {

        int node = q.poll();

        // Explore all neighbors
        for (int neighbor : adj.get(node)) {

            // If this node has not been visited yet
            if (distance[neighbor] == -1) {

                // Neighbor is one edge away from current node
                distance[neighbor] = distance[node] + 1;

                // Add it to the queue for further exploration
                q.add(neighbor);
            }
        }
    }


    // Step 4: Return shortest distance
    //
    // distance[dest] == -1 means destination is unreachable.
    return distance[dest];
}
//```
//
//        ### Why `distance[]` is enough
//
//Your original code had:
//
//        ```java
//Queue<int[]> q = new LinkedList<>();
//
//q.add(new int[]{src, 0});
//        ```
//
//where you stored:
//
//        ```text
//[node, distance]
//        ```
//
//But you already have:
//
//        ```java
//distance[node]
//        ```
//
//So storing the distance again in the queue is redundant.
//
//We only need:
//
//        ```java
//Queue<Integer> q
//```
//
//because:
//
//        ```text
//queue → tells us WHICH node to process
//distance[] → tells us HOW FAR that node is
//```
//
//        ### The core BFS idea
//
//For an **unweighted graph**, every edge has cost `1`.
//
//So when we go:
//
//        ```text
//node → neighbor
//```
//
//the distance becomes:
//
//        ```java
//distance[neighbor] = distance[node] + 1;
//        ```
//
//And because BFS processes nodes level by level:
//
//        ```text
//        Source
//  ↓
//distance 1
//        ↓
//distance 2
//        ↓
//distance 3
//        ↓
//        ...
//        ```
//
//the **first time we visit a node, we have found its shortest path**.
//
//        ### Complexity
//
//```text
//Build adjacency list → O(E)
//
//BFS                 → O(V + E)
//
//Overall              → O(V + E)
//
//Space                → O(V + E)
//```
//
//        ### Pattern to remember
//
//```text
//Unweighted Graph
//       ↓
//BFS
//       ↓
//distance[src] = 0
//        ↓
//distance[neighbor] = distance[node] + 1
//        ↓
//First visit = shortest distance
//```
