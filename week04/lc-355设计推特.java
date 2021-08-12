// https://leetcode-cn.com/problems/design-twitter/

class Twitter {
    // 用链表存储推文 并用头插法维持最新推文
    public class Tweet {
        int id;
        int index;
        Tweet next;
    }

    // 用户与关注用户映射
    private Map<Integer, Set<Integer>> userMap;
    // 用户与推文映射
    private Map<Integer, Tweet> tweetMap;

    private int tweetCount;     // 记录推文发送顺序 可以用atomicInteger做并发改进 或者用System.currentMilles

    /** Initialize your data structure here. */
    public Twitter() {
        this.userMap = new HashMap<>();
        this.tweetMap = new HashMap<>();
        this.tweetCount = 0;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet();
        tweet.id = tweetId;
        tweet.index = tweetCount++;
        Tweet hair = tweetMap.computeIfAbsent(userId, (key) -> new Tweet());
        tweet.next = hair.next;
        hair.next = tweet;
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        // 利用堆进行推文排序
        Set<Integer> followeeSet = userMap.computeIfAbsent(userId, (key) -> new HashSet());
        // System.out.println(userId + " followee set =====");
        // for (Integer followee : followeeSet) {
        //     System.out.print(followee + "  ");
        // }
        // System.out.println();
        PriorityQueue<Tweet> maxHeap = new PriorityQueue<>(followeeSet.size() + 1, (t1, t2) -> t2.index - t1.index);
        for (Integer followee : followeeSet) {
            Tweet hair = tweetMap.computeIfAbsent(followee, (key) -> new Tweet());
            if (hair.next != null) {
                maxHeap.offer(hair.next);
            }
        }
        // 当前用户的推文也需要读取
        Tweet hair = tweetMap.computeIfAbsent(userId, (key) -> new Tweet());
        if (hair.next != null) {
            maxHeap.offer(hair.next);
        }
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (maxHeap.isEmpty()) {
                break;
            } else {
                Tweet tweet = maxHeap.poll();
                ans.add(tweet.id);
                if (tweet.next != null) {
                    maxHeap.offer(tweet.next);
                }
            }
        }
        return ans;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        Set<Integer> followeeSet = userMap.computeIfAbsent(followerId, (key) -> new HashSet());
        followeeSet.add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        Set<Integer> followeeSet = userMap.computeIfAbsent(followerId, (key) -> new HashSet());
        followeeSet.remove(followeeId);
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */