package io;

import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import operations.TabulatedDifferentialOperator;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TabulatedFunctionFileInputStream {
	public static void main(String[] args) {
		// 1) Read binary function from file and print
		try (FileInputStream fis = new FileInputStream("input/binary function.bin");
		     BufferedInputStream bis = new BufferedInputStream(fis)) {
			TabulatedFunction f = FunctionsIO.readTabulatedFunction(bis, new ArrayTabulatedFunctionFactory());
			System.out.println(f.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 2) Read a function from console (text format) and print its derivative
		try {
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			System.out.println("Введите размер и значения функции");
			TabulatedFunction f2 = FunctionsIO.readTabulatedFunction(br, new LinkedListTabulatedFunctionFactory());
			TabulatedDifferentialOperator op = new TabulatedDifferentialOperator();
			TabulatedFunction df2 = op.derive(f2);
			System.out.println(df2.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


