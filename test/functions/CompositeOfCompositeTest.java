package functions;

public class CompositeOfCompositeTest {
    public static void run() {
        MathFunction f1 = new LinearFunction(2.0, 3.0);
        MathFunction g1 = new LinearFunction(-1.0, 0.0);
        MathFunction h1 = new CompositeFunction(f1, g1);
        MathFunction f2 = new LinearFunction(0.5, -1.0);
        MathFunction g2 = new IdentityFunction();
        MathFunction h2 = new CompositeFunction(f2, g2);
        MathFunction big = new CompositeFunction(h1, h2);
        TestUtils.assertAlmostEquals(-4.5, big.apply(2.0));
    }
}


