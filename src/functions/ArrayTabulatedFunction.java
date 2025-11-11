package functions;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class ArrayTabulatedFunction extends AbstractTabulatedFunction implements TabulatedFunction, Insertable, Removable, Iterable<Point> {
    private static final long serialVersionUID = 2L;
    private double[] xValues;
    private double[] yValues;

    public ArrayTabulatedFunction(double[] xValues, double[] yValues) {
        if (xValues == null || yValues == null) throw new IllegalArgumentException("null arrays");
        checkLengthIsTheSame(xValues, yValues);
        checkSorted(xValues);
        this.count = xValues.length;
        this.xValues = Arrays.copyOf(xValues, xValues.length);
        this.yValues = Arrays.copyOf(yValues, yValues.length);
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

    @Override
    public void remove(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for count " + count);
        }
        
        // Создаём новые массивы размером на 1 меньше
        double[] newX = new double[count - 1];
        double[] newY = new double[count - 1];
        
        // Копируем элементы до удаляемого индекса
        System.arraycopy(xValues, 0, newX, 0, index);
        System.arraycopy(yValues, 0, newY, 0, index);
        
        // Копируем элементы после удаляемого индекса
        System.arraycopy(xValues, index + 1, newX, index, count - index - 1);
        System.arraycopy(yValues, index + 1, newY, index, count - index - 1);
        
        // Обновляем ссылки на массивы и счётчик
        this.xValues = newX;
        this.yValues = newY;
        this.count--;
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (count == 0) return -1;
        if (x < xValues[0]) return -1;
        if (x >= xValues[count - 1]) return count - 1;
        
        for (int i = 0; i < count - 1; i++) {
            if (x >= xValues[i] && x < xValues[i + 1]) {
                return i;
            }
        }
        return count - 1;
    }

    @Override
    protected double extrapolateLeft(double x) {
        if (count == 1) return yValues[0];
        return interpolate(x, xValues[0], xValues[1], yValues[0], yValues[1]);
    }

    @Override
    protected double extrapolateRight(double x) {
        if (count == 1) return yValues[0];
        return interpolate(x, xValues[count - 2], xValues[count - 1], yValues[count - 2], yValues[count - 1]);
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        if (count == 1) return yValues[0];
        if (floorIndex < 0) return extrapolateLeft(x);
        if (floorIndex >= count - 1) return extrapolateRight(x);

        double leftX = xValues[floorIndex];
        double rightX = xValues[floorIndex + 1];
        if (x < leftX || x > rightX) {
            throw new exceptions.InterpolationException("x is out of interpolation interval");
        }

        return interpolate(x, leftX, rightX, 
                         yValues[floorIndex], yValues[floorIndex + 1]);
    }

    @Override
    public Iterator<Point> iterator() {
        return new Iterator<Point>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < count;
            }

            @Override
            public Point next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more points");
                }
                Point point = new Point(xValues[i], yValues[i]);
                i++;
                return point;
            }
        };
    }
}


