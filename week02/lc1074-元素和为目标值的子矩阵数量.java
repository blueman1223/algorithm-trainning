// https://leetcode-cn.com/problems/number-of-submatrices-that-sum-to-target/

class Solution {
    int[][] matrixSum;

    public int numSubmatrixSumTarget(int[][] matrix, int target) {
        int ans = 0;
        fillMartixSum(matrix);
        // 哈希查找
        for (int top = 1; top < matrixSum.length; top++) {  //固定上边界
            for (int btm = top; btm < matrixSum.length; btm++) {  // 固定下边界
                Map<Integer, Integer> matrixSumCount = new HashMap<>();
                matrixSumCount.put(0, 1);   // (s[btm][r] - s[top][r]) == target
                for (int r = 1; r < matrixSum[0].length; r++) {  
                    // (s[btm][r] - s[top][r]) - (s[btm][l] - s[top][l]) = target ->
                    // find(s[btm][l] - s[top][l] = target - (s[btm][r] - s[top][r]))
                    int curSum = matrixSum[btm][r] - matrixSum[top - 1][r];
                    ans += matrixSumCount.getOrDefault(curSum - target, 0);
                    matrixSumCount.put(curSum, matrixSumCount.getOrDefault(curSum, 0) + 1);
                }
            }
        }
    
        // 枚举搜索
        // for (int x1 = 1; x1 < matrixSum.length; x1++) {
        //     for (int y1 = 1; y1 < matrixSum[0].length; y1++) {
        //         for (int x2 = x1; x2 < matrixSum.length; x2++) {
        //             for (int y2 = y1; y2 < matrixSum[0].length; y2++) {
        //                 // System.out.println("" + x1 + " " + y1 + " " + x2 + " " + y2);
                        
        //                 if (target == getSum(x1, y1, x2, y2)) {
        //                     ans++;
        //                 }
        //             }
        //         }
        //     }
        // }
        return ans;
    }

    private void fillMartixSum(int[][] matrix) {
        matrixSum = new int[matrix.length + 1][matrix[0].length + 1];
        for (int i = 1; i < matrixSum.length; i++) {
            for (int j = 1; j < matrixSum[0].length; j++) {
                matrixSum[i][j] = matrixSum[i - 1][j] + matrixSum[i][j - 1] - matrixSum[i - 1][j - 1] + matrix[i - 1][j - 1];
            }
        }
        // for (int[] xSum : matrixSum) {
        //     for (int x : xSum) {
        //         System.out.print(x + " ");
        //     }
        //     System.out.println();
        // }
    }

    private int getSum(int x1, int y1, int x2, int y2) {
        // 0 < x1 < x2、0 < y1 < y2
        // System.out.println("" + matrixSum[x2][y2] + " - " + matrixSum[x1][y2] + " - " + matrixSum[x2][y1] + " + " + matrixSum[x1][y1]);
        return matrixSum[x2][y2] - matrixSum[x1 - 1][y2] - matrixSum[x2][y1 - 1] + matrixSum[x1 - 1][y1 - 1];
    }

    
}