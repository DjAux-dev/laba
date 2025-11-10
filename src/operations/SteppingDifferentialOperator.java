package operations;

import functions.MathFunction;

public abstract class SteppingDifferentialOperator implements DifferentialOperator<MathFunction> {
    protected double step;

    public SteppingDifferentialOperator(double step) {
        if (Double.isNaN(step) || step <= 0.0 || step == Double.POSITIVE_INFINITY) {
            throw new IllegalArgumentException("Invalid step");
        }
        this.step = step;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        if (Double.isNaN(step) || step <= 0.0 || step == Double.POSITIVE_INFINITY) {
            throw new IllegalArgumentException("Invalid step");
        }
        this.step = step;
    }
}


