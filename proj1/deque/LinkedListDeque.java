package deque;

import org.antlr.v4.runtime.misc.NotNull;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private final Node head;      // head node
    private final Node tail;      // tail node

    public LinkedListDeque() {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T item) {
        Node node = new Node();
        node.value = item;
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        size++;
    }

    public void addLast(T item) {
        Node node = new Node();
        node.value = item;
        node.next = tail;
        node.prev = tail.prev;
        tail.prev.next = node;
        tail.prev = node;
        size++;
    }

    public T removeFirst() {
        Node node = head.next;
        if (node == tail) {
            return null;
        }
        head.next = node.next;
        node.next.prev = head;
        size--;
        return node.value;
    }

    public T removeLast() {
        Node node = tail.prev;
        if (node == head) {
            return null;
        }
        tail.prev = node.prev;
        node.prev.next = tail;
        size--;
        return node.value;
    }

    // ?
    public T get(int index) {
        if (index < 0 || index > size - 1) {
            return null;
        }
        Node node = head;
        for (int i = 0; i <= index; i++) {
            node = node.next;
        }
        return node.value;
    }

    public T getRecursive(int index) {
        if (index < 0 || index > size - 1) {
            return null;
        }
        return getRecursion(index, head.next);
    }

    private T getRecursion(int index, Node curNode) {
        if (index == 0) {
            return curNode.value;
        }
        return getRecursion(index-1, curNode.next);
    }

    public void printDeque() {
        String[] items = new String[size];
        Node node = head.next;
        for (int i = 0; i < size; i++) {
            items[i] = node.value.toString();
            node = node.next;
        }
        System.out.println(String.join(" ", items));
        System.out.println("\n");
    }


    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LinkedListDeque))
            return false;

        LinkedListDeque<?> llDeque = (LinkedListDeque<?>) o;    // is good?
        if (llDeque.size() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (llDeque.get(i) != get(i)) {
                return false;
            }
        }
        return true;
    }

    private class Node {
        T value;
        Node next = null;
        Node prev = null;
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node p;

        LinkedListDequeIterator() {
            p = head;
        }

        public boolean hasNext() {
            return p.next != tail;
        }

        public T next() {
            T item = p.value;
            p = p.next;
            return item;
        }
    }
}


