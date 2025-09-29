package functions;

import java.util.Arrays;

public class ArrayTabulatedFunctionTest {
    private static final double EPS = 1e-9;

    public static void main(String[] args) {
        testConstructFromArraysCopiesDataAndCounts();
        testConstructFromFunctionDiscretizesInclusive();
        testConstructFromFunctionSinglePointAndEqualBounds();
        testIndexSearchAndBounds();
        testInterpolationAndExtrapolation();
        testMutationsSetY();
        System.out.println("ArrayTabulatedFunction tests passed");
    }

    private static void testConstructFromArraysCopiesDataAndCounts() {
        double[] xs = new double[] {0.0, 1.0, 2.0};
        double[] ys = new double[] {0.0, 1.0, 4.0};
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(xs, ys);
        assertEquals(3, f.getCount());
        assertAlmostEquals(0.0, f.getX(0));
        assertAlmostEquals(2.0, f.getX(2));
        xs[1] = 100.0; ys[1] = 100.0; // should not affect
        assertAlmostEquals(1.0, f.getX(1));
        assertAlmostEquals(1.0, f.getY(1));
    }

    private static void testConstructFromFunctionDiscretizesInclusive() {
        MathFunction sqr = new SqrFunction();
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(sqr, -2.0, 2.0, 5);
        assertEquals(5, f.getCount());
        assertAlmostEquals(-2.0, f.getX(0));
        assertAlmostEquals(2.0, f.getX(4));
        for (int i = 0; i < f.getCount(); i++) {
            assertAlmostEquals(sqr.apply(f.getX(i)), f.getY(i));
        }
    }

    private static void testConstructFromFunctionSinglePointAndEqualBounds() {
        MathFunction sin = new SinFunction();
        ArrayTabulatedFunction f1 = new ArrayTabulatedFunction(sin, 1.5, 1.5, 1);
        assertEquals(1, f1.getCount());
        assertAlmostEquals(1.5, f1.getX(0));
        assertAlmostEquals(Math.sin(1.5), f1.getY(0));

        ArrayTabulatedFunction f2 = new ArrayTabulatedFunction(sin, 1.5, 1.5, 4);
        assertEquals(4, f2.getCount());
        for (int i = 0; i < 4; i++) {
            assertAlmostEquals(1.5, f2.getX(i));
            assertAlmostEquals(Math.sin(1.5), f2.getY(i));
        }
    }

    private static void testIndexSearchAndBounds() {
        double[] xs = new double[] {-3.0, 4.0, 6.0};
        double[] ys = new double[] {9.0, 16.0, 36.0};
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(xs, ys);
        assertAlmostEquals(-3.0, f.leftBound());
        assertAlmostEquals(6.0, f.rightBound());
        assertEquals(1, f.floorIndexOfX(4.5));
        assertEquals(0, f.floorIndexOfX(-5.0));
        assertEquals(3, f.floorIndexOfX(10.0));
        assertEquals(1, f.indexOfX(4.0));
        assertEquals(-1, f.indexOfX(5.0));
    }

    private static void testInterpolationAndExtrapolation() {
        double[] xs = new double[] {0.0, 1.0, 2.0};
        double[] ys = new double[] {0.0, 1.0, 4.0};
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(xs, ys);
        assertAlmostEquals(0.5, f.apply(0.5));
        assertAlmostEquals(2.5, f.apply(1.5));
        assertAlmostEquals(-0.5, f.apply(-0.5));
        assertAlmostEquals(5.5, f.apply(2.5));

        ArrayTabulatedFunction single = new ArrayTabulatedFunction(new double[]{2.0}, new double[]{10.0});
        assertAlmostEquals(10.0, single.apply(-100));
        assertAlmostEquals(10.0, single.apply(100));
        assertAlmostEquals(10.0, single.apply(2.0));
    }

    private static void testMutationsSetY() {
        double[] xs = new double[] {0.0, 1.0, 2.0};
        double[] ys = new double[] {0.0, 1.0, 2.0};
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(xs, ys);
        f.setY(1, 5.0);
        assertAlmostEquals(5.0, f.getY(1));
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


