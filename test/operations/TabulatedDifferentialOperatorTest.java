package operations;

import concurrent.SynchronizedTabulatedFunction;
import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.LinkedListTabulatedFunctionFactory;
import functions.SqrFunction;
import functions.TabulatedFunction;
import functions.TestUtils;

public class TabulatedDifferentialOperatorTest {
    public static void run() {
        testDerive();
        testDeriveSynchronouslyWithRegularFunction();
        testDeriveSynchronouslyWithSynchronizedFunction();
        testDeriveSynchronouslyWithLeftOperator();
        testDeriveSynchronouslyWithRightOperator();
        testDeriveSynchronouslyWithMiddleOperator();
        testDeriveSynchronouslyWithCustomFactory();
    }

    private static void testDerive() {
        System.out.println("Testing derive()...");
        double[] xValues = {0.0, 1.0, 2.0, 3.0, 4.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0, 16.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        
        double step = (function.rightBound() - function.leftBound()) / (function.getCount() - 1);
        MiddleSteppingDifferentialOperator steppingOperator = new MiddleSteppingDifferentialOperator(step);
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(steppingOperator);
        
        TabulatedFunction derivative = operator.derive(function);
        
        TestUtils.assertEquals(function.getCount(), derivative.getCount());
        for (int i = 0; i < function.getCount(); i++) {
            TestUtils.assertEquals(function.getX(i), derivative.getX(i));
        }
        
        // For f(x) = x^2, derivative should be approximately 2x
        // Using middle difference, at x=2.0, derivative should be approximately 4.0
        TestUtils.assertAlmostEquals(4.0, derivative.getY(2), 0.1);
        
        System.out.println("✓ derive() test passed");
    }

    private static void testDeriveSynchronouslyWithRegularFunction() {
        System.out.println("Testing deriveSynchronously() with regular function...");
        double[] xValues = {0.0, 1.0, 2.0, 3.0, 4.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0, 16.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        
        double step = (function.rightBound() - function.leftBound()) / (function.getCount() - 1);
        MiddleSteppingDifferentialOperator steppingOperator = new MiddleSteppingDifferentialOperator(step);
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(steppingOperator);
        
        TabulatedFunction derivative = operator.deriveSynchronously(function);
        
        TestUtils.assertEquals(function.getCount(), derivative.getCount());
        for (int i = 0; i < function.getCount(); i++) {
            TestUtils.assertEquals(function.getX(i), derivative.getX(i));
        }
        
        // For f(x) = x^2, derivative should be approximately 2x
        TestUtils.assertAlmostEquals(4.0, derivative.getY(2), 0.1);
        
        System.out.println("✓ deriveSynchronously() with regular function test passed");
    }

    private static void testDeriveSynchronouslyWithSynchronizedFunction() {
        System.out.println("Testing deriveSynchronously() with SynchronizedTabulatedFunction...");
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        SynchronizedTabulatedFunction synchronizedFunction = new SynchronizedTabulatedFunction(arrayFunction);
        
        double step = (synchronizedFunction.rightBound() - synchronizedFunction.leftBound()) / (synchronizedFunction.getCount() - 1);
        MiddleSteppingDifferentialOperator steppingOperator = new MiddleSteppingDifferentialOperator(step);
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(steppingOperator);
        
        TabulatedFunction derivative = operator.deriveSynchronously(synchronizedFunction);
        
        TestUtils.assertEquals(synchronizedFunction.getCount(), derivative.getCount());
        for (int i = 0; i < synchronizedFunction.getCount(); i++) {
            TestUtils.assertEquals(synchronizedFunction.getX(i), derivative.getX(i));
        }
        
        // For f(x) = x^2, derivative should be approximately 2x
        TestUtils.assertAlmostEquals(4.0, derivative.getY(2), 0.1);
        
        System.out.println("✓ deriveSynchronously() with SynchronizedTabulatedFunction test passed");
    }

    private static void testDeriveSynchronouslyWithLeftOperator() {
        System.out.println("Testing deriveSynchronously() with LeftSteppingDifferentialOperator...");
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        
        double step = 0.1;
        LeftSteppingDifferentialOperator steppingOperator = new LeftSteppingDifferentialOperator(step);
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(steppingOperator);
        
        TabulatedFunction derivative = operator.deriveSynchronously(function);
        
        TestUtils.assertEquals(function.getCount(), derivative.getCount());
        // For f(x) = x^2, left difference at x=2.0 should be approximately 2*2 - step = 4 - 0.1 = 3.9
        TestUtils.assertAlmostEquals(3.9, derivative.getY(2), 0.2);
        
        System.out.println("✓ deriveSynchronously() with LeftSteppingDifferentialOperator test passed");
    }

    private static void testDeriveSynchronouslyWithRightOperator() {
        System.out.println("Testing deriveSynchronously() with RightSteppingDifferentialOperator...");
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        
        double step = 0.1;
        RightSteppingDifferentialOperator steppingOperator = new RightSteppingDifferentialOperator(step);
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(steppingOperator);
        
        TabulatedFunction derivative = operator.deriveSynchronously(function);
        
        TestUtils.assertEquals(function.getCount(), derivative.getCount());
        // For f(x) = x^2, right difference at x=2.0 should be approximately 2*2 + step = 4 + 0.1 = 4.1
        TestUtils.assertAlmostEquals(4.1, derivative.getY(2), 0.2);
        
        System.out.println("✓ deriveSynchronously() with RightSteppingDifferentialOperator test passed");
    }

    private static void testDeriveSynchronouslyWithMiddleOperator() {
        System.out.println("Testing deriveSynchronously() with MiddleSteppingDifferentialOperator...");
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        
        double step = 0.1;
        MiddleSteppingDifferentialOperator steppingOperator = new MiddleSteppingDifferentialOperator(step);
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(steppingOperator);
        
        TabulatedFunction derivative = operator.deriveSynchronously(function);
        
        TestUtils.assertEquals(function.getCount(), derivative.getCount());
        // For f(x) = x^2, middle difference at x=2.0 should be approximately 2*2 = 4.0
        TestUtils.assertAlmostEquals(4.0, derivative.getY(2), 0.1);
        
        System.out.println("✓ deriveSynchronously() with MiddleSteppingDifferentialOperator test passed");
    }

    private static void testDeriveSynchronouslyWithCustomFactory() {
        System.out.println("Testing deriveSynchronously() with custom factory...");
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        
        double step = (function.rightBound() - function.leftBound()) / (function.getCount() - 1);
        MiddleSteppingDifferentialOperator steppingOperator = new MiddleSteppingDifferentialOperator(step);
        LinkedListTabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(steppingOperator, factory);
        
        TabulatedFunction derivative = operator.deriveSynchronously(function);
        
        TestUtils.assertEquals(function.getCount(), derivative.getCount());
        // Verify that the result is a LinkedListTabulatedFunction
        if (!(derivative instanceof LinkedListTabulatedFunction)) {
            throw new AssertionError("Expected LinkedListTabulatedFunction but got " + derivative.getClass().getName());
        }
        
        System.out.println("✓ deriveSynchronously() with custom factory test passed");
    }
}

