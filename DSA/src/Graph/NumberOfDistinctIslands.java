class Solution {
    public int countDistinctIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] visited = new int[m][n];
        Set<List<List<Integer>>> set = new HashSet<>();

        int[][] directions = {{0,1},{1,0},{0,-1},{-1,0}};

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(grid[i][j]=='L' && visited[i][j]!=1){
                    List<List<Integer>> list = new ArrayList<>();
                    dfs(grid,visited,i,j,list,directions,i,j);
                    set.add(list);
                }

            }
        }
        return set.size();
    }
    public static void dfs(char[][]grid,int [][] visited,int i,int j,List<List<Integer>> list,int[][] directions,int bi,int bj){
        visited[i][j]=1;
        list.add(Arrays.asList(bi-i,bj-j));

        for(int[] dir: directions){
            int x = i + dir[0];
            int y = j + dir[1];

            if(x>=0 && x< grid.length && y>=0 && y<grid[0].length){
                if(grid[x][y]=='L' && visited[x][y]!=1){
                    dfs(grid,visited,x,y,list,directions,bi,bj);
                }
            }
        }
    }
}