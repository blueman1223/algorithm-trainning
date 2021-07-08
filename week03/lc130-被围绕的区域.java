// https://leetcode-cn.com/problems/surrounded-regions/

/**
 * 解题思路：
   将题目转变为 找到所有与边界相邻的O,将其改成#
   将所有的O改成X, #改成O
   查找与边界相邻的O:
   从边界上的O开始 搜索所有相邻的O
 */

class Solution {
    public void solve(char[][] board) {
        init(board);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 搜索边界上的'O'
                if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                    if ('O' == board[i][j] && !visited[i][j]) {
                        dfs(board, i, j);
                    }
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if ('O' == board[i][j]) {
                    board[i][j] = 'X';
                }
                if ('#' == board[i][j]) {
                    board[i][j] =  'O';
                }
            }
        }
    }

    private void dfs(char[][] grid, int x, int y) {
        visited[x][y] = true;
        grid[x][y] = '#';   // 将'O'涂成'#'
        //遍历一个点的四个方向
        for (int i = 0; i < 4; i++) {
            int nextX = x + dx[i];
            int nextY = y + dy[i];
            if (nextX < 0 || nextX > m - 1 || nextY < 0 || nextY > n - 1) {
                continue;
            }
            if ('O' == grid[nextX][nextY] && !visited[nextX][nextY]) {
                dfs(grid, nextX, nextY);
            }
        }
    }

    private void init(char[][] board) {
        this.m = board.length;
        this.n = board[0].length;

        this.visited = new boolean[m][n];
    }

    private boolean[][] visited;
    private int m;
    private int n;
    // 方向数组 代表(x, y)的移动方向 东->南->西->北
    private int[] dx = new int[]{-1, 0, 1, 0}; 
    private int[] dy = new int[]{0, 1, 0, -1};
}