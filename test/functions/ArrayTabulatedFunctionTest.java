package functions;

public class ArrayTabulatedFunctionTest {
    public static void run() {
        testConstructors();
        testBasicOperations();
        testInterpolation();
        testExtrapolation();
        testFloorIndexOfX();
        testApply();
        testEdgeCases();
    }

    private static void testConstructors() {
        // Test constructor with arrays
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(xValues, yValues);
        
        TestUtils.assertEquals(4, func1.getCount());
        TestUtils.assertEquals(0.0, func1.getX(0));
        TestUtils.assertEquals(3.0, func1.getX(3));
        TestUtils.assertEquals(0.0, func1.getY(0));
        TestUtils.assertEquals(9.0, func1.getY(3));

        // Test constructor with MathFunction
        MathFunction source = new SqrFunction();
        ArrayTabulatedFunction func2 = new ArrayTabulatedFunction(source, 0.0, 2.0, 3);
        
        TestUtils.assertEquals(3, func2.getCount());
        TestUtils.assertEquals(0.0, func2.getX(0));
        TestUtils.assertEquals(2.0, func2.getX(2));
        TestUtils.assertAlmostEquals(0.0, func2.getY(0));
        TestUtils.assertAlmostEquals(4.0, func2.getY(2));

        // Test constructor with swapped bounds
        ArrayTabulatedFunction func3 = new ArrayTabulatedFunction(source, 2.0, 0.0, 3);
        TestUtils.assertEquals(0.0, func3.getX(0));
        TestUtils.assertEquals(2.0, func3.getX(2));

        // Test constructor with single point
        ArrayTabulatedFunction func4 = new ArrayTabulatedFunction(source, 5.0, 5.0, 1);
        TestUtils.assertEquals(1, func4.getCount());
        TestUtils.assertEquals(5.0, func4.getX(0));
        TestUtils.assertAlmostEquals(25.0, func4.getY(0));
    }

    private static void testBasicOperations() {
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues, yValues);

        // Test getX, getY
        TestUtils.assertEquals(1.0, func.getX(1));
        TestUtils.assertEquals(4.0, func.getY(2));

        // Test setY
        func.setY(1, 2.0);
        TestUtils.assertEquals(2.0, func.getY(1));

        // Test indexOfX, indexOfY
        TestUtils.assertEquals(2, func.indexOfX(2.0));
        TestUtils.assertEquals(-1, func.indexOfX(1.5));
        TestUtils.assertEquals(2, func.indexOfY(4.0));
        TestUtils.assertEquals(-1, func.indexOfY(1.5));

        // Test bounds
        TestUtils.assertEquals(0.0, func.leftBound());
        TestUtils.assertEquals(3.0, func.rightBound());
    }

    private static void testInterpolation() {
        double[] xValues = {0.0, 1.0, 2.0};
        double[] yValues = {0.0, 1.0, 4.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues, yValues);

        // Test interpolation in the middle
        TestUtils.assertAlmostEquals(0.5, func.apply(0.5));
        TestUtils.assertAlmostEquals(2.5, func.apply(1.5));

        // Test exact points
        TestUtils.assertEquals(1.0, func.apply(1.0));
        TestUtils.assertEquals(4.0, func.apply(2.0));
    }

    private static void testExtrapolation() {
        double[] xValues = {0.0, 1.0, 2.0};
        double[] yValues = {0.0, 1.0, 4.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues, yValues);

        // Test left extrapolation
        TestUtils.assertAlmostEquals(-1.0, func.apply(-1.0));
        TestUtils.assertAlmostEquals(-2.0, func.apply(-2.0));

        // Test right extrapolation
        TestUtils.assertAlmostEquals(7.0, func.apply(3.0));
        TestUtils.assertAlmostEquals(10.0, func.apply(4.0));
    }

    private static void testFloorIndexOfX() {
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues, yValues);

        // Test floorIndexOfX indirectly through apply
        TestUtils.assertAlmostEquals(0.5, func.apply(0.5)); // should interpolate between 0 and 1
        TestUtils.assertAlmostEquals(2.5, func.apply(2.5)); // should interpolate between 2 and 3
    }

    private static void testApply() {
        double[] xValues = {0.0, 1.0, 2.0};
        double[] yValues = {0.0, 1.0, 4.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues, yValues);

        // Test apply with various inputs
        TestUtils.assertEquals(0.0, func.apply(0.0));
        TestUtils.assertEquals(1.0, func.apply(1.0));
        TestUtils.assertEquals(4.0, func.apply(2.0));
        TestUtils.assertAlmostEquals(0.5, func.apply(0.5));
        TestUtils.assertAlmostEquals(2.5, func.apply(1.5));
        TestUtils.assertAlmostEquals(-1.0, func.apply(-1.0));
        TestUtils.assertAlmostEquals(7.0, func.apply(3.0));
    }

    private static void testEdgeCases() {
        // Test single point function
        double[] xValues = {5.0};
        double[] yValues = {25.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues, yValues);

        TestUtils.assertEquals(25.0, func.apply(5.0));
        TestUtils.assertEquals(25.0, func.apply(0.0)); // extrapolation
        TestUtils.assertEquals(25.0, func.apply(10.0)); // extrapolation

        // Test constructor with MathFunction - single point
        MathFunction source = new SqrFunction();
        ArrayTabulatedFunction func2 = new ArrayTabulatedFunction(source, 3.0, 3.0, 1);
        TestUtils.assertEquals(1, func2.getCount());
        TestUtils.assertEquals(3.0, func2.getX(0));
        TestUtils.assertAlmostEquals(9.0, func2.getY(0));
    }
}
