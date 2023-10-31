package deque;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EqualsTest {
    @Test
    public void dequeEqualsTest() {
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            lld.addLast(i);
            ad.addLast(i);
        }
        assertTrue(ad.equals(lld));
        assertTrue((lld.equals(ad)));
    }
}
