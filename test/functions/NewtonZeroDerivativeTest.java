package functions;

public class NewtonZeroDerivativeTest {
    public static void run() {
        MathFunction f = x -> x;
        MathFunction df = x -> 0.0;
        MathFunction step = new NewtonMethodFunction(f, df);
        boolean thrown = false;
        try { step.apply(1.0); } catch (ArithmeticException ex) { thrown = true; }
        if (!thrown) throw new AssertionError("Expected ArithmeticException for zero derivative");
    }
}


