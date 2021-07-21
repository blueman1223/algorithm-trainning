// https://leetcode-cn.com/problems/online-election/

class TopVotedCandidate {
    // 思路：维护(时刻，得票最多的人)[]
    // 
    // 查找 t时刻得票最多的人 -> 查找t时刻的前驱节点(时刻，得票最多的人)  ===>二分查找
    // 暴力解法 遍历以上集合 查找前驱节点

    private int[] times;
    private Map<Integer, Integer> timeTopCanidateMap = new HashMap<>();

    public TopVotedCandidate(int[] persons, int[] times) {
        this.times = times;
        int maxVotedCount = 0;
        int maxVotedPerson = -1;
        Map<Integer, Integer> personVotedMap = new HashMap<>();
        for (int i = 0; i < times.length; i++) {
            int nowVoted = personVotedMap.getOrDefault(persons[i], 0) + 1;
            if (nowVoted >= maxVotedCount) {    // 这里做错了。。。。少个等号TAT =>在平局的情况下，最近获得投票的候选人将会获胜。
                maxVotedCount = nowVoted;
                maxVotedPerson = persons[i];
            }
            personVotedMap.put(persons[i], nowVoted);
            timeTopCanidateMap.put(times[i], maxVotedPerson);
        }     
        // for (Integer key : times) {
        //     System.out.println("===time:" + key + " person:" + timeTopCanidateMap.get(key));
        // }
    }
    
    public int q(int t) {
        // 二分查找t的前驱时刻
        int left = 0;
        int right = times.length - 1;
        while(left < right) {
            int mid = (left + right + 1) / 2;
            if (times[mid] <= t) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        // 较大者为答案
        return timeTopCanidateMap.get(times[right]);
    }
}

/**
 * Your TopVotedCandidate object will be instantiated and called as such:
 * TopVotedCandidate obj = new TopVotedCandidate(persons, times);
 * int param_1 = obj.q(t);
 */

 