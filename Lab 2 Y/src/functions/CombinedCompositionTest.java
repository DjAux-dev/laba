package functions;

public class CombinedCompositionTest {
    private static final double EPS = 1e-9;

    public static void main(String[] args) {
        MathFunction sin = new SinFunction();
        MathFunction sqr = new SqrFunction();
        MathFunction c3 = new ConstantFunction(3.0);

        // Array-based tabulation of sin on [-pi, pi]
        ArrayTabulatedFunction sinArray = new ArrayTabulatedFunction(sin, -Math.PI, Math.PI, 9);
        // List-based tabulation of x^2 on [-2, 2]
        LinkedListTabulatedFunction sqrList = new LinkedListTabulatedFunction(sqr, -2.0, 2.0, 9);

        // Compose: sqr(sin_array(x)) vs direct reference sqr(sin(x)) near grid points
        MathFunction comp1 = sinArray.andThen(sqr);
        for (int i = 0; i < sinArray.getCount(); i++) {
            double x = sinArray.getX(i);
            assertAlmostEquals(Math.pow(Math.sin(x), 2), comp1.apply(x));
        }

        // Compose: sin(sqr_list(x)) at grid points
        MathFunction comp2 = sqrList.andThen(sin);
        for (int i = 0; i < sqrList.getCount(); i++) {
            double x = sqrList.getX(i);
            assertAlmostEquals(Math.sin(x * x), comp2.apply(x));
        }

        // Chain multiple across representations: sqr(sin_array(sqr_list(x))) at a sample
        MathFunction chain = sqrList.andThen(sinArray).andThen(sqr);
        double x = 0.7;
        double expected = Math.pow(sinArray.apply(sqrList.apply(x)), 2);
        assertAlmostEquals(expected, chain.apply(x));

        // Mix with constants: c3.then(sin) should be sin(3) for any x
        MathFunction constThenSin = c3.andThen(sin);
        assertAlmostEquals(Math.sin(3.0), constThenSin.apply(-100));
        assertAlmostEquals(Math.sin(3.0), constThenSin.apply(100));

        // Extrapolation interplay: use array tabulation over [0,1] of sin, then compose with sqr
        ArrayTabulatedFunction shortSin = new ArrayTabulatedFunction(sin, 0.0, 1.0, 3);
        MathFunction extraComp = shortSin.andThen(sqr);
        // Left extrapolation
        double xl = -0.5;
        double linLeft = shortSin.apply(xl);
        assertAlmostEquals(linLeft * linLeft, extraComp.apply(xl));
        // Right extrapolation
        double xr = 1.5;
        double linRight = shortSin.apply(xr);
        assertAlmostEquals(linRight * linRight, extraComp.apply(xr));

        System.out.println("Combined composition tests passed");
    }

    private static void assertAlmostEquals(double expected, double actual) {
        if (Double.isNaN(expected) && Double.isNaN(actual)) return;
        if (Double.isInfinite(expected) && Double.isInfinite(actual)) return;
        if (Math.abs(expected - actual) > EPS) {
            throw new AssertionError("Expected: " + expected + ", actual: " + actual);
        }
    }
}


