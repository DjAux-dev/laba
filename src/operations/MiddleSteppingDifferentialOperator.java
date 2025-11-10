package operations;

import functions.MathFunction;

public class MiddleSteppingDifferentialOperator extends SteppingDifferentialOperator {
    public MiddleSteppingDifferentialOperator(double step) {
        super(step);
    }

    @Override
    public MathFunction derive(MathFunction function) {
        return new MathFunction() {
            @Override
            public double apply(double x) {
                double h = getStep();
                return (function.apply(x + h) - function.apply(x - h)) / (2.0 * h);
            }
        };
    }
}


