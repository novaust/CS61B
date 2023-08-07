package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> buggy = new BuggyAList<>();

        correct.addLast(1);
        correct.addLast(3);
        correct.addLast(2);

        buggy.addLast(1);
        buggy.addLast(3);
        buggy.addLast(2);

        assertEquals(correct.size(), buggy.size());
        assertEquals(correct.removeLast(), buggy.removeLast());
        assertEquals(correct.removeLast(), buggy.removeLast());
        assertEquals(correct.removeLast(), buggy.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> buggy = new BuggyAList<>();

        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                buggy.addLast(randVal);
            } else if (operationNumber == 1) {
                assertEquals(L.size(), buggy.size());
            }
            else if (operationNumber == 2) {
                if (L.size() > 0 && buggy.size() > 0) {
                    assertEquals(L.getLast(), buggy.getLast());
                }
            }
            else if (operationNumber == 3) {
                if (L.size() > 0 && buggy.size() > 0) {
                    assertEquals(L.removeLast(), buggy.removeLast());
                }
            }
        }
    }
}
