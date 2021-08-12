// https://leetcode-cn.com/problems/find-all-anagrams-in-a-string/

class Solution {
    // 常规做法，滑动窗口，用一个指针表示窗口起点，遍历字符串s  判断窗口内字符串是否为异位词
    // 由于java中截取char[]转字符串判断操作不方便，题中表示字符串都是小写字母，所以用质数表示字符串中字符，各位相乘取模判断异位词
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> ans = new ArrayList<>();
        int[] primes = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
        int primeP = 1;
        int pMod = (int)1E9 + 7;
        for (char ch : p.toCharArray()) {
            primeP = primeP * primes[ch - 'a'] % pMod;
        }

        for (int i = 0; i < (s.length() - p.length() + 1); i++) {
            int primeS = 1;
            for (int j = 0; j < p.length(); j++) {
                primeS = primeS * primes[s.charAt(i + j) - 'a'] % pMod;
            }
            // System.out.println("s1:" + primeS + " s2:" + primeP);
            if (primeS == primeP) ans.add(i);
        }
        return ans;

    }
