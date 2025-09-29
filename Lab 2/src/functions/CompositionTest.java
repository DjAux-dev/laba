package functions;

public class CompositionTest {
    private static final double EPS = 1e-12;

    public static void main(String[] args) {
        MathFunction sqr = new SqrFunction();
        MathFunction sin = new SinFunction();
        MathFunction zero = new ZeroFunction();
        MathFunction one = new UnitFunction();
        MathFunction c = new ConstantFunction(3.0);

        // f(g(h(x))) = sin(sqr(x))
        MathFunction f1 = sin.andThen(sqr); // sqr(sin(x))
        assertAlmostEquals(Math.pow(Math.sin(0.7), 2), f1.apply(0.7));

        MathFunction f2 = sqr.andThen(sin); // sin(x^2)
        assertAlmostEquals(Math.sin(0.7 * 0.7), f2.apply(0.7));

        // chaining multiple
        MathFunction chain = sqr.andThen(sin).andThen(sqr); // (sin(x^2))^2
        double x = -1.3;
        assertAlmostEquals(Math.pow(Math.sin(x * x), 2), chain.apply(x));

        // zero and unit effects
        assertAlmostEquals(0.0, zero.andThen(sin).apply(123)); // sin(0) = 0
        assertAlmostEquals(1.0, one.apply(-999));

        // constant then any
        assertAlmostEquals(Math.sin(3.0), c.andThen(sin).apply(1000));
        assertAlmostEquals(9.0, c.andThen(sqr).apply(5));

        // any then constant
        assertAlmostEquals(3.0, sin.andThen(c).apply(0.123));

        System.out.println("Composition tests passed");
    }

    private static void assertAlmostEquals(double expected, double actual) {
        if (Double.isNaN(expected) && Double.isNaN(actual)) return;
        if (Double.isInfinite(expected) && Double.isInfinite(actual)) return;
        if (Math.abs(expected - actual) > EPS) {
            throw new AssertionError("Expected: " + expected + ", actual: " + actual);
        }
    }
}


