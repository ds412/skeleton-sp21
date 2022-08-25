package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE

    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing correct = new AListNoResizing();
        BuggyAList buggy = new BuggyAList();

        for(int i = 2; i <= 8; i *= 2) {                //add 2, 4, 8 to both ALists;
            correct.addLast(i);
            buggy.addLast(i);
        }

        assertEquals(correct.size(), buggy.size());

        for(int i = 0; i < 3; i++){                     //remove 3 items from both ALists and check if they are correct
            assertEquals(correct.removeLast(), buggy.removeLast());
        }
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> buggy = new BuggyAList<>();

        int N = 50000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                correct.addLast(randVal);
                buggy.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                assertEquals(correct.size(), buggy.size());
            }
            else if (operationNumber == 2 && correct.size() > 0) {
                // removeLast
                assertEquals(correct.removeLast(), buggy.removeLast());
            }
            else if (operationNumber == 3 && correct.size() > 0) {
                // getLast
                assertEquals(correct.getLast(), buggy.getLast());
            }
        }
    }
}
