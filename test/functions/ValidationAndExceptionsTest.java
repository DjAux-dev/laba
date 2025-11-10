package functions;

import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;
import exceptions.InterpolationException;

public class ValidationAndExceptionsTest {
    public static void run() {
        testCheckLengthIsTheSame();
        testCheckSorted();
        testConstructorsValidation();
        testInterpolationException();
    }

    private static void testCheckLengthIsTheSame() {
        double[] x1 = {0.0, 1.0};
        double[] y1 = {2.0};
        boolean thrown = false;
        try {
            AbstractTabulatedFunction.checkLengthIsTheSame(x1, y1);
        } catch (DifferentLengthOfArraysException e) {
            thrown = true;
        }
        if (!thrown) throw new AssertionError("DifferentLengthOfArraysException was expected");

        // matching lengths should not throw
        AbstractTabulatedFunction.checkLengthIsTheSame(new double[]{0.0}, new double[]{1.0});
    }

    private static void testCheckSorted() {
        // sorted should pass
        AbstractTabulatedFunction.checkSorted(new double[]{0.0, 1.0, 2.0});

        // duplicates should fail
        boolean dupThrown = false;
        try {
            AbstractTabulatedFunction.checkSorted(new double[]{0.0, 0.0});
        } catch (ArrayIsNotSortedException e) {
            dupThrown = true;
        }
        if (!dupThrown) throw new AssertionError("ArrayIsNotSortedException expected for duplicates");

        // descending should fail
        boolean descThrown = false;
        try {
            AbstractTabulatedFunction.checkSorted(new double[]{2.0, 1.0});
        } catch (ArrayIsNotSortedException e) {
            descThrown = true;
        }
        if (!descThrown) throw new AssertionError("ArrayIsNotSortedException expected for descending order");
    }

    private static void testConstructorsValidation() {
        // ArrayTabulatedFunction length mismatch
        boolean lenThrown = false;
        try {
            new ArrayTabulatedFunction(new double[]{0.0, 1.0}, new double[]{2.0});
        } catch (DifferentLengthOfArraysException e) {
            lenThrown = true;
        }
        if (!lenThrown) throw new AssertionError("DifferentLengthOfArraysException expected in ArrayTabulatedFunction constructor");

        // ArrayTabulatedFunction unsorted
        boolean sortThrown = false;
        try {
            new ArrayTabulatedFunction(new double[]{1.0, 0.0}, new double[]{0.0, 1.0});
        } catch (ArrayIsNotSortedException e) {
            sortThrown = true;
        }
        if (!sortThrown) throw new AssertionError("ArrayIsNotSortedException expected in ArrayTabulatedFunction constructor");

        // LinkedListTabulatedFunction length mismatch
        boolean lenThrownLL = false;
        try {
            new LinkedListTabulatedFunction(new double[]{0.0, 1.0}, new double[]{2.0});
        } catch (DifferentLengthOfArraysException e) {
            lenThrownLL = true;
        }
        if (!lenThrownLL) throw new AssertionError("DifferentLengthOfArraysException expected in LinkedListTabulatedFunction constructor");

        // LinkedListTabulatedFunction unsorted
        boolean sortThrownLL = false;
        try {
            new LinkedListTabulatedFunction(new double[]{1.0, 0.0}, new double[]{0.0, 1.0});
        } catch (ArrayIsNotSortedException e) {
            sortThrownLL = true;
        }
        if (!sortThrownLL) throw new AssertionError("ArrayIsNotSortedException expected in LinkedListTabulatedFunction constructor");
    }

    private static void testInterpolationException() {
        double[] x = {0.0, 1.0, 2.0};
        double[] y = {0.0, 1.0, 4.0};

        ArrayTabulatedFunction arr = new ArrayTabulatedFunction(x, y);
        boolean thrown = false;
        try {
            // floorIndex=0 corresponds to [0,1], x=-1 is outside -> should throw
            arr.interpolate(-1.0, 0);
        } catch (InterpolationException e) {
            thrown = true;
        }
        if (!thrown) throw new AssertionError("InterpolationException expected in ArrayTabulatedFunction.interpolate");

        LinkedListTabulatedFunction list = new LinkedListTabulatedFunction(x, y);
        boolean thrownLL = false;
        try {
            // floorIndex=1 corresponds to [1,2], x=3 is outside -> should throw
            list.interpolate(3.0, 1);
        } catch (InterpolationException e) {
            thrownLL = true;
        }
        if (!thrownLL) throw new AssertionError("InterpolationException expected in LinkedListTabulatedFunction.interpolate");
    }
}


