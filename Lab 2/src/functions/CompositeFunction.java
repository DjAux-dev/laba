package functions;

public final class CompositeFunction implements MathFunction {
    private final MathFunction first;
    private final MathFunction second;

    public CompositeFunction(MathFunction first, MathFunction second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Functions must not be null");
        }
        this.first = first;
        this.second = second;
    }

    @Override
    public double apply(double x) {
        return second.apply(first.apply(x));
    }
}


