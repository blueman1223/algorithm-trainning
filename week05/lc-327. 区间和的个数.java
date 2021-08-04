// https://leetcode-cn.com/problems/count-of-range-sum/


class Solution {

    // 思路：
    // 计算数据区间和用前缀和公式 S(i, j) = presum[j] - presum[i]
    // 后面计数没想出来，只想到了枚举解法
    // 题解并归排序思路：在并归排序的过程中，左半部分数组的下标一定小于右半部分，且左右半边各自有序
    // 所以在合并之前 记左边数组为n1 右边数组为 n2 固定n2的下标为k 在n1中取l=0,r=n2.length-1
    //l和r分别向中间靠拢，当满足 l<r 且 n2[k] - n1[l] <= upper, n2[k] - n1[r] >= lower时 ans += r-l+1
    //当前统计的答案数量 为下一轮递归的 左边或右边子数组，所以不存在漏计或者重复计数的情况
    private long[] presums;
    private int[] nums;
    private int ansCount = 0;
    private int lower;
    private int upper;
    public int countRangeSum(int[] nums, int lower, int upper) {
        this.nums = nums;
        this.ansCount = 0;
        this.lower = lower;
        this.upper = upper;
        calculatePresums();
        mergeSortCountAns(1, presums.length - 1);
        return ansCount;
    }

    private void calculatePresums() {
        presums = new long[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            presums[i + 1] = presums[i] + nums[i];
        }
    }


    private void mergeSortCountAns(int left, int right) {
        // System.out.println("left:" + left + " right:" + right);
        if (left == right) {
            if (presums[left] >= lower && presums[left] <= upper) {
                ansCount++;
            }
            return;
        }
        int mid = (left + right) / 2;
        mergeSortCountAns(left, mid);
        mergeSortCountAns(mid + 1, right);
        //统计合法答案
        countAns(left, mid, mid + 1, right);

        //合并排序
        long[] sorted = new long[right - left + 1];
        int i = 0;
        int p1 = left, p2 = mid + 1;
        while(p1 <= mid && p2 <= right) {
            if (presums[p1] < presums[p2]) {
                sorted[i++] = presums[p1++];
            } else {
                sorted[i++] = presums[p2++];
            }
        }
        while(p1 <= mid) {
            sorted[i++] = presums[p1++];
        }
        while(p2 <= right) {
            sorted[i++] = presums[p2++];
        }
        //排序结果放回presums
        i = 0;
        while (i < sorted.length) {
            presums[left + i] = sorted[i++];
        }
    }

    private void countAns(int n1Left, int n1Right, int n2Left, int n2Right) {
        int k = n2Left;
        while (k <= n2Right) {
            int l = n1Left, r = n1Right;
            while (l <= n1Right && presums[k] - presums[l] > upper) {
                l++;
            }
            while (r >= l && presums[k] - presums[r] < lower) {
                // System.out.println("count");
                r--;
            }
            ansCount += r - l + 1;
            k++;
        }
    }
}
