package functions;

import exceptions.InconsistentFunctionsException;
import operations.TabulatedFunctionOperationService;

public class OperationsServiceBinaryOpsTest {
    public static void run() {
        testSumWithArray();
        testSubtractWithList();
        testCountMismatchThrows();
        testXMismatchThrows();
        testSumArrayPlusListWithLinkedListFactory();
        testSubtractListMinusArrayWithArrayFactory();
    }

    private static void testSumWithArray() {
        double[] xs = {0.0, 1.0, 2.0};
        double[] y1 = {0.0, 1.0, 4.0};
        double[] y2 = {1.0, 2.0, 3.0};
        ArrayTabulatedFunction f1 = new ArrayTabulatedFunction(xs, y1);
        ArrayTabulatedFunction f2 = new ArrayTabulatedFunction(xs, y2);

        TabulatedFunctionOperationService svc = new TabulatedFunctionOperationService();
        TabulatedFunction sum = svc.sum(f1, f2);

        TestUtils.assertEquals(3, sum.getCount());
        TestUtils.assertEquals(0.0, sum.getX(0));
        TestUtils.assertEquals(1.0, sum.getX(1));
        TestUtils.assertEquals(2.0, sum.getX(2));
        TestUtils.assertEquals(1.0, sum.getY(0)); // 0+1
        TestUtils.assertEquals(3.0, sum.getY(1)); // 1+2
        TestUtils.assertEquals(7.0, sum.getY(2)); // 4+3
    }

    private static void testSubtractWithList() {
        double[] xs = {0.0, 1.0, 3.0};
        double[] y1 = {0.0, 1.0, 9.0};
        double[] y2 = {1.0, 1.5, 4.5};
        LinkedListTabulatedFunction f1 = new LinkedListTabulatedFunction(xs, y1);
        LinkedListTabulatedFunction f2 = new LinkedListTabulatedFunction(xs, y2);

        TabulatedFunctionOperationService svc = new TabulatedFunctionOperationService();
        TabulatedFunction diff = svc.subtract(f1, f2);

        TestUtils.assertEquals(3, diff.getCount());
        TestUtils.assertEquals(0.0, diff.getX(0));
        TestUtils.assertEquals(1.0, diff.getX(1));
        TestUtils.assertEquals(3.0, diff.getX(2));
        TestUtils.assertEquals(-1.0, diff.getY(0)); // 0-1
        TestUtils.assertEquals(-0.5, diff.getY(1)); // 1-1.5
        TestUtils.assertEquals(4.5, diff.getY(2)); // 9-4.5
    }

    private static void testCountMismatchThrows() {
        double[] xs1 = {0.0, 1.0};
        double[] ys1 = {0.0, 1.0};
        double[] xs2 = {0.0, 1.0, 2.0};
        double[] ys2 = {0.0, 1.0, 4.0};
        ArrayTabulatedFunction f1 = new ArrayTabulatedFunction(xs1, ys1);
        ArrayTabulatedFunction f2 = new ArrayTabulatedFunction(xs2, ys2);

        TabulatedFunctionOperationService svc = new TabulatedFunctionOperationService();
        boolean thrown = false;
        try {
            svc.sum(f1, f2);
        } catch (InconsistentFunctionsException e) {
            thrown = true;
        }
        if (!thrown) throw new AssertionError("InconsistentFunctionsException expected for count mismatch");
    }

    private static void testXMismatchThrows() {
        double[] xs1 = {0.0, 2.0};
        double[] ys1 = {1.0, 3.0};
        double[] xs2 = {0.0, 3.0};
        double[] ys2 = {2.0, 4.0};
        ArrayTabulatedFunction f1 = new ArrayTabulatedFunction(xs1, ys1);
        ArrayTabulatedFunction f2 = new ArrayTabulatedFunction(xs2, ys2);

        TabulatedFunctionOperationService svc = new TabulatedFunctionOperationService();
        boolean thrown = false;
        try {
            svc.subtract(f1, f2);
        } catch (InconsistentFunctionsException e) {
            thrown = true;
        }
        if (!thrown) throw new AssertionError("InconsistentFunctionsException expected for x mismatch");
    }

    private static void testSumArrayPlusListWithLinkedListFactory() {
        double[] xs = {0.0, 1.0, 2.0};
        double[] y1 = {1.0, 2.0, 3.0};
        double[] y2 = {2.0, 4.0, 6.0};
        ArrayTabulatedFunction fArray = new ArrayTabulatedFunction(xs, y1);
        LinkedListTabulatedFunction fList = new LinkedListTabulatedFunction(xs, y2);

        TabulatedFunctionOperationService svc = new TabulatedFunctionOperationService(new LinkedListTabulatedFunctionFactory());
        TabulatedFunction sum = svc.sum(fArray, fList);

        TestUtils.assertEquals(3, sum.getCount());
        TestUtils.assertEquals(0.0, sum.getX(0));
        TestUtils.assertEquals(1.0, sum.getX(1));
        TestUtils.assertEquals(2.0, sum.getX(2));
        TestUtils.assertEquals(3.0, sum.getY(0)); // 1+2
        TestUtils.assertEquals(6.0, sum.getY(1)); // 2+4
        TestUtils.assertEquals(9.0, sum.getY(2)); // 3+6
    }

    private static void testSubtractListMinusArrayWithArrayFactory() {
        double[] xs = {0.0, 1.0, 3.0, 4.0};
        double[] yList = {0.0, 1.0, 9.0, 16.0};
        double[] yArr = {1.0, 1.0, 4.0, 9.0};
        LinkedListTabulatedFunction fList = new LinkedListTabulatedFunction(xs, yList);
        ArrayTabulatedFunction fArray = new ArrayTabulatedFunction(xs, yArr);

        TabulatedFunctionOperationService svc = new TabulatedFunctionOperationService(new ArrayTabulatedFunctionFactory());
        TabulatedFunction diff = svc.subtract(fList, fArray);

        TestUtils.assertEquals(4, diff.getCount());
        TestUtils.assertEquals(0.0, diff.getX(0));
        TestUtils.assertEquals(1.0, diff.getX(1));
        TestUtils.assertEquals(3.0, diff.getX(2));
        TestUtils.assertEquals(4.0, diff.getX(3));
        TestUtils.assertEquals(-1.0, diff.getY(0)); // 0-1
        TestUtils.assertEquals(0.0, diff.getY(1));  // 1-1
        TestUtils.assertEquals(5.0, diff.getY(2));  // 9-4
        TestUtils.assertEquals(7.0, diff.getY(3));  // 16-9
    }
}


