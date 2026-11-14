
class Solution {

    public static int countSCC(ArrayList<ArrayList<Integer>> adj) {

        int n = adj.size();

        int[] visited = new int[n];

        Stack<Integer> st = new Stack<>();

        // STEP 1: Perform DFS and store nodes
        // according to their finishing time.

        for (int i = 0; i < n; i++) {

            if (visited[i] == 0) {
                dfs1(i, st, visited, adj);
            }
        }

        // STEP 2: Reverse the graph.

        ArrayList<ArrayList<Integer>> adjT = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adjT.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {

            for (int neighbor : adj.get(i)) {

                // Reverse edge: i -> neighbor
                // becomes neighbor -> i

                adjT.get(neighbor).add(i);
            }
        }

        // STEP 3: Reset visited array.

        Arrays.fill(visited, 0);

        // STEP 4: Process nodes in decreasing
        // order of finishing time.

        int ans = 0;

        while (!st.isEmpty()) {

            int node = st.pop();

            if (visited[node] == 0) {

                // Every new DFS identifies one SCC.
                ans++;

                dfs2(node, visited, adjT);
            }
        }

        return ans;
    }


    // First DFS: Calculate finishing time.

    public static void dfs1(
            int node,
            Stack<Integer> st,
            int[] visited,
            ArrayList<ArrayList<Integer>> adj
    ) {

        visited[node] = 1;

        for (int neighbor : adj.get(node)) {

            if (visited[neighbor] == 0) {
                dfs1(neighbor, st, visited, adj);
            }
        }

        // Push after visiting all neighbours.
        st.push(node);
    }


    // Second DFS: Find an SCC in the reversed graph.

    public static void dfs2(
            int node,
            int[] visited,
            ArrayList<ArrayList<Integer>> adjT
    ) {

        visited[node] = 1;

        for (int neighbor : adjT.get(node)) {

            if (visited[neighbor] == 0) {
                dfs3(neighbor, visited, adjT);
            }
        }
    }
}
