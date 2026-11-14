class Solution {
    public void floydWarshall(int[][] dist) {
        int n = dist.length;          // number of nodes (square matrix)
        int INF = (int) 1e8;          // "no path" value; match the problem's sentinel

        // 'via' is the intermediate node we are allowed to route through.
        // MUST be the outermost loop.
        for (int via = 0; via < n; via++) {

            // every possible source node
            for (int i = 0; i < n; i++) {

                // every possible destination node
                for (int j = 0; j < n; j++) {

                    // If either leg is unreachable, the path through 'via'
                    // does not exist, so skip it. This also avoids adding
                    // INF values together.
                    if (dist[i][via] == INF || dist[via][j] == INF) continue;

                    // Take the shorter of:
                    //   1) the current best i -> j
                    //   2) the new path i -> via -> j
                    dist[i][j] = Math.min(dist[i][j], dist[i][via] + dist[via][j]);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (dist[i][i] < 0) {
                // negative cycle exists
            }
        }
    }
}


