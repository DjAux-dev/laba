package functions;

public final class SqrFunction implements MathFunction {
    @Override
    public double apply(double x) {
        return Math.pow(x, 2);
    }
}


