package deque;

import java.util.Iterator;


public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] items = (T[]) new Object[8];

    public ArrayDeque() {
        nextFirst = 4;
        nextLast = 5;
        size = 0;
    }
    public void addFirst(T item) {
        items[nextFirst] = item;
        nextFirst = (nextFirst + 1 + 8) % 8;
        size++;
        if (size == items.length)
            resize(size * 2);
    }

    public void addLast(T item) {
        items[nextLast] = item;
        nextLast = (nextLast + 1) % 8;
        size++;
        if (size == items.length)
            resize(size * 2);

    }

    public T removeFirst() {
        if (isEmpty())
            return null;
        nextFirst = (nextFirst + 1) % 8;
        T item = items[nextFirst];
        items[nextFirst] = null;
        size--;
        if (size < items.length / 4 && size >= 4)
            resize(items.length / 2);
        return item;
    }

    public T removeLast() {
        if (isEmpty())
            return null;
        nextLast = (nextLast + 1 + 8) % 8;
        T item = items[nextLast];
        items[nextLast] = null;
        size--;
        if (size < items.length / 4 && size >= 4)
            resize(items.length / 2);
        return item;
    }

    private void resize(int newSize) {
        T[] newItems = (T[]) new Object[newSize];   // is good?
        int pos = Math.abs(newSize - size) / 2;
        System.arraycopy(items, 0, newItems, pos, size);
        items = newItems;
        nextFirst = pos - 1;
        nextLast = size + pos;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        String[] str = new String[size];
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null)
                continue;
            str[i] = items[i].toString();
        }
        System.out.println(String.join(" ", str));
        System.out.println("\n");
    }

    public T get(int index) {
        if (index < 0 || index >= items.length)
            return null;
        return items[nextFirst + index + 1];
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArrayDeque))
            return false;
        ArrayDeque<?> ad = (ArrayDeque<?>) o;
        if (ad.size() != size)
            return false;
        for (int i = 0; i < size; i++) {
            if (ad.get(i) != get(i))
                return false;
        }
        return true;
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int index;
        ArrayDequeIterator() {
            index = 0;
        }

        public boolean hasNext() {
            return index < size;
        }

        public T next() {
            T item = get(index);
            index++;
            return item;
        }
    }
}
