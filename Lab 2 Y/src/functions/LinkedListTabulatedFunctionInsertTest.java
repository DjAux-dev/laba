package functions;

public class LinkedListTabulatedFunctionInsertTest {
    private static final double EPS = 1e-9;

    public static void main(String[] args) {
        testInsertIntoEmpty();
        testReplaceExisting();
        testInsertAtHead();
        testInsertInMiddle();
        testInsertAtTail();
        System.out.println("LinkedListTabulatedFunction insert tests passed");
    }

    private static void testInsertIntoEmpty() {
        LinkedListTabulatedFunction f = new LinkedListTabulatedFunction();
        f.insert(1.0, 2.0);
        assertEquals(1, f.getCount());
        assertAlmostEquals(1.0, f.getX(0));
        assertAlmostEquals(2.0, f.getY(0));
    }

    private static void testReplaceExisting() {
        LinkedListTabulatedFunction f = new LinkedListTabulatedFunction(new double[]{0.0, 1.0}, new double[]{0.0, 1.0});
        f.insert(1.0, 5.0);
        assertAlmostEquals(5.0, f.getY(1));
        assertEquals(2, f.getCount());
    }

    private static void testInsertAtHead() {
        LinkedListTabulatedFunction f = new LinkedListTabulatedFunction(new double[]{1.0, 2.0}, new double[]{10.0, 20.0});
        f.insert(0.5, 5.0);
        assertEquals(3, f.getCount());
        assertAlmostEquals(0.5, f.getX(0));
        assertAlmostEquals(5.0, f.getY(0));
        assertAlmostEquals(1.0, f.getX(1));
    }

    private static void testInsertInMiddle() {
        LinkedListTabulatedFunction f = new LinkedListTabulatedFunction(new double[]{0.0, 2.0}, new double[]{0.0, 4.0});
        f.insert(1.0, 3.0);
        assertEquals(3, f.getCount());
        assertAlmostEquals(1.0, f.getX(1));
        assertAlmostEquals(3.0, f.getY(1));
    }

    private static void testInsertAtTail() {
        LinkedListTabulatedFunction f = new LinkedListTabulatedFunction(new double[]{0.0, 1.0}, new double[]{0.0, 1.0});
        f.insert(2.0, 4.0);
        assertEquals(3, f.getCount());
        assertAlmostEquals(2.0, f.getX(2));
        assertAlmostEquals(4.0, f.getY(2));
    }

    private static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("Expected: " + expected + ", actual: " + actual);
        }
    }

    private static void assertAlmostEquals(double expected, double actual) {
        if (Double.isNaN(expected) && Double.isNaN(actual)) return;
        if (Double.isInfinite(expected) && Double.isInfinite(actual)) return;
        if (Math.abs(expected - actual) > EPS) {
            throw new AssertionError("Expected: " + expected + ", actual: " + actual);
        }
    }
}


