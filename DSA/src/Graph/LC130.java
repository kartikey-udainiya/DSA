class Solution {
    public void solve(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int [][] visited = new int[m][n];
        int[][] direction = {{0,1},{1,0},{-1,0},{0,-1}};

        //upper and lower
        for (int i = 0; i < n; i++) {
            if(grid[0][i]=='O' && visited[0][i]!=1){
                dfs(visited,grid,0,i,direction);
            }
            if(grid[m-1][i]=='O'&& visited[m-1][i]!=1){
                dfs(visited,grid,m-1,i,direction);
            }
        }
        for (int i = 0; i < m; i++) {
            if(grid[i][0]=='O'&& visited[i][0]!=1){
                dfs(visited,grid,i,0,direction);
            }
            if(grid[i][n-1]=='O' && visited[i][n-1]!=1){
                dfs(visited,grid,i,n-1,direction);
            }
        }

        for (int i = 1; i < m-1; i++) {
            for (int j = 1; j < n-1; j++) {
                if(grid[i][j]=='O' && visited[i][j]!=1){
                    grid[i][j]='X';
                }
            }
        }
    }
    public void dfs(int[][]visited, char[][]grid,int i,int j,int[][]direction){
        visited[i][j]=1;
        for(int[] dir : direction){
            int x = i + dir[0];
            int y = j + dir[1];

            if(x>=0 && x< grid.length && y>=0 && y<grid[0].length){
                if(grid[x][y]=='O' && visited[x][y]==0){
                    visited[x][y]=1;
                    dfs(visited,grid,x,y,direction);
                }
            }
        }
    }
}
