package functions;

public class RungeKuttaFunctionTest {
    public static void run() {
        // Test against exact solution for y' = y, y(0)=1 => y(h) = e^h
        MathFunction dy = y -> y;
        double h = 0.1;
        MathFunction rk = new RungeKuttaFunction(dy, h);
        double y0 = 1.0;
        double y1 = rk.apply(y0);
        TestUtils.assertAlmostEquals(Math.exp(h), y1, 1e-12);

        // Do multiple steps and compare with exact y(nh) = e^{nh}
        double y = y0;
        int steps = 10; // integrate to 1.0
        for (int i = 0; i < steps; i++) {
            y = rk.apply(y);
        }
        TestUtils.assertAlmostEquals(Math.exp(steps * h), y, 1e-10);

        // Negative step should integrate backward
        MathFunction rkBack = new RungeKuttaFunction(dy, -h);
        double yBack = rkBack.apply(y1);
        TestUtils.assertAlmostEquals(y0, yBack, 1e-10);

        // Nonlinear test: y' = y^2, y(0)=1 => y(h) = 1/(1-h)
        MathFunction dyy = yv -> yv * yv;
        double h2 = 0.05;
        MathFunction rk2 = new RungeKuttaFunction(dyy, h2);
        double z0 = 1.0;
        double z1 = rk2.apply(z0);
        TestUtils.assertAlmostEquals(1.0 / (1.0 - h2), z1, 1e-10);
    }
}


