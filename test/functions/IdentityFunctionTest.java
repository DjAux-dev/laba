package functions;

public class IdentityFunctionTest {
    public static void run() {
        TestUtils.assertEquals(0.0, new IdentityFunction().apply(0.0));
        TestUtils.assertEquals(2.5, new IdentityFunction().apply(2.5));
        TestUtils.assertEquals(-3.0, new IdentityFunction().apply(-3.0));
    }
}


