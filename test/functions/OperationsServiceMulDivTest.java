package functions;

import operations.TabulatedFunctionOperationService;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;

public class OperationsServiceMulDivTest {
	public static void run() {
		double[] xs = {0.0, 1.0, 2.0};
		double[] ya = {1.0, 2.0, 3.0};
		double[] yb = {2.0, 4.0, 6.0};

		// Array factory
		ArrayTabulatedFunction aA = new ArrayTabulatedFunction(xs, ya);
		ArrayTabulatedFunction bA = new ArrayTabulatedFunction(xs, yb);
		TabulatedFunctionOperationService svcA = new TabulatedFunctionOperationService(new ArrayTabulatedFunctionFactory());
		TabulatedFunction mulA = svcA.multiply(aA, bA);
		TabulatedFunction divA = svcA.divide(aA, bA);
		TestUtils.assertEquals(2.0, mulA.getY(0));
		TestUtils.assertEquals(8.0, mulA.getY(1));
		TestUtils.assertEquals(18.0, mulA.getY(2));
		TestUtils.assertAlmostEquals(0.5, divA.getY(0));
		TestUtils.assertAlmostEquals(0.5, divA.getY(1));
		TestUtils.assertAlmostEquals(0.5, divA.getY(2));

		// Linked list factory
		LinkedListTabulatedFunction aL = new LinkedListTabulatedFunction(xs, ya);
		LinkedListTabulatedFunction bL = new LinkedListTabulatedFunction(xs, yb);
		TabulatedFunctionOperationService svcL = new TabulatedFunctionOperationService(new LinkedListTabulatedFunctionFactory());
		TabulatedFunction mulL = svcL.multiply(aL, bL);
		TabulatedFunction divL = svcL.divide(aL, bL);
		TestUtils.assertEquals(2.0, mulL.getY(0));
		TestUtils.assertEquals(8.0, mulL.getY(1));
		TestUtils.assertEquals(18.0, mulL.getY(2));
		TestUtils.assertAlmostEquals(0.5, divL.getY(0));
		TestUtils.assertAlmostEquals(0.5, divL.getY(1));
		TestUtils.assertAlmostEquals(0.5, divL.getY(2));
	}
}


