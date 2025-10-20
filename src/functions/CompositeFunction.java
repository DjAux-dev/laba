package functions;

public final class CompositeFunction implements MathFunction {
    private final MathFunction firstFunction;  // f
    private final MathFunction secondFunction; // g

    public CompositeFunction(MathFunction firstFunction, MathFunction secondFunction) {
        this.firstFunction = firstFunction;
        this.secondFunction = secondFunction;
    }

    @Override
    public double apply(double x) {
        double y = firstFunction.apply(x);
        return secondFunction.apply(y);
    }
}


