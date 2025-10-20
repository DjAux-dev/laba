package functions;

public class AndThenMethodTest {
    public static void run() {
        MathFunction id = new IdentityFunction();
        MathFunction lin = new LinearFunction(2.0, 3.0); // 2x+3
        MathFunction sqr = new SqrFunction(); // x^2

        // Identity chaining.
        TestUtils.assertEquals(5.0, id.andThen(id).apply(5.0));

        // Simple two-step: (2x+3) then square => (2x+3)^2
        MathFunction f = lin.andThen(sqr);
        TestUtils.assertEquals(9.0, f.apply(0.0));
        TestUtils.assertEquals(25.0, f.apply(1.0));

        // Three-step: id -> lin -> id
        MathFunction g = id.andThen(lin).andThen(id);
        TestUtils.assertEquals(3.0, g.apply(0.0));
        TestUtils.assertEquals(7.0, g.apply(2.0));

        // Longer chain: ((x^2) -> lin) -> sqr -> id
        MathFunction h = sqr.andThen(lin).andThen(sqr).andThen(id);
        TestUtils.assertEquals(9.0, h.apply(0.0));
        TestUtils.assertEquals(49.0, h.apply(2.0)); // (((2^2)=4)-> (2*4+3=11) -> 121 -> 121)

        // Compose with tabulated function
        double[] xs = {0.0, 1.0, 2.0};
        double[] ys = {0.0, 1.0, 4.0};
        TabulatedFunction tf = new LinkedListTabulatedFunction(xs, ys);
        MathFunction c1 = tf.andThen(lin); // first interpolate, then linear
        TestUtils.assertAlmostEquals(4.0, c1.apply(1.5));
        MathFunction c2 = lin.andThen(tf); // linear then interpolate
        TestUtils.assertAlmostEquals(7.0, c2.apply(2.0));
    }
}


