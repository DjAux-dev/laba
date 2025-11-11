package functions;

import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;

import java.io.Serializable;

public abstract class AbstractTabulatedFunction implements TabulatedFunction, Serializable {
    private static final long serialVersionUID = 1L;
    protected int count;

    @Override
    public int getCount() {
        return count;
    }

    public static void checkLengthIsTheSame(double[] xValues, double[] yValues) {
        if (xValues.length != yValues.length) {
            throw new DifferentLengthOfArraysException();
        }
    }

    public static void checkSorted(double[] xValues) {
        for (int i = 0; i + 1 < xValues.length; i++) {
            if (xValues[i] >= xValues[i + 1]) {
                throw new ArrayIsNotSortedException();
            }
        }
    }

    protected abstract int floorIndexOfX(double x);

    protected abstract double extrapolateLeft(double x);
    
    protected abstract double extrapolateRight(double x);
    
    protected abstract double interpolate(double x, int floorIndex);
    
    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        if (rightX == leftX) return leftY;
        double t = (x - leftX) / (rightX - leftX);
        return leftY + t * (rightY - leftY);
    }


    @Override
    public double apply(double x) {
        if (count == 0) {
            throw new IllegalStateException("No points in tabulated function");
        }
        if (x < leftBound()) {
            return extrapolateLeft(x);
        }
        if (x > rightBound()) {
            return extrapolateRight(x);
        }
        int index = indexOfX(x);
        if (index != -1) {
            return getY(index);
        }
        int floorIndex = floorIndexOfX(x);
        return interpolate(x, floorIndex);
    }
}


