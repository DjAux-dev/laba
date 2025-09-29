package functions;

public class NewtonMethodFunction implements MathFunction {
    private final MathFunction function;
    private final MathFunction derivative;

    public NewtonMethodFunction(MathFunction function, MathFunction derivative) {
        if (function == null || derivative == null) {
            throw new IllegalArgumentException("Function and derivative must not be null");
        }
        this.function = function;
        this.derivative = derivative;
    }

    @Override
    public double apply(double x) {
        double f = function.apply(x);
        double df = derivative.apply(x);
        if (df == 0.0) {
            throw new ArithmeticException("Derivative is zero at x=" + x);
        }
        return x - f / df;
    }
}


