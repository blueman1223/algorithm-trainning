// https://leetcode-cn.com/problems/climbing-stairs/

class Solution {
    // 状态转移方程：f(n) = f(n-1) + 1 + f(n-2)
    //初始状态f(0) = 1 f(1) = 1
    public int climbStairs(int n) {
        int[] dp = new int[n+1]; 
        dp[0] = 1;
        dp[1] = 1;
        if (n < 2) return dp[n];
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }   

        return dp[n];
    }
}