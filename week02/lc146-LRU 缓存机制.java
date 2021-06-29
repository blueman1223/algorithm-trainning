// https://leetcode-cn.com/problems/lru-cache/
class LRUCache {
    private int capacity;
    private HashMap<Integer, Node> map;
    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = new Node();
        this.tail = new Node();
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }
    
    public int get(int key) {
        Node target = map.get(key);
        if (target == null) {
            return -1;
        }
        removeFromList(target);
        insertIntoListHead(target);
        return target.value;
    }
    
    public void put(int key, int value) {
        if (capacity == 0) {return;}

        Node node = new Node(key, value);

        Node dupKey = map.remove(key);
        if (dupKey != null) {
            removeFromList(dupKey);
        }

        if (map.size() + 1 > capacity) {
            map.remove(tail.prev.key);
            removeFromList(tail.prev);
        }
        map.put(key, node);
        insertIntoListHead(node);
        // System.out.println("put " + key + " " + value);
        // System.out.println("map data start ===");
        // for (Node n : map.values()) {
        //     System.out.println(n.key + " " + n.value);
        // }
        // System.out.println("mpa data end ===");

        // Node n = head;
        // System.out.println("list data start ===");
        // while (n != tail) {
        //     System.out.println(n.key + " " + n.value);
        //     n = n.next;
        // }
        // System.out.println("list data end ===");

    }

    private void removeFromList(Node node) {
        // 前后有保护节点 不需要判断前后为空
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
    }

    private void insertIntoListHead(Node node) {
        Node tmp = head.next;
        head.next = node;
        node.prev = head;
        node.next = tmp;
        tmp.prev = node;
    }


    static class Node {
        int key;
        int value;
        Node prev = null;
        Node next = null;
        Node() {}
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */