package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTabulatedFunctionTest {

    @Test
    void construct_fromArrays_andAccessorsWork() {
        double[] xs = {0.0, 1.0, 2.0, 3.0};
        double[] ys = {0.0, 1.0, 4.0, 9.0};
        TabulatedFunction f = new LinkedListTabulatedFunction(xs, ys);

        assertEquals(4, f.getCount());
        assertEquals(0.0, f.leftBound());
        assertEquals(3.0, f.rightBound());
        assertEquals(2.0, f.getX(2));
        assertEquals(4.0, f.getY(2));
        f.setY(2, 5.0);
        assertEquals(5.0, f.getY(2));
    }

    @Test
    void construct_fromSourceFunction_discretization() {
        MathFunction sqr = x -> x * x;
        TabulatedFunction f = new LinkedListTabulatedFunction(sqr, -1.0, 1.0, 3);
        // points: -1, 0, 1
        assertEquals(-1.0, f.getX(0));
        assertEquals(0.0, f.getX(1));
        assertEquals(1.0, f.getX(2));
        assertEquals(1.0, f.getY(0));
        assertEquals(0.0, f.getY(1));
        assertEquals(1.0, f.getY(2));
    }

    @Test
    void indexOfX_indexOfY() {
        double[] xs = {1.0, 2.0, 4.0};
        double[] ys = {10.0, 20.0, 40.0};
        TabulatedFunction f = new LinkedListTabulatedFunction(xs, ys);
        assertEquals(1, f.indexOfX(2.0));
        assertEquals(-1, f.indexOfX(3.0));
        assertEquals(2, f.indexOfY(40.0));
        assertEquals(-1, f.indexOfY(30.0));
    }

    @Test
    void apply_interpolation_and_extrapolation() {
        double[] xs = {0.0, 2.0};
        double[] ys = {0.0, 4.0};
        TabulatedFunction f = new LinkedListTabulatedFunction(xs, ys);
        // linear y=2x
        assertEquals(1.0, f.apply(0.5), 1e-12);
        assertEquals(3.0, f.apply(1.5), 1e-12);
        assertEquals(-2.0, f.apply(-1.0), 1e-12); // left extrapolation
        assertEquals(6.0, f.apply(3.0), 1e-12);   // right extrapolation
    }

    @Test
    void apply_singlePoint_returnsThatYAlways() {
        MathFunction c = x -> 7.0;
        TabulatedFunction f = new LinkedListTabulatedFunction(c, 5.0, 5.0, 1);
        assertEquals(7.0, f.apply(-100.0));
        assertEquals(7.0, f.apply(5.0));
        assertEquals(7.0, f.apply(100.0));
    }
}


