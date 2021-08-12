// https://leetcode-cn.com/problems/number-of-islands/

class Solution {
    //并查集思路，遍历所有点，在遍历的过程中将周围的陆地加入并查集
    //统计并查集的个数即为岛屿的数量
    public int numIslands(char[][] grid) {
        this.parent = new int[grid.length][grid[0].length][2];
        int m = grid.length;
        int n = grid[0].length;
        int count = 0;
        //初始化并查集
        for (int i = 0; i < this.parent.length; i++) {
            for (int j = 0; j < this.parent[0].length; j++) {
                this.parent[i][j] = new int[]{i, j};
            }
        }
        // 遍历地图将陆地加入并查集
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '0') continue;
                int[] curPos = new int[]{i, j};
                for (int d = 0; d < 2; d++) {
                    int nx = i + dx[d];
                    int ny = j + dy[d];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] == '1') {
                        //将周围的陆地加入并查集
                        union(curPos, new int[]{nx, ny});
                        // System.out.println("x: " + i + " y:" + j + " nx:" + nx + " ny:" + ny);
                    }
                }
            }
        }
        // 计算并查集数量
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '0') continue;
                int[] curPos = new int[]{i, j};
                if (Arrays.equals(find(curPos), curPos)) count++;
            }
        }

        // //检查并查集
        // for(int i = 0; i < m; i++) {
        //     for (int j = 0; j < n; j++) {
        //         System.out.print(parent[i][j][0] + "," + parent[i][j][1] + " ");
        //     }
        //     System.out.println();
        // }

        return count;
    }
    //因为遍历顺序是从左到右，从上到下，所以不需要左上方向
    private int[] dx = new int[]{-1, 0};
    private int[] dy = new int[]{0, -1};

    private int[][][] parent;

    private int[] find(int[] x) {
        if (Arrays.equals(parent[x[0]][x[1]], x)) return x;
        return parent[x[0]][x[1]] = find(parent[x[0]][x[1]]);
    }

    private void union(int[] x, int[] y) {
        int[] px = find(x);
        int[] py = find(y);
        if (!Arrays.equals(px, py)) {
            parent[px[0]][px[1]] = py;
        }
    }
}

