package functions;

public class SinFunctionTest {
    private static final double EPS = 1e-12;

    public static void main(String[] args) {
        SinFunction f = new SinFunction();

        assertAlmostEquals(0.0, f.apply(0.0));
        assertAlmostEquals(0.0, f.apply(Math.PI));
        assertAlmostEquals(1.0, f.apply(Math.PI / 2));
        assertAlmostEquals(-1.0, f.apply(-Math.PI / 2));

        double nan = Double.NaN;
        if (!Double.isNaN(f.apply(nan))) {
            throw new AssertionError("NaN input should produce NaN output");
        }

        double inf = Double.POSITIVE_INFINITY;
        if (!Double.isNaN(f.apply(inf))) {
            throw new AssertionError("Infinity input should produce NaN for sin");
        }

        System.out.println("SinFunction tests passed");
    }

    private static void assertAlmostEquals(double expected, double actual) {
        if (Double.isNaN(expected) && Double.isNaN(actual)) return;
        if (Double.isInfinite(expected) && Double.isInfinite(actual)) return;
        if (Math.abs(expected - actual) > EPS) {
            throw new AssertionError("Expected: " + expected + ", actual: " + actual);
        }
    }
}


