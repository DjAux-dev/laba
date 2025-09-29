package functions;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction implements Insertable {
    private double[] xs;
    private double[] ys;

    public ArrayTabulatedFunction(double[] xValues, double[] yValues) {
        if (xValues == null || yValues == null) {
            throw new IllegalArgumentException("xValues and yValues must not be null");
        }
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException("xValues and yValues must have same length");
        }
        this.count = xValues.length;
        this.xs = new double[count];
        this.ys = new double[count];
        System.arraycopy(xValues, 0, xs, 0, count);
        System.arraycopy(yValues, 0, ys, 0, count);
    }

    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (source == null) {
            throw new IllegalArgumentException("source must not be null");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("count must be positive");
        }
        double from = xFrom;
        double to = xTo;
        if (from > to) {
            double tmp = from;
            from = to;
            to = tmp;
        }
        this.count = count;
        this.xs = new double[count];
        this.ys = new double[count];
        if (count == 1) {
            xs[0] = from;
            ys[0] = source.apply(from);
            return;
        }
        double step = (to - from) / (count - 1);
        for (int i = 0; i < count; i++) {
            xs[i] = from + i * step;
            ys[i] = source.apply(xs[i]);
        }
    }

    @Override
    public double getX(int index) {
        return xs[index];
    }

    @Override
    public double getY(int index) {
        return ys[index];
    }

    @Override
    public void setY(int index, double value) {
        ys[index] = value;
    }

    @Override
    public int indexOfX(double x) {
        for (int i = 0; i < count; i++) if (xs[i] == x) return i;
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        for (int i = 0; i < count; i++) if (ys[i] == y) return i;
        return -1;
    }

    @Override
    public double leftBound() {
        return xs[0];
    }

    @Override
    public double rightBound() {
        return xs[count - 1];
    }

    @Override
    public void insert(double x, double y) {
        // check if x exists -> replace y
        int idx = indexOfX(x);
        if (idx != -1) {
            ys[idx] = y;
            return;
        }

        // find position to insert to keep xs sorted
        int pos = 0;
        while (pos < count && xs[pos] < x) pos++;

        // grow arrays by one
        double[] newXs = new double[count + 1];
        double[] newYs = new double[count + 1];

        // copy left part
        if (pos > 0) {
            System.arraycopy(xs, 0, newXs, 0, pos);
            System.arraycopy(ys, 0, newYs, 0, pos);
        }
        // insert element
        newXs[pos] = x;
        newYs[pos] = y;
        // copy right part
        if (pos < count) {
            System.arraycopy(xs, pos, newXs, pos + 1, count - pos);
            System.arraycopy(ys, pos, newYs, pos + 1, count - pos);
        }

        this.xs = newXs;
        this.ys = newYs;
        this.count++;
    }
}


