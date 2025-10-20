package functions;

public class LinearFunctionTest {
    public static void run() {
        MathFunction f = new LinearFunction(2.0, 3.0);
        TestUtils.assertEquals(3.0, f.apply(0.0));
        TestUtils.assertEquals(7.0, f.apply(2.0));
        TestUtils.assertEquals(-1.0, f.apply(-2.0));
    }
}


