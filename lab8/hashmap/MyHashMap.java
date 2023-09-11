package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private final double maxLoadFactor;
    private int size;

    private static final int DEFAULT_SIZE = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    /** Constructors */
    public MyHashMap() {
        this(DEFAULT_SIZE, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad)
    {
        buckets = createTable(initialSize);
        maxLoadFactor = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            // use factory method
            table[i] = createBucket();
        }
        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    @Override
    public void clear() {
        buckets = createTable(buckets.length);
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) throw new IllegalArgumentException("Null Argument");
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        if (node == null) return null;
        return node.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        Node node = getNode(key);
        if (node != null) {
            // override value?
            node.value = value;
            return;
        }

        // node == null
        node = createNode(key, value);
        buckets[getHash(key)].add(node);
        size += 1;

        // *check if reach max load factor
        if (isReachMaxLoad()) {
            resize(buckets.length * 2);
        }
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<>();
        // map61b extends from Iterable ?
        for (K key : this) {
            set.add(key);
        }
        return set;
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    private int getHash(K key) {
        int hashcode = key.hashCode();
        return Math.floorMod(hashcode, buckets.length);
    }

    private Node getNode(K key) {
        for (Node node : buckets[getHash(key)]) {
            if (node.key.equals(key)) return node;
        }
        return null;
    }

    private boolean isReachMaxLoad() {
        return (size*1.00 / buckets.length) > maxLoadFactor;
    }

    private void resize(int size) {
        Collection<Node>[] temp = createTable(size);
        for (int i = 0; i < buckets.length; i++) {
            for (Node node : buckets[i]) {
                temp[i].add(node);
            }
        }
        this.buckets = temp;
    }

    // custom iterator from exuanbo/cs61b-sp21
    private class MyHashMapIterator implements Iterator<K> {
        private final Iterator<Node> nodeIterator = new MyHashMapNodeIterator();

        public boolean hasNext() {
            return nodeIterator.hasNext();
        }

        public K next() {
            return nodeIterator.next().key;
        }
    }
    private class MyHashMapNodeIterator implements Iterator<Node> {
        // *stream func
        private final Iterator<Collection<Node>> bucketsIterator = Arrays.stream(buckets).iterator();
        private Iterator<Node> curBucketIterator;
        private int restNodes = size;

        public boolean hasNext() {
            return restNodes > 0;
        }

        public Node next() {
            if (curBucketIterator == null || !curBucketIterator.hasNext()) {
                Collection<Node> curBucket = bucketsIterator.next();
                while (curBucket.isEmpty()) {
                    curBucket = bucketsIterator.next();
                }
                curBucketIterator = curBucket.iterator();
            }
            restNodes -= 1;
            return curBucketIterator.next();
        }
    }
}
