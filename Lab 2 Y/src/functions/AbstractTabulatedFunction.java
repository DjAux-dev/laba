package functions;

public abstract class AbstractTabulatedFunction implements MathFunction {
    protected abstract int floorIndexOfX(double x);
    protected abstract double extrapolateLeft(double x);
    protected abstract double extrapolateRight(double x);
    protected abstract double interpolate(double x, int floorIndex);

    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        double slope = (rightY - leftY) / (rightX - leftX);
        return leftY + slope * (x - leftX);
    }

    protected abstract int indexOfX(double x);
    protected abstract double getY(int index);
    protected abstract double leftBound();
    protected abstract double rightBound();

    @Override
    public double apply(double x) {
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
        int floor = floorIndexOfX(x);
        return interpolate(x, floor);
    }
}


