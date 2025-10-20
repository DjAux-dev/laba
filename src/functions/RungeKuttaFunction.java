package functions;


public final class RungeKuttaFunction implements MathFunction {
    private final MathFunction derivative;
    private final double step;

    public RungeKuttaFunction(MathFunction derivative, double step) {
        if (derivative == null) throw new IllegalArgumentException("derivative is null");
        if (!Double.isFinite(step) || step == 0.0) throw new IllegalArgumentException("step must be finite and non-zero");
        this.derivative = derivative;
        this.step = step;
    }

    @Override
    public double apply(double y) {
        double h = step;
        double k1 = derivative.apply(y);
        double k2 = derivative.apply(y + 0.5 * h * k1);
        double k3 = derivative.apply(y + 0.5 * h * k2);
        double k4 = derivative.apply(y + h * k3);
        return y + (h / 6.0) * (k1 + 2.0 * k2 + 2.0 * k3 + k4);
    }
}


