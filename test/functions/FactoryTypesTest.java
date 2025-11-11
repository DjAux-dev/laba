package functions;

import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

public class FactoryTypesTest {
    public static void run() {
        double[] xs = {0.0, 1.0, 2.0};
        double[] ys = {0.0, 1.0, 4.0};

        TabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
        TabulatedFunction f1 = arrayFactory.create(xs, ys);
        if (!(f1 instanceof ArrayTabulatedFunction)) {
            throw new AssertionError("Expected ArrayTabulatedFunction from ArrayTabulatedFunctionFactory");
        }

        TabulatedFunctionFactory listFactory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction f2 = listFactory.create(xs, ys);
        if (!(f2 instanceof LinkedListTabulatedFunction)) {
            throw new AssertionError("Expected LinkedListTabulatedFunction from LinkedListTabulatedFunctionFactory");
        }
    }
}


