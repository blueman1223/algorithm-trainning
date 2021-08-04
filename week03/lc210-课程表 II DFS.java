// https://leetcode-cn.com/problems/course-schedule-ii/
class Solution {
    // DFS解法
    //思路：维护两个访问表，visited记录所有已访问的节点，learned记录当前路径已经经过的节点
    //visited防止重复搜索，learned检测是否有环
    private Map<Integer, List<Integer>> edgesMap = new HashMap<>();
    private Map<Integer, Boolean> visited = new HashMap<>();
    private List<Integer> ans = new ArrayList<>();
    private Map<Integer, Integer> degree = new HashMap<>();
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        //初始化课程学习图
        addEdges(prerequisites);
        for (int i = 0; i < numCourses; i++ ) {
            if (degree.getOrDefault(i, 0) == 0 && !visited.getOrDefault(i, false)) {
                Set<Integer> learned = new HashSet<>();
                boolean hasLoop = dfsFindLoop(i, learned);
                if (hasLoop) return new int[]{};
            }
        }
        if (ans.size() != numCourses) return new int[]{};
        return ans.stream().mapToInt(Integer::intValue).toArray();
    }

    private void addEdges(int[][] prerequisites) {
        for (int[] prerequisite : prerequisites) {
            List<Integer> edges = edgesMap.computeIfAbsent(prerequisite[1], k->new ArrayList<>());
            edges.add(prerequisite[0]);
            int nowDegree = degree.getOrDefault(prerequisite[0], 0) + 1;
            degree.put(prerequisite[0], nowDegree);
        }
    }

    private boolean dfsFindLoop(Integer root, Set<Integer> learned) {
        // System.out.print(" " + root);
        ans.add(root);  //添加答案
        visited.put(root, true);
        List<Integer> edges = edgesMap.get(root);
        if (edges != null) {
            for (Integer edge : edges) {
                if (learned.contains(edge)) {   //发现有环
                    return true;
                }
                int nowDegree = degree.get(edge) - 1;
                degree.put(edge, nowDegree);
                if (!visited.getOrDefault(edge, false) && nowDegree == 0) {
                    learned.add(edge);  //记录当前经过的路径
                    boolean hasLoop = dfsFindLoop(edge, learned);
                    if (hasLoop) return true;   //发现有环直接结束递归
                    learned.remove(edge);   //恢复现场
                }
            }
        }
        return false;
    }
}

