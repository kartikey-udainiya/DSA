class Solution {
    public int minimumEffortPath(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;

        int[][] direction = {{0,1},{1,0},{0,-1},{-1,0}};
        int[][] distance = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }

        PriorityQueue<int[]> q = new PriorityQueue<>((a,b)->Integer.compare(a[0],b[0]));
        q.add(new int[]{0,0,0});
        distance[0][0]=0;

        while(!q.isEmpty()){
            int [] values = q.poll();
            int diff = values[0];
            int u = values[1];
            int v = values[2];

            if(diff > distance[u][v]){
                continue;
            }

            if(u==m-1 && v ==n-1){
                return diff;
            }

            for(int [] dir : direction){
                int x = u+dir[0];
                int y = v+dir[1];

                if(x>=0 && x<m && y>=0 && y<n){
                    int cost = Math.abs(mat[u][v]-mat[x][y]);
                    int newEffort = Math.max(cost,diff);
                    if(newEffort<distance[x][y]){
                        distance[x][y]=newEffort;
                        q.add(new int[]{newEffort,x,y});
                    }

                }
            }
        }
        return -1;
    }
}