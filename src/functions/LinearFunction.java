package functions;

/**
 * Linear function: f(x) = a * x + b
 */
public final class LinearFunction implements MathFunction {
    private final double a;
    private final double b;

    public LinearFunction(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double apply(double x) {
        return a * x + b;
    }
}


