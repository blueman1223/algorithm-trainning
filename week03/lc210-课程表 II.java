// https://leetcode-cn.com/problems/course-schedule-ii/
class Solution {
    private int numCourses = 0;
    private Map<Integer, List<Integer>> edges = new HashMap<>();
    private Map<Integer, Integer> degreeMap = new HashMap<>();
    private List<Integer> learned = new ArrayList<>();

    // 解题思路：
    // 将课程和依赖关系转化为有向图，课程的学习顺序即为此有向图的拓扑排序

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        this.numCourses = numCourses;
        initEdges(prerequisites);
        topSort();
        //
        // System.out.println("edges=======");
        // for (Integer node : edges.keySet()) {
        //     System.out.println(node + " :" + edges.get(node));
        // }
        // System.out.println(learned);
        if (learned.size() != numCourses) {
            // System.out.println(this.numCourses + " " + learned.size());
            return new int[0];
        } else {
            return learned.stream().mapToInt(Integer::valueOf).toArray();
        }
        
    }

    private void topSort() {
        Queue<Integer> queue = new LinkedList<>();

        for (Integer node : degreeMap.keySet()) {
            if (degreeMap.get(node) == 0) {
                queue.offer(node);
            }
        }

        while (!queue.isEmpty()) {
            Integer node = queue.poll();
            learned.add(node);
            if (!edges.get(node).isEmpty()) {
                for (Integer nextNode : edges.get(node)) {
                    int nextDegree = degreeMap.get(nextNode) - 1;   // 出队列的表示已学过，它的下一节点度-1
                    if (nextDegree == 0) {
                        queue.offer(nextNode);
                    }
                   degreeMap.put(nextNode, nextDegree);   
                }
            }
        }
    }

    private void initEdges(int[][] prerequisites) {
        for (int i = 0; i < numCourses; i++) {
            // 初始化所有点
            edges.computeIfAbsent(i, k->new ArrayList<>());
            degreeMap.computeIfAbsent(i, k->0);
        }
        // System.out.println(numCourses);
        for (int[] prerequisite : prerequisites) {
            // System.out.println(prerequisite[0] + " " + prerequisite[1]);
            edges.get(prerequisite[1]).add(prerequisite[0]);
            degreeMap.put(prerequisite[0], degreeMap.get(prerequisite[0]) + 1);
        }
    }
}