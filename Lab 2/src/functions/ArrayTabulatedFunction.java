package functions;

import java.util.Arrays;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction implements Removable {
    protected double[] xValues;
    protected double[] yValues;
    protected int count;

    public ArrayTabulatedFunction(double[] xValues, double[] yValues) {
        if (xValues == null || yValues == null) {
            throw new IllegalArgumentException("xValues and yValues must not be null");
        }
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException("xValues and yValues must be the same length");
        }
        if (xValues.length == 0) {
            throw new IllegalArgumentException("xValues and yValues must not be empty");
        }
        // Copy to protect from external mutation
        this.xValues = Arrays.copyOf(xValues, xValues.length);
        this.yValues = Arrays.copyOf(yValues, yValues.length);
        this.count = xValues.length;
    }

    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (source == null) {
            throw new IllegalArgumentException("source function must not be null");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("count must be positive");
        }
        if (xFrom > xTo) {
            double tmp = xFrom; xFrom = xTo; xTo = tmp;
        }
        this.xValues = new double[count];
        this.yValues = new double[count];
        this.count = count;

        if (count == 1) {
            this.xValues[0] = xFrom;
            this.yValues[0] = source.apply(xFrom);
            return;
        }

        if (xFrom == xTo) {
            Arrays.fill(this.xValues, xFrom);
            double y = source.apply(xFrom);
            Arrays.fill(this.yValues, y);
            return;
        }

        double step = (xTo - xFrom) / (count - 1);
        for (int i = 0; i < count; i++) {
            double x = xFrom + step * i;
            this.xValues[i] = x;
            this.yValues[i] = source.apply(x);
        }
    }

    public int getCount() {
        return count;
    }

    public double getX(int index) {
        checkIndex(index);
        return xValues[index];
    }

    public double getY(int index) {
        checkIndex(index);
        return yValues[index];
    }

    public void setY(int index, double value) {
        checkIndex(index);
        yValues[index] = value;
    }

    protected double leftBound() {
        return xValues[0];
    }

    protected double rightBound() {
        return xValues[count - 1];
    }

    protected int indexOfX(double x) {
        for (int i = 0; i < count; i++) {
            if (xValues[i] == x) return i;
        }
        return -1;
    }

    protected int indexOfY(double y) {
        for (int i = 0; i < count; i++) {
            if (yValues[i] == y) return i;
        }
        return -1;
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (count == 1) return 0;
        if (x < xValues[0]) return 0;
        if (x > xValues[count - 1]) return count;
        // return the index of the largest x less than given x
        int low = 0;
        int high = count - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            double midX = xValues[mid];
            if (midX < x) {
                low = mid + 1;
            } else if (midX > x) {
                high = mid - 1;
            } else {
                return mid; // exact match
            }
        }
        return Math.max(0, low - 1);
    }

    @Override
    protected double extrapolateLeft(double x) {
        if (count == 1) return yValues[0];
        double x0 = xValues[0];
        double x1 = xValues[1];
        double y0 = yValues[0];
        double y1 = yValues[1];
        return interpolate(x, x0, x1, y0, y1);
    }

    @Override
    protected double extrapolateRight(double x) {
        if (count == 1) return yValues[0];
        double x0 = xValues[count - 2];
        double x1 = xValues[count - 1];
        double y0 = yValues[count - 2];
        double y1 = yValues[count - 1];
        return interpolate(x, x0, x1, y0, y1);
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        if (count == 1) return yValues[0];
        int rightIndex = Math.min(floorIndex + 1, count - 1);
        double leftX = xValues[floorIndex];
        double rightX = xValues[rightIndex];
        double leftY = yValues[floorIndex];
        double rightY = yValues[rightIndex];
        if (leftX == rightX) return leftY; // degenerate interval
        return interpolate(x, leftX, rightX, leftY, rightY);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + count);
        }
    }

    @Override
    public void remove(double x) {
        int idx = indexOfX(x);
        if (idx == -1) return; // nothing to remove
        if (count == 1) {
            // shrink to empty not allowed by current invariants; keep as single point
            return;
        }
        double[] newX = new double[count - 1];
        double[] newY = new double[count - 1];
        if (idx > 0) {
            System.arraycopy(xValues, 0, newX, 0, idx);
            System.arraycopy(yValues, 0, newY, 0, idx);
        }
        if (idx < count - 1) {
            System.arraycopy(xValues, idx + 1, newX, idx, count - idx - 1);
            System.arraycopy(yValues, idx + 1, newY, idx, count - idx - 1);
        }
        xValues = newX;
        yValues = newY;
        count--;
    }
}


