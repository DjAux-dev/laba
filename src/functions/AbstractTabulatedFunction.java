package functions;

/**
 * Abstract base for tabulated functions providing common interpolation/extrapolation helpers.
 */
public abstract class AbstractTabulatedFunction implements TabulatedFunction {
    protected int count;

    @Override
    public int getCount() {
        return count;
    }

    /**
     * Returns floor index i such that getX(i) <= x, or throws if x < leftBound().
     */
    protected int floorIndexOfX(double x) {
        if (x < leftBound()) {
            throw new IllegalArgumentException("x is less than left bound");
        }
        int n = getCount();
        if (n == 0) {
            throw new IllegalStateException("No points in tabulated function");
        }
        if (n == 1) {
            return 0;
        }
        if (x >= rightBound()) {
            return n - 2; // last segment start
        }
        // Linear scan; list-backed impls can override for speed if desired
        for (int i = 0; i < n - 1; i++) {
            double xi = getX(i);
            double xj = getX(i + 1);
            if (xi <= x && x <= xj) {
                return i;
            }
        }
        return n - 2; // fallback
    }

    protected double interpolate(double x, int floorIndex) {
        if (count == 1) {
            return getY(0);
        }
        double x0 = getX(floorIndex);
        double y0 = getY(floorIndex);
        double x1 = getX(floorIndex + 1);
        double y1 = getY(floorIndex + 1);
        if (x1 == x0) return y0;
        double t = (x - x0) / (x1 - x0);
        return y0 + t * (y1 - y0);
    }

    protected double extrapolateLeft(double x) {
        if (count == 1) return getY(0);
        return interpolate(x, 0);
    }

    protected double extrapolateRight(double x) {
        if (count == 1) return getY(0);
        return interpolate(x, count - 2);
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
        int i = floorIndexOfX(x);
        return interpolate(x, i);
    }
}


