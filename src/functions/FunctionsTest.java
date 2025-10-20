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
            // f1(2)=7, g1(7)=-7, then f2(-7)=-3.5-1=-4.5
            assertAlmostEquals(-4.5, big.apply(2.0));
            passed += 1;
        } catch (AssertionError e) { failed++; e.printStackTrace(); }

        // LinkedListTabulatedFunction: constructor from arrays and basic ops
        try {
            double[] xs = {0.0, 1.0, 2.0, 4.0};
            double[] ys = {0.0, 1.0, 4.0, 16.0};
            TabulatedFunction tf = new LinkedListTabulatedFunction(xs, ys);
            assertEquals(4, tf.getCount());
            assertEquals(0.0, tf.leftBound());
            assertEquals(4.0, tf.rightBound());
            assertEquals(2.0, tf.getX(2));
            assertEquals(4.0, tf.getY(2));
            tf.setY(2, 5.0);
            assertEquals(5.0, tf.getY(2));
            assertEquals(0, tf.indexOfX(0.0));
            assertEquals(2, tf.indexOfX(2.0));
            assertEquals(-1, tf.indexOfX(3.0));
            assertEquals(2, tf.indexOfY(5.0));
            assertEquals(-1, tf.indexOfY(2.0));
            // interpolation inside [1,2] with points (1,1) and (2,5)
            assertAlmostEquals(3.0, tf.apply(1.5));
            // left extrapolation using first segment slope: between (0,0) and (1,1)
            assertAlmostEquals(-0.5, tf.apply(-0.5));
            // right extrapolation using last segment slope: between (2,5) and (4,16)
            // slope = (16-5)/(4-2) = 5.5; at x=5 -> 5 + 5.5*(3) = 21.5
            assertAlmostEquals(21.5, tf.apply(5.0));
            passed += 14;
        } catch (AssertionError e) { failed++; e.printStackTrace(); }

        // LinkedListTabulatedFunction: constructor from source function with discretization
        try {
            MathFunction src = x -> x * x; // x^2
            TabulatedFunction tf = new LinkedListTabulatedFunction(src, 2.0, 0.0, 3); // reversed bounds -> 0,1,2
            assertEquals(3, tf.getCount());
            assertEquals(0.0, tf.leftBound());
            assertEquals(2.0, tf.rightBound());
            assertEquals(0.0, tf.getX(0));
            assertEquals(1.0, tf.getX(1));
            assertEquals(2.0, tf.getX(2));
            assertEquals(0.0, tf.getY(0));
            assertEquals(1.0, tf.getY(1));
            assertEquals(4.0, tf.getY(2));
            // interpolate at 0.5 => 0.25
            assertAlmostEquals(0.25, tf.apply(0.5));
            passed += 10;
        } catch (AssertionError e) { failed++; e.printStackTrace(); }

        // Composition: tabulated with other functions
        try {
            TabulatedFunction tf = new LinkedListTabulatedFunction(
                new double[]{0.0, 1.0, 2.0}, new double[]{0.0, 1.0, 4.0}
            ); // approximates x^2
            MathFunction linear = new LinearFunction(2.0, -1.0); // 2x-1
            MathFunction composed = new CompositeFunction(tf, linear); // (2 * tf(x) - 1)
            // At x=1.5, tf interpolates between (1,1) and (2,4) -> 2.5, then 2*2.5 -1 = 4
            assertAlmostEquals(4.0, composed.apply(1.5));
            // Compose in reverse order: linear first, then tf
            MathFunction composed2 = new CompositeFunction(linear, tf);
            // At x=2, linear=3, tf at 3 extrapolates right between (1,1)-(2,4): slope 3, extrap from 2 -> y=4+3*(1)=7
            assertAlmostEquals(7.0, composed2.apply(2.0));
            passed += 2;
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


