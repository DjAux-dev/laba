package functions;

public class ArrayTabulatedFunctionRemoveTest {
    private static final double EPS = 1e-9;

    public static void main(String[] args) {
        testRemoveHead();
        testRemoveMiddle();
        testRemoveTail();
        testRemoveNonExisting();
        testRemoveSingleIgnored();
        System.out.println("ArrayTabulatedFunction remove tests passed");
    }

    private static void testRemoveHead() {
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(new double[]{0.0, 1.0, 2.0}, new double[]{0.0, 1.0, 4.0});
        f.remove(0.0);
        assertEquals(2, f.getCount());
        assertAlmostEquals(1.0, f.getX(0));
        assertAlmostEquals(2.0, f.getX(1));
    }

    private static void testRemoveMiddle() {
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(new double[]{0.0, 1.0, 2.0}, new double[]{0.0, 1.0, 4.0});
        f.remove(1.0);
        assertEquals(2, f.getCount());
        assertAlmostEquals(0.0, f.getX(0));
        assertAlmostEquals(2.0, f.getX(1));
    }

    private static void testRemoveTail() {
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(new double[]{0.0, 1.0, 2.0}, new double[]{0.0, 1.0, 4.0});
        f.remove(2.0);
        assertEquals(2, f.getCount());
        assertAlmostEquals(0.0, f.getX(0));
        assertAlmostEquals(1.0, f.getX(1));
    }

    private static void testRemoveNonExisting() {
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(new double[]{0.0, 1.0}, new double[]{0.0, 1.0});
        f.remove(3.0);
        assertEquals(2, f.getCount());
    }

    private static void testRemoveSingleIgnored() {
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(new double[]{0.0}, new double[]{0.0});
        f.remove(0.0);
        assertEquals(1, f.getCount());
        assertAlmostEquals(0.0, f.getX(0));
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


