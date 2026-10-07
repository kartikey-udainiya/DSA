public class Main {
    public static void main(String[] args) {
        int V = 5;
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        adj.add(new ArrayList<>(Arrays.asList(1, 2)));
        adj.add(new ArrayList<>(Arrays.asList(0, 2)));
        adj.add(new ArrayList<>(Arrays.asList(0, 1, 3)));
        adj.add(new ArrayList<>(Arrays.asList(2, 4)));
        adj.add(new ArrayList<>(Arrays.asList(3)));
        System.out.println(cycleDetection(V,adj));

    }
    public static boolean cycleDetection(int v, ArrayList<ArrayList<Integer>> adj){
        boolean[] visited = new boolean[v];
        for (int i = 0; i < v; i++) {
            if(!visited[i]){
                if(dfsCycleDetection(visited,i,adj,-1)){
                    return true;
                };
            }
        }
        return false;
    }
    public static boolean dfsCycleDetection(boolean[] visited,int node,ArrayList<ArrayList<Integer>> adj,int parent){
        visited[node]=true;
        for(Integer neighbor : adj.get(node)){
            if(!visited[neighbor]){
                if (dfsCycleDetection(visited, neighbor, adj, node)) {
                    return true;
                }
            }else if(neighbor!=parent){
                return true;
            }
        }
        return false;
    }