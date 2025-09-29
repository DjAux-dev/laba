package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayTabulatedFunctionInsertTest {

    @Test
    void replace_existingX_updatesY_only() {
        double[] xs = {1.0, 3.0, 5.0};
        double[] ys = {10.0, 30.0, 50.0};
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(xs, ys);

        f.insert(3.0, 99.0);

        assertEquals(3, f.getCount());
        assertEquals(99.0, f.getY(1));
        assertEquals(3.0, f.getX(1));
    }

    @Test
    void insert_atHead() {
        double[] xs = {2.0, 3.0};
        double[] ys = {20.0, 30.0};
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(xs, ys);

        f.insert(1.0, 10.0);

        assertEquals(3, f.getCount());
        assertEquals(1.0, f.getX(0));
        assertEquals(10.0, f.getY(0));
        assertEquals(2.0, f.getX(1));
    }

    @Test
    void insert_inMiddle() {
        double[] xs = {1.0, 4.0};
        double[] ys = {10.0, 40.0};
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(xs, ys);

        f.insert(3.0, 30.0);

        assertEquals(3, f.getCount());
        assertEquals(1.0, f.getX(0));
        assertEquals(3.0, f.getX(1));
        assertEquals(4.0, f.getX(2));
        assertEquals(30.0, f.getY(1));
    }

    @Test
    void insert_atTail() {
        double[] xs = {1.0, 2.0};
        double[] ys = {10.0, 20.0};
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(xs, ys);

        f.insert(5.0, 50.0);

        assertEquals(3, f.getCount());
        assertEquals(5.0, f.getX(2));
        assertEquals(50.0, f.getY(2));
        assertEquals(2.0, f.getX(1));
    }
}


