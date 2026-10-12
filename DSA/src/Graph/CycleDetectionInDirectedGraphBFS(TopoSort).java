## Kahn's Algorithm — Directed Cycle Detection

        ### Idea

Kahn's Algorithm uses **BFS + Indegree**.

        * If we can process all `V` nodes → **no cycle**
        * If we cannot process all `V` nodes → **cycle exists**

A cycle keeps its nodes' indegree greater than `0`, so they can never enter the queue.

        ### Code

```java
class Solution {

    public boolean isCyclic(int V, int[][] edges) {

        // Create adjacency list
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        // Calculate indegree
        int[] inDegree = new int[V];

        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];

            adj.get(from).add(to);
            inDegree[to]++;
        }

        // Add nodes with indegree 0
        Queue<Integer> q = new LinkedList<>();

        for (int i = 0; i < V; i++) {
            if (inDegree[i] == 0) {
                q.add(i);
            }
        }

        // Process nodes
        int count = 0;

        while (!q.isEmpty()) {

            int node = q.poll();
            count++;

            for (int neighbor : adj.get(node)) {

                inDegree[neighbor]--;

                if (inDegree[neighbor] == 0) {
                    q.add(neighbor);
                }
            }
        }

        // If all nodes were processed, there is no cycle
        return count == V;
    }
}
```

        ### Example

```text
0 → 1 → 2
        ```

Indegree:

        ```text
0 = 0
        1 = 1
        2 = 1
        ```

Queue starts with:

        ```text
[0]
        ```

Process:

        ```text
0 → 1 → 2
        ```

All `3` nodes are processed:

        ```text
        count = V
```

Therefore:

        ```text
No cycle
```

For a cycle:

        ```text
0 → 1 → 2
        ↑     ↓
        └─────┘
        ```

The cyclic nodes never reach indegree `0`.

Therefore:

        ```text
count < V
```

and we return:

        ```text
true → cycle exists
```

        ### Complexity

Building adjacency list:

        ```text
O(V + E)
```

Kahn's BFS:

        ```text
O(V + E)
```

Overall:

        ```text
Time:  O(V + E)
Space: O(V + E)
```

        ### Important Formula

```text
count == V  → No cycle
count < V   → Cycle exists
```

Kahn's Algorithm can therefore be used for:

        1. **Topological Sort**
        2. **Directed Cycle Detection**
        3. **Course Schedule**
        4. **Course Schedule II**
        5. **Eventual Safe Nodes** using a reversed graph
