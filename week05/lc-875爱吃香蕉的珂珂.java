// https://leetcode-cn.com/problems/koko-eating-bananas/

class Solution {
    //思路：
    //由于每次只能吃一堆，所以当速度确定时，总耗时与吃的顺序无关
    //解空间范围 香蕉总数/h <= K <= 保证每小时都能吃掉一堆(最大堆)
    //暴力：遍历解空间，找到正好满足 耗时 <= h 的 K
    //二分查找
    private int[] piles;
    private int h;

    public int minEatingSpeed(int[] piles, int h) {
        this.piles = piles;
        this.h = h;

        int total = 0;
        int maxPile = 0;
        for (int i = 0; i < piles.length; i++) {
            total += piles[i];
            if (maxPile < piles[i]) {
                maxPile = piles[i];
            }
        }
        // int left = (int)Math.ceil((double) total / h);   // 向上取整
        int left = (total + h - 1) / h;
        int right = maxPile;
        if (left > right) return -1;
        if (left == right) return left;
        while (left < right) {
            int mid = (left + right) / 2;
            if (canFinish(mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private boolean canFinish(int k) {
        int useHours = 0;
        
        // 香蕉个数/速度 向上取整
        for (int pile : piles) {
            useHours += (pile + k - 1) / k;
        }
        // for (int i = 0; i < piles.length;) {
        //     remain -= k;
        //     useHours++;
        //     if (remain <= 0) {
        //         i++;
        //         if (i < piles.length) remain = piles[i];
        //     }
        //     // System.out.println("remain:" + remain + " use:" + useHours);
        // }
        // 这样算会超时。。。。
        // System.out.println("k:" + k + " use:" + useHours);
        return useHours <= h;    
    }
}