package concurrent;

import functions.ArrayTabulatedFunction;
import functions.Point;
import functions.TestUtils;

import java.util.Iterator;

public class SynchronizedTabulatedFunctionTest {
    public static void run() {
        testConstructor();
        testGetCount();
        testGetX();
        testGetY();
        testSetY();
        testIndexOfX();
        testIndexOfY();
        testLeftBound();
        testRightBound();
        testApply();
        testIterator();
        testDelegation();
    }

    private static void testConstructor() {
        System.out.println("Testing constructor...");
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        SynchronizedTabulatedFunction syncFunction = new SynchronizedTabulatedFunction(arrayFunction);
        
        TestUtils.assertEquals(4, syncFunction.getCount());
        System.out.println("✓ Constructor test passed");
    }

    private static void testGetCount() {
        System.out.println("Testing getCount()...");
        double[] xValues = {0.0, 1.0, 2.0};
        double[] yValues = {0.0, 1.0, 4.0};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        SynchronizedTabulatedFunction syncFunction = new SynchronizedTabulatedFunction(arrayFunction);
        
        TestUtils.assertEquals(3, syncFunction.getCount());
        System.out.println("✓ getCount() test passed");
    }

    private static void testGetX() {
        System.out.println("Testing getX()...");
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        SynchronizedTabulatedFunction syncFunction = new SynchronizedTabulatedFunction(arrayFunction);
        
        TestUtils.assertEquals(0.0, syncFunction.getX(0));
        TestUtils.assertEquals(1.0, syncFunction.getX(1));
        TestUtils.assertEquals(2.0, syncFunction.getX(2));
        TestUtils.assertEquals(3.0, syncFunction.getX(3));
        System.out.println("✓ getX() test passed");
    }

    private static void testGetY() {
        System.out.println("Testing getY()...");
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        SynchronizedTabulatedFunction syncFunction = new SynchronizedTabulatedFunction(arrayFunction);
        
        TestUtils.assertEquals(0.0, syncFunction.getY(0));
        TestUtils.assertEquals(1.0, syncFunction.getY(1));
        TestUtils.assertEquals(4.0, syncFunction.getY(2));
        TestUtils.assertEquals(9.0, syncFunction.getY(3));
        System.out.println("✓ getY() test passed");
    }

    private static void testSetY() {
        System.out.println("Testing setY()...");
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        SynchronizedTabulatedFunction syncFunction = new SynchronizedTabulatedFunction(arrayFunction);
        
        syncFunction.setY(0, 10.0);
        TestUtils.assertEquals(10.0, syncFunction.getY(0));
        TestUtils.assertEquals(10.0, arrayFunction.getY(0));
        
        syncFunction.setY(2, 20.0);
        TestUtils.assertEquals(20.0, syncFunction.getY(2));
        TestUtils.assertEquals(20.0, arrayFunction.getY(2));
        System.out.println("✓ setY() test passed");
    }

    private static void testIndexOfX() {
        System.out.println("Testing indexOfX()...");
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        SynchronizedTabulatedFunction syncFunction = new SynchronizedTabulatedFunction(arrayFunction);
        
        TestUtils.assertEquals(0, syncFunction.indexOfX(0.0));
        TestUtils.assertEquals(1, syncFunction.indexOfX(1.0));
        TestUtils.assertEquals(2, syncFunction.indexOfX(2.0));
        TestUtils.assertEquals(3, syncFunction.indexOfX(3.0));
        TestUtils.assertEquals(-1, syncFunction.indexOfX(5.0));
        System.out.println("✓ indexOfX() test passed");
    }

    private static void testIndexOfY() {
        System.out.println("Testing indexOfY()...");
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        SynchronizedTabulatedFunction syncFunction = new SynchronizedTabulatedFunction(arrayFunction);
        
        TestUtils.assertEquals(0, syncFunction.indexOfY(0.0));
        TestUtils.assertEquals(1, syncFunction.indexOfY(1.0));
        TestUtils.assertEquals(2, syncFunction.indexOfY(4.0));
        TestUtils.assertEquals(3, syncFunction.indexOfY(9.0));
        TestUtils.assertEquals(-1, syncFunction.indexOfY(10.0));
        System.out.println("✓ indexOfY() test passed");
    }

    private static void testLeftBound() {
        System.out.println("Testing leftBound()...");
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        SynchronizedTabulatedFunction syncFunction = new SynchronizedTabulatedFunction(arrayFunction);
        
        TestUtils.assertEquals(0.0, syncFunction.leftBound());
        System.out.println("✓ leftBound() test passed");
    }

    private static void testRightBound() {
        System.out.println("Testing rightBound()...");
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        SynchronizedTabulatedFunction syncFunction = new SynchronizedTabulatedFunction(arrayFunction);
        
        TestUtils.assertEquals(3.0, syncFunction.rightBound());
        System.out.println("✓ rightBound() test passed");
    }

    private static void testApply() {
        System.out.println("Testing apply()...");
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        SynchronizedTabulatedFunction syncFunction = new SynchronizedTabulatedFunction(arrayFunction);
        
        // Test exact matches
        TestUtils.assertEquals(0.0, syncFunction.apply(0.0));
        TestUtils.assertEquals(1.0, syncFunction.apply(1.0));
        TestUtils.assertEquals(4.0, syncFunction.apply(2.0));
        TestUtils.assertEquals(9.0, syncFunction.apply(3.0));
        
        // Test interpolation
        TestUtils.assertAlmostEquals(2.5, syncFunction.apply(1.5));
        
        // Test extrapolation left
        double leftExtrapolated = syncFunction.apply(-1.0);
        TestUtils.assertAlmostEquals(-1.0, leftExtrapolated);
        
        // Test extrapolation right
        double rightExtrapolated = syncFunction.apply(4.0);
        TestUtils.assertAlmostEquals(14.0, rightExtrapolated);
        
        System.out.println("✓ apply() test passed");
    }

    private static void testIterator() {
        System.out.println("Testing iterator()...");
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        SynchronizedTabulatedFunction syncFunction = new SynchronizedTabulatedFunction(arrayFunction);
        
        Iterator<Point> iterator = syncFunction.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            TestUtils.assertEquals(xValues[count], point.x);
            TestUtils.assertEquals(yValues[count], point.y);
            count++;
        }
        TestUtils.assertEquals(4, count);
        
        // Test enhanced for loop
        count = 0;
        for (Point point : syncFunction) {
            TestUtils.assertEquals(xValues[count], point.x);
            TestUtils.assertEquals(yValues[count], point.y);
            count++;
        }
        TestUtils.assertEquals(4, count);
        
        System.out.println("✓ iterator() test passed");
    }

    private static void testDelegation() {
        System.out.println("Testing delegation...");
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        SynchronizedTabulatedFunction syncFunction = new SynchronizedTabulatedFunction(arrayFunction);
        
        // Test that changes through wrapper affect underlying function
        syncFunction.setY(1, 100.0);
        TestUtils.assertEquals(100.0, arrayFunction.getY(1));
        TestUtils.assertEquals(100.0, syncFunction.getY(1));
        
        // Test that changes to underlying function are reflected in wrapper
        arrayFunction.setY(2, 200.0);
        TestUtils.assertEquals(200.0, syncFunction.getY(2));
        TestUtils.assertEquals(200.0, arrayFunction.getY(2));
        
        System.out.println("✓ delegation test passed");
    }
}

