public class Main {
    public static void main(String[] args) {
        int[][]edges = {
                {1, 2, 2},
                {2, 5, 5},
                {2, 3, 4},
                {1, 4, 1},
                {4, 3, 3},
                {3, 5, 1}
        };
        System.out.println(shortestPaths(5,edges,1,5));
    }
    public static ArrayList<Integer> shortestPaths(int V, int[][] edges, int src, int dest) {
        // step I create weighted adj list :
        ArrayList<ArrayList<int[]>> adj = new ArrayList<>();
        for (int i = 0; i <= V; i++) {
            adj.add(new ArrayList<>());
        }

        for(int [] edge : edges){
            int u = edge[0];
            int v = edge[1];
            int wt = edge[2];
            adj.get(u).add(new int[]{v,wt});
            adj.get(v).add(new int[]{u,wt});
        }

        //Step II
        // create minHeap priorityQueue storing pairs (a,b)
        // a = distance ,b = node
        // initialize distance array and priority Queue
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)-> Integer.compare(a[0],b[0]));
        int[] dist = new int[V+1];
        int[] parent = new int[V+1];
        for (int i = 0; i < ; i++) {
            parent[i]=i;
        }
        Arrays.fill(dist,Integer.MAX_VALUE);
        dist[src]=0;
        pq.add(new int[]{0,src});


        //Step III dijkstra

        while(!pq.isEmpty()){
            int []pair = pq.poll();
            int distance = pair[0];
            int node = pair[1];

            if(distance>dist[node]){
                continue;
            }

            for(int [] neighbor : adj.get(node)){
                int neighborNode = neighbor[0];
                int neighborWeight = neighbor[1];

                if(dist[node]+neighborWeight< dist[neighborNode]){
                    dist[neighborNode] = dist[node]+ neighborWeight;
                    parent[neighborNode]=node;
                    pq.add(new int[]{dist[neighborNode],neighborNode});
                }
            }
        }
        // Step 4: If destination is unreachable
        if (dist[dest] == Integer.MAX_VALUE) {
            return new ArrayList<>(Arrays.asList(-1));
        }

        // Step 5: Reconstruct path
        ArrayList<Integer> res = new ArrayList<>();
        int current = dest;

        while (current!=src) {
            res.add(current);
            current = parent[current];
        }

        // Add source
        res.add(src);

        // Reverse path
        Collections.reverse(res);

        return res;
    }