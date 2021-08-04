// https://leetcode-cn.com/problems/redundant-connection/

class Solution {
    //依次将边的两个端点加入到并查集
    //当一条新的边，它的两个端点都在并查集中时，那么这个边就是最后那条构成环的边
    public int[] findRedundantConnection(int[][] edges) {
        this.parent = new int[edges.length+1];
        for (int i = 1; i <= edges.length; i++) {
            //初始化并查集数组，依照题意节点数=边数(1~n)
            parent[i] = i;
        }

        for (int[] edge : edges) {
            int x = edge[0];
            int y = edge[1];
            int pX = find(x);
            int pY = find(y);
            if (pX == pY) return edge;
            union(x, y);
        }
        return new int[2];
    }
    private int[] parent;

    private int find(int x) {
        if (x == parent[x]) return x;
        return parent[x] = find(parent[x]);
    }

    private void union(int x, int y) {
        int parentX = parent[x];
        int parentY = parent[y];
        if (parentX != parentY) parent[parentY] = parentX;
    }
}

