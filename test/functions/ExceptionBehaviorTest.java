package functions;

public class ExceptionBehaviorTest {
    public static void run() {
        // Constructors: length < 2
        try {
            new ArrayTabulatedFunction(new double[]{0.0}, new double[]{1.0});
            throw new AssertionError("Expected IllegalArgumentException for ArrayTabulatedFunction length < 2");
        } catch (IllegalArgumentException expected) { }

        try {
            new LinkedListTabulatedFunction(new double[]{0.0}, new double[]{1.0});
            throw new AssertionError("Expected IllegalArgumentException for LinkedListTabulatedFunction length < 2");
        } catch (IllegalArgumentException expected) { }

        // Constructors from MathFunction: count < 2
        MathFunction sqr = new SqrFunction();
        try {
            new ArrayTabulatedFunction(sqr, 0.0, 1.0, 1);
            throw new AssertionError("Expected IllegalArgumentException for ArrayTabulatedFunction count < 2");
        } catch (IllegalArgumentException expected) { }

        try {
            new LinkedListTabulatedFunction(sqr, 0.0, 1.0, 1);
            throw new AssertionError("Expected IllegalArgumentException for LinkedListTabulatedFunction count < 2");
        } catch (IllegalArgumentException expected) { }

        // Valid functions for further checks
        double[] xs = {0.0, 1.0, 2.0};
        double[] ys = {0.0, 1.0, 4.0};
        ArrayTabulatedFunction af = new ArrayTabulatedFunction(xs, ys);
        LinkedListTabulatedFunction lf = new LinkedListTabulatedFunction(xs, ys);

        // Index validations: getX/getY/setY/remove
        assertThrows(() -> af.getX(-1));
        assertThrows(() -> af.getX(3));
        assertThrows(() -> af.getY(-1));
        assertThrows(() -> af.getY(3));
        assertThrows(() -> af.setY(-1, 0.0));
        assertThrows(() -> af.setY(3, 0.0));
        assertThrows(() -> af.remove(-1));
        assertThrows(() -> af.remove(3));

        assertThrows(() -> lf.getX(-1));
        assertThrows(() -> lf.getX(3));
        assertThrows(() -> lf.getY(-1));
        assertThrows(() -> lf.getY(3));
        assertThrows(() -> lf.setY(-1, 0.0));
        assertThrows(() -> lf.setY(3, 0.0));
        assertThrows(() -> lf.remove(-1));
        assertThrows(() -> lf.remove(3));

        // floorIndexOfX should throw when x < left bound
        assertThrows(() -> invokeFloorIndex(af, -0.1));
        assertThrows(() -> invokeFloorIndex(lf, -0.1));
    }

    private static void assertThrows(Runnable r) {
        try {
            r.run();
            throw new AssertionError("Expected exception was not thrown");
        } catch (IllegalArgumentException expected) { }
    }

    private static int invokeFloorIndex(AbstractTabulatedFunction f, double x) {
        // call through public API in a way that triggers floorIndexOfX only when x is within bounds;
        // for x < leftBound() the apply() short-circuits to extrapolateLeft, so we call via reflection
        try {
            java.lang.reflect.Method m = f.getClass().getDeclaredMethod("floorIndexOfX", double.class);
            m.setAccessible(true);
            return (int) m.invoke(f, x);
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (e.getTargetException() instanceof IllegalArgumentException) throw (IllegalArgumentException) e.getTargetException();
            throw new RuntimeException(e.getTargetException());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


