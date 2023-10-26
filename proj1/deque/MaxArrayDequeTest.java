package deque;

import org.junit.Test;
import java.util.Comparator;
import static org.junit.Assert.assertEquals;

public class MaxArrayDequeTest {
    @Test
    public void maxWithoutComparatorTest() {
        MaxArrayDeque<Integer> maxArrDeque = new MaxArrayDeque<Integer>(Integer::compareTo);
        for (int i = 0; i < 10; i++) {
            maxArrDeque.addLast(i);
        }
        assertEquals((Integer) 9 , maxArrDeque.max());
    }

    @Test
    public void maxWithComparatorTest() {
        MaxArrayDeque<String> maxArrDeque = new MaxArrayDeque<String>(String::compareTo);
        maxArrDeque.addLast("test with string");
        maxArrDeque.addLast("Test with string!!!!!");
        assertEquals("test with string", maxArrDeque.max());
        assertEquals("Test with string!!!!!", maxArrDeque.max(new StringLengthComparator()));
    }

    // Custom comparator
    private static class StringLengthComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return s1.length() - s2.length();
        }
    }

}
