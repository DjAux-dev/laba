package operations;

import functions.Point;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

public class TabulatedDifferentialOperator implements DifferentialOperator<TabulatedFunction> {
	private TabulatedFunctionFactory factory;

	public TabulatedDifferentialOperator() {
		this.factory = new ArrayTabulatedFunctionFactory();
	}

	public TabulatedDifferentialOperator(TabulatedFunctionFactory factory) {
		this.factory = factory;
	}

	public TabulatedFunctionFactory getFactory() {
		return factory;
	}

	public void setFactory(TabulatedFunctionFactory factory) {
		this.factory = factory;
	}

	@Override
	public TabulatedFunction derive(TabulatedFunction function) {
		Point[] pts = TabulatedFunctionOperationService.asPoints(function);
		int n = pts.length;
		double[] xValues = new double[n];
		double[] yValues = new double[n];
		for (int i = 0; i < n; i++) {
			xValues[i] = pts[i].x;
		}
		if (n >= 2) {
			// forward difference for first
			yValues[0] = (pts[1].y - pts[0].y) / (pts[1].x - pts[0].x);
			// centered differences for interior
			for (int i = 1; i <= n - 2; i++) {
				yValues[i] = (pts[i + 1].y - pts[i - 1].y) / (pts[i + 1].x - pts[i - 1].x);
			}
			// backward difference for last
			yValues[n - 1] = (pts[n - 1].y - pts[n - 2].y) / (pts[n - 1].x - pts[n - 2].x);
		}
		return factory.create(xValues, yValues);
	}
}


