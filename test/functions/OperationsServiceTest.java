package functions;

import operations.TabulatedFunctionOperationService;

public class OperationsServiceTest {
    public static void run() {
        testAsPointsWithArray();
        testAsPointsWithList();
        testAsPointsEmptyAndSingle();
    }

    private static void testAsPointsWithArray() {
        double[] xs = {0.0, 1.0, 2.0};
        double[] ys = {0.0, 1.0, 4.0};
        ArrayTabulatedFunction f = new ArrayTabulatedFunction(xs, ys);

        Point[] pts = TabulatedFunctionOperationService.asPoints(f);
        TestUtils.assertEquals(3.0, pts.length);
        for (int i = 0; i < xs.length; i++) {
            TestUtils.assertEquals(xs[i], pts[i].x);
            TestUtils.assertEquals(ys[i], pts[i].y);
        }
    }

    private static void testAsPointsWithList() {
        double[] xs = {0.0, 1.0, 3.0, 4.0};
        double[] ys = {0.0, 1.0, 9.0, 16.0};
        LinkedListTabulatedFunction f = new LinkedListTabulatedFunction(xs, ys);

        Point[] pts = TabulatedFunctionOperationService.asPoints(f);
        TestUtils.assertEquals(4.0, pts.length);
        for (int i = 0; i < xs.length; i++) {
            TestUtils.assertEquals(xs[i], pts[i].x);
            TestUtils.assertEquals(ys[i], pts[i].y);
        }
    }

    private static void testAsPointsEmptyAndSingle() {
        // Single point
        double[] xs1 = {2.0};
        double[] ys1 = {5.0};
        ArrayTabulatedFunction f1 = new ArrayTabulatedFunction(xs1, ys1);
        Point[] pts1 = TabulatedFunctionOperationService.asPoints(f1);
        TestUtils.assertEquals(1.0, pts1.length);
        TestUtils.assertEquals(2.0, pts1[0].x);
        TestUtils.assertEquals(5.0, pts1[0].y);
    }
}


