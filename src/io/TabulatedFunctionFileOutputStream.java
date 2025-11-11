package io;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TabulatedFunctionFileOutputStream {
	public static void main(String[] args) {
		try (FileOutputStream fosArray = new FileOutputStream("output/array function.bin");
		     FileOutputStream fosList = new FileOutputStream("output/linked list function.bin");
		     BufferedOutputStream bosArray = new BufferedOutputStream(fosArray);
		     BufferedOutputStream bosList = new BufferedOutputStream(fosList)) {

			double[] xs = {0.0, 0.5, 1.0, 1.5, 2.0};
			double[] ys = {0.0, 0.25, 1.0, 2.25, 4.0};

			ArrayTabulatedFunction arrayFunc = new ArrayTabulatedFunction(xs, ys);
			LinkedListTabulatedFunction listFunc = new LinkedListTabulatedFunction(xs, ys);

			FunctionsIO.writeTabulatedFunction(bosArray, arrayFunc);
			FunctionsIO.writeTabulatedFunction(bosList, listFunc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


