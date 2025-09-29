package functions;

public class ConstantFunctionTest {
    private static final double EPS = 1e-12;

    public static void main(String[] args) {
        ConstantFunction c = new ConstantFunction(42.5);
        assertAlmostEquals(42.5, c.apply(-1000));
        assertAlmostEquals(42.5, c.apply(0));
        assertAlmostEquals(42.5, c.apply(123.456));
        assertAlmostEquals(42.5, c.getValue());

        ZeroFunction z = new ZeroFunction();
        assertAlmostEquals(0.0, z.apply(-7));
        assertAlmostEquals(0.0, z.apply(0));
        assertAlmostEquals(0.0, z.apply(9));

        UnitFunction u = new UnitFunction();
        assertAlmostEquals(1.0, u.apply(-7));
        assertAlmostEquals(1.0, u.apply(0));
        assertAlmostEquals(1.0, u.apply(9));

        System.out.println("Constant/Zero/Unit tests passed");
    }

    private static void assertAlmostEquals(double expected, double actual) {
        if (Double.isNaN(expected) && Double.isNaN(actual)) return;
        if (Double.isInfinite(expected) && Double.isInfinite(actual)) return;
        if (Math.abs(expected - actual) > EPS) {
            throw new AssertionError("Expected: " + expected + ", actual: " + actual);
        }
    }
}


