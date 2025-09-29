package functions;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {
    protected int count;

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double apply(double x) {
        if (count == 0) {
            throw new IllegalStateException("Tabulated function has no points");
        }
        if (count == 1) {
            return getY(0);
        }

        if (x < leftBound()) {
            return extrapolateLeft(x);
        }
        if (x > rightBound()) {
            return extrapolateRight(x);
        }

        int exactIndex = indexOfX(x);
        if (exactIndex != -1) {
            return getY(exactIndex);
        }

        int i = floorIndexOfX(x);
        return interpolate(x, i);
    }

    protected int floorIndexOfX(double x) {
        if (x <= getX(0)) {
            return 0;
        }
        if (x >= getX(count - 1)) {
            return count - 2;
        }
        for (int i = 0; i < count - 1; i++) {
            double xi = getX(i);
            double xi1 = getX(i + 1);
            if (xi <= x && x < xi1) {
                return i;
            }
        }
        return count - 2; // fallback, should not happen with sorted unique x
    }

    protected double interpolate(double x, int floorIndex) {
        if (count == 1) {
            return getY(0);
        }
        double x0 = getX(floorIndex);
        double y0 = getY(floorIndex);
        double x1 = getX(floorIndex + 1);
        double y1 = getY(floorIndex + 1);
        double dx = x1 - x0;
        if (dx == 0.0) {
            return y0; // degenerate, but x assumed unique so unlikely
        }
        double t = (x - x0) / dx;
        return y0 + t * (y1 - y0);
    }

    protected double extrapolateLeft(double x) {
        if (count == 1) {
            return getY(0);
        }
        double x0 = getX(0);
        double y0 = getY(0);
        double x1 = getX(1);
        double y1 = getY(1);
        if (x1 == x0) {
            return y0;
        }
        double t = (x - x0) / (x1 - x0);
        return y0 + t * (y1 - y0);
    }

    protected double extrapolateRight(double x) {
        if (count == 1) {
            return getY(0);
        }
        double x0 = getX(count - 2);
        double y0 = getY(count - 2);
        double x1 = getX(count - 1);
        double y1 = getY(count - 1);
        if (x1 == x0) {
            return y0;
        }
        double t = (x - x0) / (x1 - x0);
        return y0 + t * (y1 - y0);
    }
}


