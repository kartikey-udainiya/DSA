class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] cost = new int[n][n];

        int INF = (int)1e8;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(i==j){
                    cost[i][j]=0;
                }else{
                    cost[i][j]=INF;
                }
            }
        }

        for(int [] edge : edges){
            int u = edge[0];
            int v = edge[1];
            int wt = edge[2];

            cost[u][v]=wt;
            cost[v][u]=wt;
        }

        for (int via = 0; via < n; via++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if(cost[i][via]==INF || cost[via][j]==INF){
                        continue;
                    }
                    cost[i][j]= Math.min(cost[i][j],cost[i][via]+cost[via][j]);
                }
            }
        }

        int [] ans = new int[n];

        for (int i = 0; i < n; i++) {
            int number = 0;
            for (int j = 0; j < n; j++) {
                if(cost[i][j]<=distanceThreshold){
                    number++;
                }
            }
            ans[i]=number;
        }

        int min = Integer.MAX_VALUE;
        int res = 0 ;

        for (int i = 0; i < n; i++) {
            if(ans[i]<=min){
                res = i;
                min=ans[i];
            }
        }
        return res;
    }
}