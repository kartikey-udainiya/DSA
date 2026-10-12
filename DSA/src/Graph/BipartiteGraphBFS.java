class Solution {
    public boolean isBipartite(int V, int[][] edges) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        for(int[] edge : edges){
            int u = edge[0];
            int v = edge[1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }
        Queue<Integer> q = new LinkedList<>();
        int[]visited = new int[V];
        for (int i = 0; i < V; i++) {
            if(visited[i]==0){
                q.add(i);
                visited[i]=-1;
                if(!bfs(q,visited,adj)){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean bfs(Queue<Integer> q,int[] visited,ArrayList<ArrayList<Integer>> adj){
        while(!q.isEmpty()){
            int node = q.poll();

            for(int neighbor : adj.get(node)){
                if(visited[neighbor]==0){
                    visited[neighbor]=-visited[node];
                    q.add(neighbor);
                }
                else if(visited[neighbor]==visited[node]){
                    return false;
                }
            }
        }
        return true;
    }
}