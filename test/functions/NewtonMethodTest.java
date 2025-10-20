package functions;

public class NewtonMethodTest {
    public static void run() {
        MathFunction f = x -> x * x - 2.0;
        MathFunction df = x -> 2.0 * x;
        MathFunction newtonStep = new NewtonMethodFunction(f, df);
        double x0 = 1.0;
        double x1 = newtonStep.apply(x0);
        double x2 = newtonStep.apply(x1);
        double x3 = newtonStep.apply(x2);
        TestUtils.assertAlmostEquals(1.5, x1);
        TestUtils.assertAlmostEquals(1.4142, x3, 1e-3);
    }
}


