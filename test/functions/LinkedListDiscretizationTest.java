package functions;

public class LinkedListDiscretizationTest {
    public static void run() {
        MathFunction src = x -> x * x;
        TabulatedFunction tf = new LinkedListTabulatedFunction(src, 2.0, 0.0, 3);
        TestUtils.assertEquals(3, tf.getCount());
        TestUtils.assertEquals(0.0, tf.leftBound());
        TestUtils.assertEquals(2.0, tf.rightBound());
        TestUtils.assertEquals(0.0, tf.getX(0));
        TestUtils.assertEquals(1.0, tf.getX(1));
        TestUtils.assertEquals(2.0, tf.getX(2));
        TestUtils.assertEquals(0.0, tf.getY(0));
        TestUtils.assertEquals(1.0, tf.getY(1));
        TestUtils.assertEquals(4.0, tf.getY(2));
        TestUtils.assertAlmostEquals(0.25, tf.apply(0.5));
    }
}


