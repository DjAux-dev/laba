package io;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.SqrFunction;
import functions.TabulatedFunction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TabulatedFunctionFileWriter {
    public static void main(String[] args) {
        try (FileWriter fileWriter1 = new FileWriter("output/array function.txt");
             FileWriter fileWriter2 = new FileWriter("output/linked list function.txt");
             BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
             BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2)) {
            
            TabulatedFunction arrayFunction = new ArrayTabulatedFunction(new SqrFunction(), 0, 10, 11);
            TabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(new SqrFunction(), 0, 10, 11);
            
            FunctionsIO.writeTabulatedFunction(bufferedWriter1, arrayFunction);
            FunctionsIO.writeTabulatedFunction(bufferedWriter2, linkedListFunction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

