package operations;

import functions.Point;
import functions.TabulatedFunction;
import functions.factory.TabulatedFunctionFactory;
import functions.factory.ArrayTabulatedFunctionFactory;
import exceptions.InconsistentFunctionsException;

public class TabulatedFunctionOperationService {
    private interface BiOperation {
        double apply(double u, double v);
    }

    private TabulatedFunctionFactory factory;

    public TabulatedFunctionOperationService() {
        this.factory = new ArrayTabulatedFunctionFactory();
    }

    public TabulatedFunctionOperationService(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public TabulatedFunctionFactory getFactory() {
        return factory;
    }

    public void setFactory(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public static Point[] asPoints(TabulatedFunction tabulatedFunction) {
        Point[] points = new Point[tabulatedFunction.getCount()];
        int i = 0;
        for (Point p : tabulatedFunction) {
            points[i] = p;
            i++;
        }
        return points;
    }

    public TabulatedFunction sum(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, new BiOperation() {
            @Override
            public double apply(double u, double v) {
                return u + v;
            }
        });
    }

    public TabulatedFunction subtract(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, new BiOperation() {
            @Override
            public double apply(double u, double v) {
                return u - v;
            }
        });
    }

    private TabulatedFunction doOperation(TabulatedFunction a, TabulatedFunction b, BiOperation operation) {
        int n = a.getCount();
        if (n != b.getCount()) {
            throw new InconsistentFunctionsException("Different number of points");
        }
        Point[] ap = asPoints(a);
        Point[] bp = asPoints(b);
        double[] xValues = new double[n];
        double[] yValues = new double[n];
        for (int i = 0; i < n; i++) {
            xValues[i] = ap[i].x;
            if (Double.doubleToLongBits(xValues[i]) != Double.doubleToLongBits(bp[i].x)) {
                throw new InconsistentFunctionsException("X values differ at index " + i);
            }
            yValues[i] = operation.apply(ap[i].y, bp[i].y);
        }
        return factory.create(xValues, yValues);
    }
}


