public class Main {
    public static void main(String[] args) {
        int[][] edges = {{0, 1, 2}, {0, 4, 1}, {4, 5, 4}, {4, 2, 2}, {1, 2, 3}, {2, 3, 6}, {5, 3, 1}};

        System.out.println(shortestPath(6, edges));
    }

    public static ArrayList<Integer> shortestPath(int V, int[][] edges) {
//        Step 1 create weighted Adj list
        ArrayList<ArrayList<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int wt = edge[2];
            adj.get(u).add(new int[]{v, wt});
        }

        // step II topo sort and build the stack

        int[] visited = new int[V];
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < V; i++) {
            if (visited[i] == 0) {
                topoSort(i, adj, st, visited);
            }
        }

        //step III find the shortest distance.
        int[] distance = new int[V];
        Arrays.fill(distance, Integer.MAX_VALUE);

        distance[0] = 0;
        while (!st.empty()) {
            int node = st.pop();

            for (int[] neighbor : adj.get(node)) {
                int v = neighbor[0];
                int wt = neighbor[1];
                if (distance[node] + wt < distance[v]) {
                    distance[v] = distance[node] + wt;
                }
            }
        }

        // converting answer in the required format
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            res.add(distance[i]);

        }
        return res;
    }

    public static void topoSort(int node, ArrayList<ArrayList<int[]>> adj, Stack<Integer> st, int[] visited) {
        visited[node] = 1;
        for (int[] neighbor : adj.get(node)) {
            int v = neighbor[0];
            if (visited[v] == 0) {
                topoSort(v, adj, st, visited);
            }
        }
        st.add(node);
    }
}