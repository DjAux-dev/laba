package functions;

public class CompositionWithTabulatedTest {
    public static void run() {
        TabulatedFunction tf = new LinkedListTabulatedFunction(
            new double[]{0.0, 1.0, 2.0}, new double[]{0.0, 1.0, 4.0}
        );
        MathFunction linear = new LinearFunction(2.0, -1.0);
        MathFunction composed = new CompositeFunction(tf, linear);
        TestUtils.assertAlmostEquals(4.0, composed.apply(1.5));
        MathFunction composed2 = new CompositeFunction(linear, tf);
        TestUtils.assertAlmostEquals(7.0, composed2.apply(2.0));
    }
}


