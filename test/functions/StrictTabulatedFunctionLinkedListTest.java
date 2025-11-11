package functions;

public class StrictTabulatedFunctionLinkedListTest {
    public static void run() {
        double[] xs = {0.0, 1.0, 2.0};
        double[] ys = {0.0, 1.0, 4.0};
        LinkedListTabulatedFunction base = new LinkedListTabulatedFunction(xs, ys);
        StrictTabulatedFunction strict = new StrictTabulatedFunction(base);

        // exact x: should return corresponding y
        TestUtils.assertEquals(0.0, strict.apply(0.0));
        TestUtils.assertEquals(1.0, strict.apply(1.0));
        TestUtils.assertEquals(4.0, strict.apply(2.0));

        // non-exact x: should throw UnsupportedOperationException
        assertUnsupported(() -> strict.apply(0.5));
        assertUnsupported(() -> strict.apply(-1.0));
        assertUnsupported(() -> strict.apply(3.0));

        // delegation sanity
        TestUtils.assertEquals(3, strict.getCount());
        TestUtils.assertEquals(1.0, strict.getX(1));
        TestUtils.assertEquals(1.0, strict.getY(1));
    }

    private static void assertUnsupported(Runnable r) {
        try {
            r.run();
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) { }
    }
}


