package functions;

/**
 * Identity function: f(x) = x
 */
public final class IdentityFunction implements MathFunction {
    @Override
    public double apply(double x) {
        return x;
    }
}


