// https://leetcode-cn.com/problems/degree-of-an-array/

class Solution {
    public int findShortestSubArray(int[] nums) {
        int maxDegree = 0;
        int minStep = Integer.MAX_VALUE;
        Map<Integer, Info> infoMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Info info = infoMap.getOrDefault(nums[i], new Info());
            info.degree++;
            if (-1 == info.firstIndex) {
                info.firstIndex = i;
            }
            info.step = i - info.firstIndex + 1;
            
            infoMap.put(nums[i], info);
            if (info.degree >= maxDegree) {
                maxDegree = info.degree;
            }
        }

        for (Info info : infoMap.values()) {
            if (info.degree == maxDegree) {
                if (minStep > info.step) {
                    minStep = info.step;
                }
            }
        }
        
        return minStep;
    }

    class Info {
        int degree;
        int step;
        int firstIndex = -1;
    }
}