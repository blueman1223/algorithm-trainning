// https://leetcode-cn.com/problems/triangle/

class Solution {
    //定义 n为层数 f(n, i)为从(0, 0)到(n, i)的最小路径和
    //状态转移: f(n, i) = min(f(n-1, i) + tri[n][i], f(n-1, i-1) + tri[n][i])
    //初始状态: f(0, 0) = tri[0][0]

    //题解思路：定义状态f(i, j) 为 从 (i, j)到底层的最小路径和
    //状态转移方程：f(i, j) = min(f(i+1, j), f(i+1, j+1)) + val(i, j)
    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle.size() == 1) {
            return triangle.get(0).get(0);
        }
        int[] dp = new int[triangle.size()];
        int ans = 10001;   //记录答案
        for (int i = 0; i< dp.length; i++) {
            dp[i] = 10001;  // 初始化不合法值，按照题目条件最大值为10000，则10001可表示无穷大
        }
        dp[0] = triangle.get(0).get(0); //初始状态
        //优化:由于f(n)状态只与上一层相关，所以可以把dp压缩到一维，去掉n,直接在原数组上更新
        for (int n = 1; n < triangle.size(); n++) {
            ans = 10001;
            for (int i = triangle.get(n).size() - 1; i > 0; i--) {
                int val = triangle.get(n).get(i);
                dp[i] = Math.min(dp[i] + val, dp[i - 1] + val);
                ans = Math.min(ans, dp[i]);
            }
            dp[0] = dp[0] + triangle.get(n).get(0);
            ans = Math.min(ans, dp[0]);
            // for (int i = 0; i < dp.length; i++) {
            //     System.out.print(" " + dp[i]);
            // }
            // System.out.println();
        }
        
        return ans;
    }
}

