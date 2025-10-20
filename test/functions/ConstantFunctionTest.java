package functions;

public class ConstantFunctionTest {
    public static void run() {
        MathFunction c1 = new ConstantFunction(42.0);
        TestUtils.assertEquals(42.0, c1.apply(Double.NEGATIVE_INFINITY));
        TestUtils.assertEquals(42.0, c1.apply(-1000.0));
        TestUtils.assertEquals(42.0, c1.apply(0.0));
        TestUtils.assertEquals(42.0, c1.apply(123.456));
        TestUtils.assertEquals(42.0, c1.apply(Double.POSITIVE_INFINITY));

        ConstantFunction c2 = new ConstantFunction(-3.14);
        TestUtils.assertEquals(-3.14, c2.getValue());
        TestUtils.assertEquals(-3.14, c2.apply(999.0));
    }
}


