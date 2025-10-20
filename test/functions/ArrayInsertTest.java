package functions;

public class ArrayInsertTest {
    public static void run() {
        ArrayTabulatedFunction atf = new ArrayTabulatedFunction(
            new double[]{0.0, 1.0, 3.0}, new double[]{0.0, 1.0, 9.0}
        );
        atf.insert(2.0, 4.0);
        TestUtils.assertEquals(4, atf.getCount());
        TestUtils.assertEquals(2.0, atf.getX(2));
        TestUtils.assertEquals(4.0, atf.getY(2));
        atf.insert(1.0, 2.0);
        TestUtils.assertEquals(4, atf.getCount());
        TestUtils.assertEquals(2.0, atf.getY(1));
        atf.insert(-1.0, 1.0);
        TestUtils.assertEquals(5, atf.getCount());
        TestUtils.assertEquals(-1.0, atf.getX(0));
        TestUtils.assertEquals(1.0, atf.getY(0));
        atf.insert(10.0, 100.0);
        TestUtils.assertEquals(6, atf.getCount());
        TestUtils.assertEquals(10.0, atf.getX(5));
        TestUtils.assertEquals(100.0, atf.getY(5));
    }
}


