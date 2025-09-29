package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CompositeTabulatedCombinationsTest {

    @Test
    void compose_arrayThenList() {
        double[] xs = {0.0, 1.0, 2.0};
        double[] ys = {0.0, 1.0, 4.0};
        TabulatedFunction array = new ArrayTabulatedFunction(xs, ys); // approx x^2 on [0,2]

        MathFunction sqr = x -> x * x;
        TabulatedFunction list = new LinkedListTabulatedFunction(sqr, 0.0, 4.0, 5); // exact x^2 on [0,4]

        MathFunction h = new CompositeFunction(array, list); // list(array(x)) ≈ (x^2)^2 = x^4

        double x = 1.5;
        double expected = Math.pow(x, 4.0);
        assertEquals(expected, h.apply(x), 0.5); // allow approximation tolerance
    }

    @Test
    void compose_listThenArray() {
        MathFunction sin = Math::sin;
        TabulatedFunction list = new LinkedListTabulatedFunction(sin, 0.0, Math.PI, 7);

        double[] xs = {-1.0, 0.0, 1.0};
        double[] ys = {1.0, 0.0, 1.0}; // |x|
        TabulatedFunction array = new ArrayTabulatedFunction(xs, ys);

        MathFunction h = new CompositeFunction(list, array); // |sin(x)| approx

        assertEquals(Math.abs(Math.sin(0.3)), h.apply(0.3), 0.1);
        assertEquals(Math.abs(Math.sin(2.2)), h.apply(2.2), 0.1);
    }

    @Test
    void compose_tabulatedWithAnalytic() {
        TabulatedFunction list = new LinkedListTabulatedFunction(x -> x + 1.0, -2.0, 2.0, 5);
        MathFunction triple = x -> 3.0 * x;
        MathFunction h = new CompositeFunction(list, triple); // 3*(x+1)

        assertEquals(3.0 * (0.5 + 1.0), h.apply(0.5), 1e-12);
    }

    @Test
    void compose_analyticWithTabulated() {
        MathFunction sqr = x -> x * x;
        double[] xs = {0.0, 2.0, 4.0};
        double[] ys = {0.0, 1.0, 2.0}; // approximates sqrt(x)
        TabulatedFunction array = new ArrayTabulatedFunction(xs, ys);
        MathFunction h = new CompositeFunction(sqr, array); // ≈ sqrt(x^2)=|x|

        assertEquals(Math.abs(-1.2), h.apply(-1.2), 0.1);
        assertEquals(Math.abs(1.7), h.apply(1.7), 0.1);
    }
}


