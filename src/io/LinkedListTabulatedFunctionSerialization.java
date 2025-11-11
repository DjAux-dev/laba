package io;

import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import operations.TabulatedDifferentialOperator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LinkedListTabulatedFunctionSerialization {
	public static void main(String[] args) {
		// Serialize three functions (f, f', f'')
		try (FileOutputStream fos = new FileOutputStream("output/serialized linked list functions.bin");
		     BufferedOutputStream bos = new BufferedOutputStream(fos);
		     java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(bos)) {

			double[] xs = {0.0, 0.5, 1.0, 1.5, 2.0};
			double[] ys = {0.0, 0.25, 1.0, 2.25, 4.0};
			LinkedListTabulatedFunction f = new LinkedListTabulatedFunction(xs, ys);

			TabulatedDifferentialOperator op = new TabulatedDifferentialOperator(new functions.factory.LinkedListTabulatedFunctionFactory());
			TabulatedFunction df = op.derive(f);
			TabulatedFunction d2f = op.derive(df);

			FunctionsIO.serialize(oos, f);
			FunctionsIO.serialize(oos, df);
			FunctionsIO.serialize(oos, d2f);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Deserialize three functions and print them
		try (FileInputStream fis = new FileInputStream("output/serialized linked list functions.bin");
		     BufferedInputStream bis = new BufferedInputStream(fis);
		     ObjectInputStream ois = new ObjectInputStream(bis)) {

			TabulatedFunction f1 = FunctionsIO.deserialize(ois);
			TabulatedFunction f2 = FunctionsIO.deserialize(ois);
			TabulatedFunction f3 = FunctionsIO.deserialize(ois);

			System.out.println(f1.toString());
			System.out.println();
			System.out.println(f2.toString());
			System.out.println();
			System.out.println(f3.toString());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}


