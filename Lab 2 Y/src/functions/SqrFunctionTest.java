package functions;

public class SqrFunctionTest {
    private static final double EPS = 1e-12;

    public static void main(String[] args) {
        SqrFunction f = new SqrFunction();

        assertAlmostEquals(4.0, f.apply(-2.0));
        assertAlmostEquals(0.0, f.apply(0.0));
        assertAlmostEquals(9.0, f.apply(3.0));

        double large = 1e154;
        assertAlmostEquals(1e308, f.apply(large));

        double nan = Double.NaN;
        if (!Double.isNaN(f.apply(nan))) {
            throw new AssertionError("NaN input should produce NaN output");
        }

        double inf = Double.POSITIVE_INFINITY;
        if (!Double.isInfinite(f.apply(inf))) {
            throw new AssertionError("Infinity input should produce Infinity output");
        }

        System.out.println("SqrFunction tests passed");
    }

    private static void assertAlmostEquals(double expected, double actual) {
        if (Double.isNaN(expected) && Double.isNaN(actual)) return;
        if (Double.isInfinite(expected) && Double.isInfinite(actual)) return;
        if (Math.abs(expected - actual) > EPS) {
            throw new AssertionError("Expected: " + expected + ", actual: " + actual);
        }
    }
}


