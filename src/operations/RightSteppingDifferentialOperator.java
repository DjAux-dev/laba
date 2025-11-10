package operations;

import functions.MathFunction;

public class RightSteppingDifferentialOperator extends SteppingDifferentialOperator {
    public RightSteppingDifferentialOperator(double step) {
        super(step);
    }

    @Override
    public MathFunction derive(MathFunction function) {
        return new MathFunction() {
            @Override
            public double apply(double x) {
                double h = getStep();
                return (function.apply(x + h) - function.apply(x)) / h;
            }
        };
    }
}


