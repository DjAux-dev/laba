package functions;

public class LinkedListConstructorAndBasicOpsTest {
    public static void run() {
        double[] xs = {0.0, 1.0, 2.0, 4.0};
        double[] ys = {0.0, 1.0, 4.0, 16.0};
        TabulatedFunction tf = new LinkedListTabulatedFunction(xs, ys);
        TestUtils.assertEquals(4, tf.getCount());
        TestUtils.assertEquals(0.0, tf.leftBound());
        TestUtils.assertEquals(4.0, tf.rightBound());
        TestUtils.assertEquals(2.0, tf.getX(2));
        TestUtils.assertEquals(4.0, tf.getY(2));
        tf.setY(2, 5.0);
        TestUtils.assertEquals(5.0, tf.getY(2));
        TestUtils.assertEquals(0, tf.indexOfX(0.0));
        TestUtils.assertEquals(2, tf.indexOfX(2.0));
        TestUtils.assertEquals(-1, tf.indexOfX(3.0));
        TestUtils.assertEquals(2, tf.indexOfY(5.0));
        TestUtils.assertEquals(-1, tf.indexOfY(2.0));
        TestUtils.assertAlmostEquals(3.0, tf.apply(1.5));
        TestUtils.assertAlmostEquals(-0.5, tf.apply(-0.5));
        TestUtils.assertAlmostEquals(21.5, tf.apply(5.0));
    }
}


