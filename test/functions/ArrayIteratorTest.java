package functions;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIteratorTest {
    public static void run() {
        testIteratorWhileLoop();
        testIteratorForEachLoop();
        testIteratorExhaustionThrows();
    }

    private static void testIteratorWhileLoop() {
        double[] xs = {0.0, 1.0, 2.0};
        double[] ys = {0.0, 1.0, 4.0};
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(xs, ys);

        Iterator<Point> it = f.iterator();
        int idx = 0;
        while (it.hasNext()) {
            Point p = it.next();
            TestUtils.assertEquals(xs[idx], p.x);
            TestUtils.assertEquals(ys[idx], p.y);
            idx++;
        }
        TestUtils.assertEquals(3.0, idx);
    }

    private static void testIteratorForEachLoop() {
        double[] xs = {0.0, 1.0, 2.0, 3.0};
        double[] ys = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(xs, ys);

        int idx = 0;
        for (Point p : f) {
            TestUtils.assertEquals(xs[idx], p.x);
            TestUtils.assertEquals(ys[idx], p.y);
            idx++;
        }
        TestUtils.assertEquals(4.0, idx);
    }

    private static void testIteratorExhaustionThrows() {
        double[] xs = {0.0};
        double[] ys = {0.0};
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(xs, ys);
        Iterator<Point> it = f.iterator();

        // First element ok
        Point p = it.next();
        TestUtils.assertEquals(0.0, p.x);
        TestUtils.assertEquals(0.0, p.y);

        // Next should throw
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("NoSuchElementException expected after iterator exhaustion");
        }
    }
}


