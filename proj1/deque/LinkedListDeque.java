package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private Node head;      // head node
    private Node tail;      // tail node

    public LinkedListDeque() {
        head = new Node();
        tail = new Node();
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;   // ?
    }

    public void addFirst(T item) {
        Node node = new Node();
        node.value = item;
        if (head.next == null)
            tail = node;
        node.prev = head;
        node.next = head.next;
        head.next = node;
        size++;
    }

    public void addLast(T item) {
        Node node = new Node();
        node.value = item;
        if (tail.prev == null)
            head = node;
        node.next = tail;
        node.prev = tail.prev;
        tail.prev = node;
        size++;
    }

    public T removeFirst() {
        Node node = head.next;
        if (node == null)
            return null;
        head.next = node.next;
        node.next.prev = head;
        size--;
        return node.value;
    }

    public T removeLast() {
        Node node = tail.prev;
        if (node == null)
            return null;
        tail.prev = node.prev;
        node.prev.next = tail;
        size--;
        return node.value;
    }

    // ?
    public T get(int index) {
        if (index > size - 1)
            return null;
        Node node = head;
        for (int i = 0; i <= index; i++)
            node = node.next;
        return node.value;
    }

    public void printDeque() {
        Node node = head.next;
        while (node.next != null) {
            // TODO: remove last space
            System.out.println(node.value + " ");
            node = node.next;
        }
        System.out.println("\n");
    }

    // TODO
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        throw new IllegalArgumentException();
    }

    private class Node {
        public T value;
        public Node next = null;
        public Node prev = null;

        //public T getValue() { return value; }
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node p;

        LinkedListDequeIterator() {
            p = head;
        }

        public boolean hasNext() {
            return p.next != null;
        }

        public T next() {
            T item = p.value;
            p = p.next;
            return item;
        }
    }
}


