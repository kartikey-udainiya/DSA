public static ArrayList<Integer> dijkstra(int V, int[][] edges, int src) {
    // step I
    // create weighted adj list
    ArrayList<ArrayList<int[]>> adj = new ArrayList<>();
    for (int i = 0; i < V; i++) {
        adj.add(new ArrayList());
    }
    for(int[]edge: edges){
        int u = edge[0];
        int v = edge[1];
        int wt = edge[2];
        adj.get(u).add(new int[]{v,wt});
        adj.get(v).add(new int[]{u,wt});
    }

    // Step - 2
    // creating minHeap and storing pairs
    // (a,b) = a= is distance ,b=node
    // min heap will be created based on = a
    PriorityQueue<int[]> q = new PriorityQueue<>((a,b)-> Integer.compare(a[0],b[0]));
    int [] dist = new int[V];
    q.add(new int[]{0,src});
    Arrays.fill(dist,Integer.MAX_VALUE);
    dist[src]=0;

    //Step III Dijkstra
    while(!q.isEmpty()){
        int [] pair = q.poll();
        int distance = pair[0];
        int node = pair[1];

        // Skip outdated/stale entry
        if (distance > dist[node]) {
            continue;
        }

        for(int [] neighbor:adj.get(node)){
            int neighborNode = neighbor[0];
            int neighborWt =  neighbor[1];

            if(distance+neighborWt<dist[neighborNode]){
                dist[neighborNode] = distance+neighborWt;
                q.add(new int[]{dist[neighborNode],neighborNode});
            }
        }
    }

    ArrayList<Integer> res = new ArrayList<>();
    for (int i = 0; i < V; i++) {
        res.add(dist[i]);
    }

    return res;
}