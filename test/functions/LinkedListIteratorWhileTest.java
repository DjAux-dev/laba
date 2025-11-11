package functions;

import java.util.Iterator;

public class LinkedListIteratorWhileTest {
    public static void run() {
        double[] xs = {0.0, 1.0, 2.0, 3.0};
        double[] ys = {0.0, 1.0, 4.0, 9.0};
        LinkedListTabulatedFunction tf = new LinkedListTabulatedFunction(xs, ys);

        Iterator<Point> it = tf.iterator();
        int i = 0;
        while (it.hasNext()) {
            Point p = it.next();
            TestUtils.assertEquals(xs[i], p.x);
            TestUtils.assertEquals(ys[i], p.y);
            i++;
        }
        TestUtils.assertEquals(xs.length, i);
    }
}


