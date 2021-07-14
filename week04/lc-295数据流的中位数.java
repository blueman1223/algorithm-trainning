// https://leetcode-cn.com/problems/find-median-from-data-stream/

class MedianFinder {
    // 中位数将原数据集分为两个部分
    // 中位数左边的所有数小于中位数
    // 中数右边的所有数大于中位数
    // 分别用最大堆和最小堆来维护中位数两边的数据集 则中位数将出现在堆顶

    private PriorityQueue<Integer> maxHeap;
    private PriorityQueue<Integer> minHeap;

    /** initialize your data structure here. */
    public MedianFinder() {
        this.minHeap = new PriorityQueue<>();   // java默认priorityQueue为最小堆
        this.maxHeap = new PriorityQueue<>(11, (v1, v2) -> v2 - v1); //11 为默认initCapacity, 重载Comparator实现最大堆
    }
    
    public void addNum(int num) {
        maxHeap.offer(num);
        // 平衡两个堆的容量，奇数时左边比右边多一个
        if ((maxHeap.size() - minHeap.size()) > 1) {
            int transEle = maxHeap.poll();
            minHeap.offer(transEle);
        }

        // 需要维护最小堆内的所以元素大于或等于最大堆的堆顶
        if (!minHeap.isEmpty() && maxHeap.peek() > minHeap.peek()) {
            int tmp = minHeap.poll();
            maxHeap.offer(tmp);
            tmp = maxHeap.poll();
            minHeap.offer(tmp);
        }
    
    }
    
    public double findMedian() {
        if (maxHeap.size() > minHeap.size()) {
            return maxHeap.peek();
        } else {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */