package functions;

public final class TestUtils {
    public static final double EPS = 1e-9;

    public static void assertEquals(double expected, double actual) {
        if (Double.doubleToLongBits(expected) != Double.doubleToLongBits(actual)) {
            throw new AssertionError("Expected " + expected + " but was " + actual);
        }
    }

    public static void assertAlmostEquals(double expected, double actual) {
        assertAlmostEquals(expected, actual, EPS);
    }

    public static void assertAlmostEquals(double expected, double actual, double eps) {
        if (Math.abs(expected - actual) > eps) {
            throw new AssertionError("Expected ~" + expected + " but was " + actual + ", diff=" + Math.abs(expected - actual));
        }
    }
}


