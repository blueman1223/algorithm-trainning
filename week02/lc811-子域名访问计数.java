// https://leetcode-cn.com/problems/subdomain-visit-count/

class Solution {
    private Map<String, Integer> domainCountMap = new HashMap<>();

    public List<String> subdomainVisits(String[] cpdomains) {
        for (String cpdomian : cpdomains) {
            String[] countAndDomain = cpdomian.split(" ");
            countDomain(Integer.parseInt(countAndDomain[0]), countAndDomain[1]);
        }
        List<String> ansList = new ArrayList<>();
        for (String domain : domainCountMap.keySet()) {
            String ans = "" + domainCountMap.get(domain) + " " + domain;
            ansList.add(ans);
        }

        return ansList;
    }

    private void countDomain(int count, String domain) {
        String tmp = domain;
        domainCountMap.put(domain, domainCountMap.getOrDefault(domain, 0) + count);
        do {
            tmp = tmp.substring(tmp.indexOf(".") + 1);
            domainCountMap.put(tmp, domainCountMap.getOrDefault(tmp, 0) + count);
        } while((-1 != tmp.indexOf(".")));
    }
}