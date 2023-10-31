package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private final Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public T max() {
        return max(comparator);
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T max = get(0);
        for (int i = 1; i < size(); i++) {
            T item = get(i);
            if (c.compare(item, max) > 0) {
                max = item;
            }
        }
        return max;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MaxArrayDeque)) {
            return false;
        }
        if (((MaxArrayDeque<?>) o).max() != max()) {
            return false;
        }
        return super.equals(o);
    }
}
