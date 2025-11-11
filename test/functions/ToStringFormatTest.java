package functions;

public class ToStringFormatTest {
	public static void run() {
		double[] xs = {0.0, 0.5, 1.0};
		double[] ys = {0.0, 0.25, 1.0};

		LinkedListTabulatedFunction lf = new LinkedListTabulatedFunction(xs, ys);
		String expectedL = "LinkedListTabulatedFunction size = 3\n[0.0; 0.0]\n[0.5; 0.25]\n[1.0; 1.0]";
		String actualL = lf.toString();
		if (!expectedL.equals(actualL)) {
			throw new AssertionError("LinkedList toString mismatch.\nExpected:\n" + expectedL + "\nActual:\n" + actualL);
		}

		ArrayTabulatedFunction af = new ArrayTabulatedFunction(xs, ys);
		String expectedA = "ArrayTabulatedFunction size = 3\n[0.0; 0.0]\n[0.5; 0.25]\n[1.0; 1.0]";
		String actualA = af.toString();
		if (!expectedA.equals(actualA)) {
			throw new AssertionError("Array toString mismatch.\nExpected:\n" + expectedA + "\nActual:\n" + actualA);
		}
	}
}


