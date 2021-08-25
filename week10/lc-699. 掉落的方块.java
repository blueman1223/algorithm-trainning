// https://leetcode-cn.com/problems/falling-squares/

class Solution {
    public List<Integer> fallingSquares(int[][] positions) {
        int n = 2 * positions.length;
        int[] compArr = new int[n];

        for (int i = 0; i < positions.length; i++) {
            compArr[i*2] = positions[i][0];
            compArr[i*2+1] = positions[i][0] + positions[i][1] - 1;
        }
        Arrays.sort(compArr);
        Map<Integer, Integer> p2Index = new HashMap<>();
        for (int i = 0; i < compArr.length; i++) {
            p2Index.put(compArr[i], i);
        }

        SegmentTree t = new SegmentTree(positions);
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions) {
            int l = p2Index.get(position[0]);
            int r = p2Index.get(position[0] + position[1] - 1);
            int curH = t.Query(l, r);
            t.Change(l, r, position[1] + curH);
            ans.add(t.Query(0, n));
        }
        return ans;
    }
}

class SegmentTree{
    private Node[] tree;
    private int n;  // 区间大小
    

    public SegmentTree(int[][] positions) {
        this.n = positions.length * 2;  // 区间内至多有positions.length * 2个点
        this.tree = new Node[n * 4];    // 将区间坐标压缩到0...n
        build(1, 0, n - 1);
        // for (int i = 0; i < tree.length; i++) {
        //     System.out.println("i:" + i + " " + String.valueOf(tree[i] != null));
        // }
    }

    public void Change(int l, int r, int delta) {
        change(1, l, r, delta);
    }

    public int Query(int l, int r) {
       return query(1, l, r);
    }

    private void build(int cur, int l, int r) {
        tree[cur] = new Node();
        tree[cur].l = l;
        tree[cur].r = r;
        tree[cur].max = 0;
        tree[cur].mark = 0;
        if (l == r) {
            return;
        }
        int mid = (l + r) / 2;
        build(cur * 2, l, mid); // 构建左子树
        build(cur * 2 + 1, mid + 1, r); // 构建右子树
        // 回溯时不需要初始化max
    }

    private int query(int cur, int l, int r) {
        if(l <= tree[cur].l && r >= tree[cur].r) {
            // System.out.println("query" + cur + ":" + tree[cur]);
            return tree[cur].max;
        }
        if (tree[cur].l != tree[cur].r) spread(cur);
        int mid = (tree[cur].l + tree[cur].r) / 2;
        int ans = 0;
        if (l <= mid) ans = Math.max(ans, query(cur * 2, l, r));
        if (r > mid) ans = Math.max(ans, query(cur * 2 + 1, l, r));
        // System.out.println("query" + cur + ":" + tree[cur]);
        return ans;
    }

    private void change(int cur, int l, int r, int delta) {
        if (l <= tree[cur].l && r >= tree[cur].r) {
            //  区间全包含
            tree[cur].max = delta;
            tree[cur].mark = delta;
            System.out.println(cur + ":" + tree[cur]);
            return;
        }
        int mid = (tree[cur].l + tree[cur].r) / 2;
        if (tree[cur].l != tree[cur].r) spread(cur);
        if(l <= mid) change(cur * 2, l, r, delta);
        if(r > mid) change(cur * 2 + 1, l, r, delta);
        
        tree[cur].max = Math.max(tree[cur * 2].max, tree[cur * 2 + 1].max);
        System.out.println(cur + ":" + tree[cur]);

    }

    private void spread(int cur) {

        // 更新左右子树
        if (tree[cur].mark != 0) {
            tree[cur * 2].max = tree[cur].mark;
            tree[cur * 2].mark = tree[cur].mark;
            tree[cur * 2 + 1].max = tree[cur].mark;
            tree[cur * 2 + 1].mark = tree[cur].mark;
            tree[cur].mark = 0;
        }
    }

    class Node {
        int l;  // 区间左端点
        int r;  // 区间右端点
        int max;    // 区间最高高度
        int mark;   // 懒惰修改标记

        public String toString() {
            return "[l:" + l + " " + "r:" + r + " max:" + max + " mark:" + mark + "]";
        }
    }
}

