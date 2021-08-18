// https://leetcode-cn.com/problems/sliding-window-maximum/

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0) return new int[0];
        List<Integer> ans = new ArrayList<>();
        // 平衡树key为值，value为index 每次获取lastEntry 检查value是否在窗口中
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();    
        for (int i = 0; i < nums.length; i++) {
            treeMap.put(nums[i], i);
            if (i >= k-1) {
                // i为窗口右端
                ans.add(findWindowMax(treeMap, i, k));
            }
        }
        return ans.stream().mapToInt(Integer::valueOf).toArray();
    }

    private int findWindowMax(TreeMap<Integer, Integer> treeMap, int wr, int k) {
        while (true) {
            int max = treeMap.lastKey();
            int index = treeMap.get(max);
            if (wr - index < k) return max;
            treeMap.remove(max);    // 懒惰删除
        }
        
    }
}

