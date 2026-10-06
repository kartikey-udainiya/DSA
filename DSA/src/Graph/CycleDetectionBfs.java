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
        Queue<int[]> q = new LinkedList<>();
        for (int i = 0; i < v; i++) {
            if(!visited[i]){
                visited[i]=true;
                q.add(new int[]{i,-1});
                if(bfsCycleDetection(adj,visited,q)){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean bfsCycleDetection(ArrayList<ArrayList<Integer>> adj,boolean[] visited ,Queue<int[]> q){
        while(!q.isEmpty()){
            int[] arr = q.poll();
            int node = arr[0];
            int origin = arr[1];

            ArrayList<Integer> connectedNodes = adj.get(node);
            for(Integer neighbor:connectedNodes){
                if(!visited[neighbor]){
                    q.add(new int[]{neighbor,node});
                    visited[neighbor]=true;
                }else if(neighbor!=origin){
                    return true;
                }
            }
        }
        return false;
    }
