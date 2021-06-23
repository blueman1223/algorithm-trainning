// https://leetcode-cn.com/problems/subarray-sum-equals-k/
class Solution {
    public int subarraySum(int[] nums, int k) {
        int ans = 0;
        int[] sumArr = getSumArray(nums);
        for(int i = 1; i < sumArr.length; i++) {
            for (int j = i; j < sumArr.length; j++) {
                if (k == sumArr[j] - sumArr[i - 1]) {
                    ans++;
                }
            }
        }
        return ans;
    }

    // 求前缀和
    public int[] getSumArray(int[] nums) {
        // 首位补0
        int[] newNums = new int[nums.length+1];
        System.arraycopy(nums, 0, newNums, 1, nums.length);
        int[] sumArr = new int[nums.length + 1];
        for (int i = 1; i < newNums.length; i++) {
            sumArr[i] += newNums[i] + sumArr[i - 1];
        }
        return sumArr;
    }
}