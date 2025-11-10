package functions;

import operations.LeftSteppingDifferentialOperator;
import operations.MiddleSteppingDifferentialOperator;
import operations.RightSteppingDifferentialOperator;
import operations.SteppingDifferentialOperator;

public class DifferentialOperatorsTest {
    public static void run() {
        testConstructorValidation();
        testLeftDifferenceOnSqr();
        testRightDifferenceOnSqr();
        testMiddleDifferenceOnSqr();
    }

    private static void testConstructorValidation() {
        assertThrows(() -> new LeftSteppingDifferentialOperator(0.0));
        assertThrows(() -> new RightSteppingDifferentialOperator(-1.0));
        assertThrows(() -> new MiddleSteppingDifferentialOperator(Double.POSITIVE_INFINITY));
        assertThrows(() -> new LeftSteppingDifferentialOperator(Double.NaN));

        // valid
        new LeftSteppingDifferentialOperator(0.1);
        new RightSteppingDifferentialOperator(1.0);
        new MiddleSteppingDifferentialOperator(0.5);
    }

    private static void assertThrows(Runnable r) {
        boolean thrown = false;
        try {
            r.run();
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) throw new AssertionError("IllegalArgumentException expected");
    }

    private static void testLeftDifferenceOnSqr() {
        MathFunction sqr = new SqrFunction();
        double h = 0.1;
        SteppingDifferentialOperator op = new LeftSteppingDifferentialOperator(h);
        MathFunction der = op.derive(sqr);
        // For f(x)=x^2, left difference = 2x - h
        double x1 = 1.0;
        double x2 = 2.5;
        TestUtils.assertAlmostEquals(2*x1 - h, der.apply(x1), 1e-12);
        TestUtils.assertAlmostEquals(2*x2 - h, der.apply(x2), 1e-12);
    }

    private static void testRightDifferenceOnSqr() {
        MathFunction sqr = new SqrFunction();
        double h = 0.2;
        SteppingDifferentialOperator op = new RightSteppingDifferentialOperator(h);
        MathFunction der = op.derive(sqr);
        // For f(x)=x^2, right difference = 2x + h
        double x1 = 1.0;
        double x2 = -3.0;
        TestUtils.assertAlmostEquals(2*x1 + h, der.apply(x1), 1e-12);
        TestUtils.assertAlmostEquals(2*x2 + h, der.apply(x2), 1e-12);
    }

    private static void testMiddleDifferenceOnSqr() {
        MathFunction sqr = new SqrFunction();
        double h = 0.3;
        SteppingDifferentialOperator op = new MiddleSteppingDifferentialOperator(h);
        MathFunction der = op.derive(sqr);
        // For f(x)=x^2, middle difference = 2x exactly
        double x1 = 1.0;
        double x2 = 5.0;
        TestUtils.assertAlmostEquals(2*x1, der.apply(x1), 1e-12);
        TestUtils.assertAlmostEquals(2*x2, der.apply(x2), 1e-12);
    }
}


