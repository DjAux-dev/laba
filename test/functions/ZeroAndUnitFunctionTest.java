package functions;

public class ZeroAndUnitFunctionTest {
    public static void run() {
        MathFunction z = new ZeroFunction();
        TestUtils.assertEquals(0.0, z.apply(-123.0));
        TestUtils.assertEquals(0.0, z.apply(0.0));
        TestUtils.assertEquals(0.0, z.apply(999.999));

        MathFunction u = new UnitFunction();
        TestUtils.assertEquals(1.0, u.apply(-123.0));
        TestUtils.assertEquals(1.0, u.apply(0.0));
        TestUtils.assertEquals(1.0, u.apply(999.999));
    }
}


