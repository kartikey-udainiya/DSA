class Solution {
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        char[] wordArr = word.toCharArray();
        int[][] direction = {{0,1},{1,0},{0,-1},{-1,0}};

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(board[i][j]==wordArr[0]) {
                    if(backtrack2(word, direction, board, i, j, 0)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean backtrack2(String word,int[][] direction,char[][] board,int i , int j,int index){
        if(board[i][j]!=word.charAt(index))return false;
        if(index==word.length()-1)return true;

        char temp = board[i][j];
        board[i][j]='#';

        for(int[] dir : direction){
            int x = i + dir[0];
            int y = j + dir[1];

            if(x>=0 && x<board.length && y>=0 && y<board[0].length){
                if(backtrack2(word,direction,board,x,y,index+1)){
                    board[i][j]=temp;
                    return true;
                };
            }
        }
        board[i][j] = temp;
        return false;
    }
}
