package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTabulatedFunctionRemoveTest {

    @Test
    void remove_head() {
        double[] xs = {1.0, 2.0, 3.0};
        double[] ys = {10.0, 20.0, 30.0};
        LinkedListTabulatedFunction f = new LinkedListTabulatedFunction(xs, ys);

        f.remove(0);

        assertEquals(2, f.getCount());
        assertEquals(2.0, f.getX(0));
    }

    @Test
    void remove_middle() {
        double[] xs = {1.0, 2.0, 3.0, 4.0};
        double[] ys = {10.0, 20.0, 30.0, 40.0};
        LinkedListTabulatedFunction f = new LinkedListTabulatedFunction(xs, ys);

        f.remove(2);

        assertEquals(3, f.getCount());
        assertEquals(4.0, f.getX(2));
    }

    @Test
    void remove_tail() {
        double[] xs = {1.0, 2.0, 3.0};
        double[] ys = {10.0, 20.0, 30.0};
        LinkedListTabulatedFunction f = new LinkedListTabulatedFunction(xs, ys);

        f.remove(2);

        assertEquals(2, f.getCount());
        assertEquals(2.0, f.rightBound());
    }

    @Test
    void remove_singleElement_resultsInEmpty() {
        double[] xs = {5.0};
        double[] ys = {50.0};
        LinkedListTabulatedFunction f = new LinkedListTabulatedFunction(xs, ys);

        f.remove(0);

        assertEquals(0, f.getCount());
        assertThrows(IllegalStateException.class, f::leftBound);
    }

    @Test
    void remove_invalidIndex_throws() {
        double[] xs = {1.0, 2.0};
        double[] ys = {10.0, 20.0};
        LinkedListTabulatedFunction f = new LinkedListTabulatedFunction(xs, ys);
        assertThrows(IndexOutOfBoundsException.class, () -> f.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> f.remove(2));
    }
}


