// https://leetcode-cn.com/problems/valid-anagram/

class Solution {
    public boolean isAnagram(String s, String t) {
        Map<Character, Integer> sCount = statistics(s.toCharArray());
        Map<Character, Integer> tCount = statistics(t.toCharArray());
        return sCount.equals(tCount);
    }

    private Map<Character, Integer> statistics(char[] chArr) {
        Map<Character, Integer> res = new HashMap<>();
        for (char ch : chArr) {
            int count = res.getOrDefault(ch, 0) + 1;
            res.put(ch, count);
        }
        return res;
    }
}
