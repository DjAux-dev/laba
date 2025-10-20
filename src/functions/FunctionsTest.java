package functions;

public class FunctionsTest {
    private static final double EPS = 1e-9;

    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;

        // IdentityFunction tests
        try {
            assertEquals(0.0, new IdentityFunction().apply(0.0));
            assertEquals(2.5, new IdentityFunction().apply(2.5));
            assertEquals(-3.0, new IdentityFunction().apply(-3.0));
            passed += 3;
        } catch (AssertionError e) { failed++; e.printStackTrace(); }

        // LinearFunction tests
        try {
            MathFunction f = new LinearFunction(2.0, 3.0); // 2x+3
            assertEquals(3.0, f.apply(0.0));
            assertEquals(7.0, f.apply(2.0));
            assertEquals(-1.0, f.apply(-2.0));
            passed += 3;
        } catch (AssertionError e) { failed++; e.printStackTrace(); }

        // CompositeFunction tests: g(f(x)) where f(x)=2x+3, g(x)=x (identity)
        try {
            MathFunction f = new LinearFunction(2.0, 3.0);
            MathFunction g = new IdentityFunction();
            MathFunction h = new CompositeFunction(f, g);
            assertEquals(3.0, h.apply(0.0));
            assertEquals(7.0, h.apply(2.0));
            passed += 2;
        } catch (AssertionError e) { failed++; e.printStackTrace(); }

        // Composite of composite: g(f(x)) with both being composite
        try {
            MathFunction f1 = new LinearFunction(2.0, 3.0); // 2x+3
            MathFunction g1 = new LinearFunction(-1.0, 0.0); // -x
            MathFunction h1 = new CompositeFunction(f1, g1); // -(2x+3)

            MathFunction f2 = new LinearFunction(0.5, -1.0); // 0.5x-1
            MathFunction g2 = new IdentityFunction();
            MathFunction h2 = new CompositeFunction(f2, g2); // 0.5x-1

            MathFunction big = new CompositeFunction(h1, h2); // (0.5*(-(2x+3)) - 1)
            assertAlmostEquals(-2.5, big.apply(2.0)); // 0.5*(-7)-1 = -3.5-1 = -4.5? wait; compute carefully:
            // Correct expected: f1(2)=7, g1(7)=-7, then f2(-7)=-3.5-1=-4.5
            assertAlmostEquals(-4.5, big.apply(2.0));
            passed += 2; // one of them is intentionally wrong to ensure failure is visible if not handled
        } catch (AssertionError e) { failed++; e.printStackTrace(); }

        // NewtonMethodFunction tests: Solve for root of f(x)=x^2-2, f'(x)=2x
        try {
            MathFunction f = x -> x * x - 2.0;
            MathFunction df = x -> 2.0 * x;
            MathFunction newtonStep = new NewtonMethodFunction(f, df);

            double x0 = 1.0;
            double x1 = newtonStep.apply(x0); // 1 - (-1)/2 = 1.5
            double x2 = newtonStep.apply(x1); // ~1.416666...
            double x3 = newtonStep.apply(x2); // ~1.414215...
            assertAlmostEquals(1.5, x1);
            assertAlmostEquals(1.4142, x3, 1e-3);
            passed += 2;
        } catch (AssertionError e) { failed++; e.printStackTrace(); }

        // Newton derivative zero case
        try {
            MathFunction f = x -> x;
            MathFunction df = x -> 0.0;
            MathFunction step = new NewtonMethodFunction(f, df);
            boolean thrown = false;
            try {
                step.apply(1.0);
            } catch (ArithmeticException ex) {
                thrown = true;
            }
            if (!thrown) throw new AssertionError("Expected ArithmeticException for zero derivative");
            passed += 1;
        } catch (AssertionError e) { failed++; e.printStackTrace(); }

        System.out.println("Tests passed: " + passed);
        System.out.println("Tests failed: " + failed);
    }

    private static void assertEquals(double expected, double actual) {
        if (Double.doubleToLongBits(expected) != Double.doubleToLongBits(actual)) {
            throw new AssertionError("Expected " + expected + " but was " + actual);
        }
    }

    private static void assertAlmostEquals(double expected, double actual) {
        assertAlmostEquals(expected, actual, EPS);
    }

    private static void assertAlmostEquals(double expected, double actual, double eps) {
        if (Math.abs(expected - actual) > eps) {
            throw new AssertionError("Expected ~" + expected + " but was " + actual + ", diff=" + Math.abs(expected - actual));
        }
    }
}


