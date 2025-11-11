package io;

import functions.ArrayTabulatedFunction;
import functions.MathFunction;
import functions.SqrFunction;
import functions.TabulatedFunction;
import operations.MiddleSteppingDifferentialOperator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ArrayTabulatedFunctionSerialization {
    public static void main(String[] args) {
        try (FileOutputStream fileOutputStream = new FileOutputStream("output/serialized array functions.bin");
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream)) {
            
            MathFunction sourceFunction = new SqrFunction();
            ArrayTabulatedFunction function = new ArrayTabulatedFunction(sourceFunction, 0, 10, 11);
            
            double step = (function.rightBound() - function.leftBound()) / (function.getCount() - 1);
            MiddleSteppingDifferentialOperator operator = new MiddleSteppingDifferentialOperator(step);
            
            MathFunction firstDerivative = operator.derive(function);
            ArrayTabulatedFunction firstDerivativeFunction = createTabulatedFromMathFunction(firstDerivative, function);
            
            MathFunction secondDerivative = operator.derive(firstDerivative);
            ArrayTabulatedFunction secondDerivativeFunction = createTabulatedFromMathFunction(secondDerivative, function);
            
            FunctionsIO.serialize(objectOutputStream, function);
            FunctionsIO.serialize(objectOutputStream, firstDerivativeFunction);
            FunctionsIO.serialize(objectOutputStream, secondDerivativeFunction);
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try (FileInputStream fileInputStream = new FileInputStream("output/serialized array functions.bin");
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
             ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream)) {
            
            TabulatedFunction deserializedFunction = FunctionsIO.deserialize(objectInputStream);
            TabulatedFunction deserializedFirstDerivative = FunctionsIO.deserialize(objectInputStream);
            TabulatedFunction deserializedSecondDerivative = FunctionsIO.deserialize(objectInputStream);
            
            System.out.println(deserializedFunction.toString());
            System.out.println(deserializedFirstDerivative.toString());
            System.out.println(deserializedSecondDerivative.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private static ArrayTabulatedFunction createTabulatedFromMathFunction(MathFunction mathFunction, TabulatedFunction template) {
        double[] xValues = new double[template.getCount()];
        double[] yValues = new double[template.getCount()];
        for (int i = 0; i < template.getCount(); i++) {
            xValues[i] = template.getX(i);
            yValues[i] = mathFunction.apply(xValues[i]);
        }
        return new ArrayTabulatedFunction(xValues, yValues);
    }
}

