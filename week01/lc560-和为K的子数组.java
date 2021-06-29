class Solution {
    public int subarraySum(int[] nums, int k) {
        int ans = 0;
        Map<Integer, Integer> prevSumCount = new HashMap<>();
        int[] sumArr = getSumArray(nums);
        // 枚举搜索
        // for(int i = 1; i < sumArr.length; i++) {
        //     for (int j = i; j < sumArr.length; j++) {
        //         if (k == sumArr[j] - sumArr[i - 1]) {
        //             ans++;
        //         }
        //     }
        // }

        // 哈希查找 s[i] - s[j] = k ->  find (s[j] = s[i] - k)
        prevSumCount.put(0, 1);     // s[i] == k
        for (int i = 1; i < sumArr.length; i++) {
            ans += prevSumCount.getOrDefault(sumArr[i] - k, 0); // find s[j]
            prevSumCount.put(sumArr[i], prevSumCount.getOrDefault(sumArr[i], 0) + 1); // put s[j]
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