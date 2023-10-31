package deque;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.Iterator;

public class IteratorTest {
    @Test
    public void llDIteratorTest() {
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        for (int i = 0; i < 5; i++) {
            lld.addLast(i);
        }
        StringBuilder str = new StringBuilder();
        Iterator<Integer> iter = lld.iterator();
        while (iter.hasNext()) {
            str.append(iter.next().toString());
            str.append(" ");
        }
        assertEquals("0 1 2 3 4 ", str.toString());

    }
}
