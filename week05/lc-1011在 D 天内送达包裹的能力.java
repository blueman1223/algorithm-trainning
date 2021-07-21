//https://leetcode-cn.com/problems/capacity-to-ship-packages-within-d-days/

class Solution {
    // 暴利解法 weights.length个数 分 days组 C(weights.length-1, days-1) 计算max组和
    // 找最小 max组和 


    // 思路：考虑使用二分查找答案来解
    // 最低运载能力在解空间中处于一个临界点，所有大于最低运载能力的元素都为解空间中的元素
    private int[] weights;
    private int days;
    public int shipWithinDays(int[] weights, int days) {
        this.weights = weights;
        this.days = days;
        int maxWeight = Integer.MIN_VALUE;
        int totalWeight = 0;
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] > maxWeight) {
                maxWeight = weights[i];
            }
            totalWeight += weights[i];
        }
        // 答案下界为最大的单个重量，上界为所有负载总和(一次运完)
        return binSearchAnswer(maxWeight, totalWeight);
    }

    private int binSearchAnswer(int left, int right) {
        int mid = 0;
        while(left < right) {
            mid = (left + right) / 2;   // 每次计算mid时向下取整
            // System.out.println("=======left:" + left + " right:" + right + " mid:" + mid);
            if (isValid(mid)) {
                // 如果判定有效 那么临界点在mid左侧，包含mid
                right = mid;
            } else {
                // 判定无效 临界点在右侧 不包含mid
                left = mid + 1;
            }
        }
        // 取较小位
        return left;
    }

    private boolean isValid(int capacity) {
        int usedDays = 1;
        int nowLoads = 0;
        for (int i = 0; i < weights.length; i++) {
            if ((nowLoads + weights[i]) > capacity) {
                // 当前运载量大于运载能力时 需要换到下一批运送
                usedDays++;
                nowLoads = weights[i];
            } else {
                nowLoads += weights[i];
            }
            // System.out.println("nowloads:" + nowLoads + " usedDays:" + usedDays + " capacity:" + capacity);
        }
        // System.out.println(usedDays <= days);
        return usedDays <= days;
    }
}
