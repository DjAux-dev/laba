package functions;

import java.util.Arrays;

public final class ArrayTabulatedFunction extends AbstractTabulatedFunction implements TabulatedFunction, Insertable {
    private double[] xValues;
    private double[] yValues;

    public ArrayTabulatedFunction(double[] xValues, double[] yValues) {
        if (xValues == null || yValues == null) throw new IllegalArgumentException("null arrays");
        if (xValues.length != yValues.length) throw new IllegalArgumentException("length mismatch");
        this.count = 0;
        this.xValues = new double[xValues.length];
        this.yValues = new double[yValues.length];
        for (int i = 0; i < xValues.length; i++) {
            this.xValues[i] = xValues[i];
            this.yValues[i] = yValues[i];
            this.count++;
        }
    }

    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (source == null) throw new IllegalArgumentException("source is null");
        if (count <= 0) throw new IllegalArgumentException("count must be positive");
        double a = xFrom, b = xTo;
        if (a > b) { double t = a; a = b; b = t; }
        this.xValues = new double[count];
        this.yValues = new double[count];
        this.count = count;
        if (count == 1) {
            xValues[0] = a;
            yValues[0] = source.apply(a);
            return;
        }
        double step = (b - a) / (count - 1);
        for (int i = 0; i < count; i++) {
            double x = a + i * step;
            xValues[i] = x;
            yValues[i] = source.apply(x);
        }
    }

    @Override
    public double getX(int index) { return xValues[index]; }

    @Override
    public double getY(int index) { return yValues[index]; }

    @Override
    public void setY(int index, double value) { yValues[index] = value; }

    @Override
    public int indexOfX(double x) {
        for (int i = 0; i < count; i++) if (xValues[i] == x) return i;
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        for (int i = 0; i < count; i++) if (yValues[i] == y) return i;
        return -1;
    }

    @Override
    public double leftBound() { return xValues[0]; }

    @Override
    public double rightBound() { return xValues[count - 1]; }

    @Override
    public void insert(double x, double y) {
        int idx = indexOfX(x);
        if (idx >= 0) {
            yValues[idx] = y;
            return;
        }
        double[] newX = new double[count + 1];
        double[] newY = new double[count + 1];
        int pos = 0;
        while (pos < count && xValues[pos] < x) pos++;
        System.arraycopy(xValues, 0, newX, 0, pos);
        System.arraycopy(yValues, 0, newY, 0, pos);
        newX[pos] = x;
        newY[pos] = y;
        System.arraycopy(xValues, pos, newX, pos + 1, count - pos);
        System.arraycopy(yValues, pos, newY, pos + 1, count - pos);
        this.xValues = newX;
        this.yValues = newY;
        this.count++;
    }
}


