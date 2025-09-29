package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NewtonMethodFunctionTest {

    @Test
    void oneIteration_movesTowardRoot_forQuadratic() {
        MathFunction f = x -> x * x - 2.0; // roots at ±sqrt(2)
        MathFunction df = x -> 2.0 * x;
        MathFunction newton = new NewtonMethodFunction(f, df);

        double x0 = 1.0;
        double x1 = newton.apply(x0);
        // one iteration from 1 should be 1 - (-1)/2 = 1.5
        assertEquals(1.5, x1, 1e-12);
    }

    @Test
    void oneIteration_exactRootRemainsSame() {
        MathFunction f = x -> x - 5.0; // root at 5
        MathFunction df = x -> 1.0;
        MathFunction newton = new NewtonMethodFunction(f, df);

        assertEquals(5.0, newton.apply(5.0), 0.0);
    }

    @Test
    void derivativeZero_throwsArithmeticException() {
        MathFunction f = x -> x * x; // derivative 0 at x=0
        MathFunction df = x -> 2.0 * x;
        MathFunction newton = new NewtonMethodFunction(f, df);

        assertThrows(ArithmeticException.class, () -> newton.apply(0.0));
    }
}


