class Solution {
    class DisjointSet {
        int [] parent ;
        int [] rank ;

        public DisjointSet(int n){
            this.parent = new int[n];
            this.rank = new int[n];

            for (int i = 0; i < n; i++) {
                rank[i]=0;
                parent[i]=i;
            }
        }

        public int findPar(int n){
            if(parent[n]==n){
                return n;
            }

            int ult_par = findPar(parent[n]);
            parent[n]=ult_par;
            return ult_par;
        }

        public void unionByRank(int u,int v){
            int ulp_u = findPar(u);
            int ulp_v = findPar(v);

            if(ulp_u==ulp_v)return;
            if(rank[ulp_u]==rank[ulp_v]){
                rank[ulp_u]+=1;
                parent[ulp_v]=ulp_u;
            } else if (rank[ulp_u]>rank[ulp_v]) {
                parent[ulp_v]=ulp_u;
            }else {
                parent[ulp_u] = ulp_v;
            }
        }
    }
    public ArrayList<Integer> numOfIslands(int n, int m, int[][] A) {
        int [][] res = new int[n][m];
        DisjointSet ds = new DisjointSet(m*n);
        ArrayList<Integer> ans = new ArrayList<>();

        int [][] directions = {{0,1},{-1,0},{0,-1},{1,0}};
        int islandCount = 0 ;

        for (int i = 0; i < A.length; i++) {
            int row = A[i][0];
            int col = A[i][1];

            if(res[row][col]==1){
                ans.add(islandCount);
                continue;
            }

            res[row][col]=1;
            islandCount++;

            int  u = row*m + col;

            for(int []dir : directions) {
                int x = row + dir[0];
                int y = col + dir[1];

                if(x>=0 && x < n && y>=0 && y<m){
                    if(res[x][y]==1){
                        int v = x*m+ y;
                        int ulp_u = ds.findPar(u);
                        int ulp_v = ds.findPar(v);

                        if(ulp_u!=ulp_v){
                            ds.unionByRank(u,v);
                            islandCount--;
                        }
                    }
                }
            }
            ans.add(islandCount);
        }

        return ans;
    }
}