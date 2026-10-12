public class Main {
    public static void main(String[] args) {
        int[][] edges = {{0,1},{1,2},{2,3},{2,0}};
        System.out.println(isCyclic(4,edges));
    }
    public static boolean isCyclic(int V, int[][] edges) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();

        //adj list from edges
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
        for(int[] edge : edges){
            int u = edge[0];
            int v = edge[1];
            adj.get(u).add(v);
        }
        // path and visited arrays

        int[] visited = new int[V];
        int[] path = new int[V];

        for (int i = 0; i < V; i++) {
            if(visited[i]==0){
                if(dfsCycle(i,visited,path,adj)){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean dfsCycle(int node,int[] visited, int[] path,ArrayList<ArrayList<Integer>> adj){
        if(visited[node]==1 && path[node]==1){
            return true;
        }
        visited[node]=1;
        path[node]=1;
        for(int neighbor : adj.get(node)){
            if(visited[neighbor]==0){
                if(dfsCycle(neighbor,visited,path,adj)){
                    return true;
                }
            }else if(path[neighbor]==1){
                return true;
            }

        }
        path[node]=0;
        return false;
    }
}