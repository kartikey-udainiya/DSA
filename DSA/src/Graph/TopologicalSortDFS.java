class Solution {
    public ArrayList<Integer> topoSort(int V, int[][] edges) {
        // create adj list
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for(int i = 0 ; i<V ; i++){
            adj.add(new ArrayList<>());
        }

        for(int [] edge: edges){
            adj.get(edge[0]).add(edge[1]);
        }

        int [] visited = new int [V];
        Stack<Integer> stack = new Stack<>();

        for(int i = 0 ; i<V ; i++){
            if(visited[i]==0){
                dfs(stack,visited,adj,i);
            }
        }

        return new ArrayList<>(stack.reversed());

    }
    public void dfs(Stack<Integer> stack, int[] visited,ArrayList<ArrayList<Integer>> adj, int node){
        visited[node]=1;

        for(int neighbor : adj.get(node)){
            if(visited[neighbor]==0){
                dfs(stack,visited,adj,neighbor);
            }
        }

        stack.add(node);
    }
}