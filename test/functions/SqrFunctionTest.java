package functions;

public class SqrFunctionTest {
    public static void run() {
        MathFunction f = new SqrFunction();
        TestUtils.assertEquals(0.0, f.apply(0.0));
        TestUtils.assertEquals(1.0, f.apply(1.0));
        TestUtils.assertEquals(4.0, f.apply(2.0));
        TestUtils.assertEquals(9.0, f.apply(-3.0));
        TestUtils.assertAlmostEquals(6.25, f.apply(2.5));
    }
}


