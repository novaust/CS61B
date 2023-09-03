package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node node;
    private int size = 0;

    public void clear() {
        node = null;
        size = 0;
    }

    public boolean containsKey(K key) {
        return containsKey(node, key);
    }

    private boolean containsKey(Node node, K key) {
        if (node == null) return false;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) return containsKey(node.left, key);
        else if (cmp > 0) return containsKey(node.right, key);
        return true;
    }

    public V get(K key) {
        return get(node, key);
    }

    private V get(Node x, K key) {
        if (key == null) throw new IllegalArgumentException("call get with null key");
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.value;
    }

    public int size() {
        return size;
    }

    public void put(K key, V value) {
        node = put(node, key, value);   // recursion, root = root;
        size += 1;
    }

    private Node put(Node node, K key, V value) {
        if (node == null) return new Node(key, value);  // insert operation
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = put(node.left, key, value);
        else if (cmp > 0) node.right = put(node.right, key, value);
        else node.value = value;
        return node;
    }

    // do not implement
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<>();
        addKey(node, set);
        return set;
    }

    private void addKey(Node node, Set<K> set) {
        if (node == null) return;
        set.add(node.key);
        addKey(node.left, set);
        addKey(node.right, set);
    }

    public V remove(K key) {
        if (containsKey(key)) {
            V target = get(key);
            node = remove(node, key);
            size -= 1;
            return target;
        }
        return null;
    }

    public V remove(K key, V value) {
        if (containsKey(key)) {
            V target = get(key);
            if (target.equals(value)) {
                node = remove(node, key);
                size -= 1;
                return target;
            }
        }
        return null;
    }

    private Node remove(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = remove(node.left, key);
        else if (cmp > 0) node.right = remove(node.right, key);
        else {
            // TODO
        }
        return node;
    }

    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public void printInOrder() {
        printInOrder(node);
    }

    private void printInOrder(Node node) {
        if (node == null) return;
        printInOrder(node.left);
        System.out.println(node.key.toString() + "->" + node.value.toString());
        printInOrder(node.right);
    }

    private class Node {
        public final K key;
        public V value;
        public Node left;
        public Node right;

        public Node(K k, V v) {
            key = k;
            value = v;
        }
    }
}
