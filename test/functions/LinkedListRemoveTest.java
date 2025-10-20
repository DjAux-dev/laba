package functions;

public class LinkedListRemoveTest {
    public static void run() {
        LinkedListTabulatedFunction ltf = new LinkedListTabulatedFunction(
            new double[]{0.0, 1.0, 2.0, 3.0}, new double[]{0.0, 1.0, 4.0, 9.0}
        );
        ltf.remove(1);
        TestUtils.assertEquals(3, ltf.getCount());
        TestUtils.assertEquals(2.0, ltf.getX(1));
        ltf.remove(0);
        TestUtils.assertEquals(2, ltf.getCount());
        TestUtils.assertEquals(2.0, ltf.leftBound());
        ltf.remove(1);
        TestUtils.assertEquals(1, ltf.getCount());
        TestUtils.assertEquals(2.0, ltf.getX(0));
        ltf.remove(0);
        TestUtils.assertEquals(0, ltf.getCount());
    }
}


