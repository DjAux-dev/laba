package operations;

import concurrent.SynchronizedTabulatedFunction;
import functions.MathFunction;
import functions.TabulatedFunction;
import functions.TabulatedFunctionFactory;
import functions.ArrayTabulatedFunctionFactory;

public class TabulatedDifferentialOperator implements DifferentialOperator<TabulatedFunction> {
    private final SteppingDifferentialOperator operator;
    private final TabulatedFunctionFactory factory;

    public TabulatedDifferentialOperator(SteppingDifferentialOperator operator) {
        this.operator = operator;
        this.factory = new ArrayTabulatedFunctionFactory();
    }

    public TabulatedDifferentialOperator(SteppingDifferentialOperator operator, TabulatedFunctionFactory factory) {
        this.operator = operator;
        this.factory = factory;
    }

    @Override
    public TabulatedFunction derive(TabulatedFunction function) {
        MathFunction derivativeMathFunction = operator.derive(function);
        
        int count = function.getCount();
        double[] xValues = new double[count];
        double[] yValues = new double[count];
        
        for (int i = 0; i < count; i++) {
            xValues[i] = function.getX(i);
            yValues[i] = derivativeMathFunction.apply(xValues[i]);
        }
        
        return factory.create(xValues, yValues);
    }

    public TabulatedFunction deriveSynchronously(TabulatedFunction function) {
        SynchronizedTabulatedFunction synchronizedFunction;
        
        if (function instanceof SynchronizedTabulatedFunction) {
            synchronizedFunction = (SynchronizedTabulatedFunction) function;
        } else {
            synchronizedFunction = new SynchronizedTabulatedFunction(function);
        }
        
        return synchronizedFunction.doSynchronously(this::derive);
    }
}

