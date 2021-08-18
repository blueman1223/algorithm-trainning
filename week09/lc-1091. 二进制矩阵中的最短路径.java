// https://leetcode-cn.com/problems/shortest-path-in-binary-matrix/

class Solution {
    public int shortestPathBinaryMatrix(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        if (grid[0][0] == 1 || grid[m-1][n-1] == 1) return -1;
        if (n == 1 && grid[0][0] == 0) return 1;
        // 搜索，从0，0开始广度优先搜索每个可达点，第一次遍历到(n-1, n-1)即为最短路径   -- 会超时
        // 双端bfs
        Queue<Pair> upperQ = new LinkedList<>();
        Queue<Pair> bottomQ = new LinkedList<>();



        boolean[][] upperV = new boolean[m][n];
        boolean[][] bottomV = new boolean[m][n];

        upperQ.offer(new Pair(0, 0));
        bottomQ.offer(new Pair(m-1, n-1));
        upperV[0][0] = true;
        bottomV[m-1][n-1] = true;
        int count = 2;
        while(!upperQ.isEmpty() || !bottomQ.isEmpty()) {
            boolean flag = false;
            flag = expand(upperQ, grid, upperV, bottomV);
            if (flag) return count;
            count++;
            flag = expand(bottomQ, grid, bottomV, upperV);
            if (flag) return count;
            count++;
        }
        return -1;
    }

    private boolean expand(Queue<Pair> queue, int[][] grid, boolean[][] visited, boolean[][] otherVisited) {
        if (queue.isEmpty()) return false;
        int m = grid.length;
        int n = grid[0].length;
        int size = queue.size();
        for (int r = 0; r < size; r++) {
            Pair cur = queue.poll();
            //  System.out.println("x==>" + cur.x + " y==>" + cur.y);

            for (int i = 0; i < 8; i++)  {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if (nx >= n || nx < 0 || ny >= n || ny < 0 || visited[nx][ny] || grid[nx][ny] == 1) continue;
                if (otherVisited[nx][ny]) return true;
                queue.offer(new Pair(nx, ny));
                visited[nx][ny] = true; // 需要在入队列时设置visited 否则会将重复元素放进队列
            }
        }
        return false;
    }

    class Pair {
        int x;
        int y;
        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private int[] dx = {1, 1, 0, -1, -1, -1, 0, 1};
    private int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};
}

