class Solution {
    public List<Integer> eventualSafeNodes(int[][] edges) {
        // create adj list
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < edges.length; i++) {
            adj.add((new ArrayList<>()));
        }
        for(int i = 0 ; i<edges.length ;i++){
            for(int j : edges[i]){
                adj.get(i).add(j);
            }
        }

        int [] visited = new int[edges.length];
        int [] path = new int[edges.length];
        List<Integer> res = new ArrayList<>();

        for (int i = 0; i < edges.length; i++) {
            if(visited[i]==0){
                if(dfsCycle(i,visited,path,adj)){
                    continue;
                }else{
                    res.add(i);
                }
            }else if(visited[i]==1 && path[i]==0){
                res.add(i);
            }
        }
        return  res;
    }
    public boolean dfsCycle(int node,int[] visited, int[] path,ArrayList<ArrayList<Integer>> adj){
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