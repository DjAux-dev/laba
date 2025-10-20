package functions;

public final class NewtonMethodFunction implements MathFunction {
    private final MathFunction function;
    private final MathFunction derivative;

    public NewtonMethodFunction(MathFunction function, MathFunction derivative) {
        this.function = function;
        this.derivative = derivative;
    }

    @Override
    public double apply(double x) {
        double fx = function.apply(x);
        double dfx = derivative.apply(x);
        if (dfx == 0.0) {
            throw new ArithmeticException("Derivative is zero in NewtonMethodFunction");
        }
        return x - fx / dfx;
    }
}


