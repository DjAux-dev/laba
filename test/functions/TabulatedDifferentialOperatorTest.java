package functions;

import operations.TabulatedDifferentialOperator;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;

public class TabulatedDifferentialOperatorTest {
    public static void run() {
        double[] xs = {0.0, 1.0, 2.0, 3.0};
        double[] ys = {0.0, 1.0, 4.0, 9.0}; // x^2

        // Array-based
        ArrayTabulatedFunction baseArray = new ArrayTabulatedFunction(xs, ys);
        TabulatedDifferentialOperator opArr = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory());
        TabulatedFunction dArray = opArr.derive(baseArray);
        // expected finite differences: [1, 2, 4, 5]
        TestUtils.assertAlmostEquals(1.0, dArray.getY(0));
        TestUtils.assertAlmostEquals(2.0, dArray.getY(1));
        TestUtils.assertAlmostEquals(4.0, dArray.getY(2));
        TestUtils.assertAlmostEquals(5.0, dArray.getY(3));

        // Linked-list-based
        LinkedListTabulatedFunction baseList = new LinkedListTabulatedFunction(xs, ys);
        TabulatedDifferentialOperator opList = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
        TabulatedFunction dList = opList.derive(baseList);
        TestUtils.assertAlmostEquals(1.0, dList.getY(0));
        TestUtils.assertAlmostEquals(2.0, dList.getY(1));
        TestUtils.assertAlmostEquals(4.0, dList.getY(2));
        TestUtils.assertAlmostEquals(5.0, dList.getY(3));
    }
}


