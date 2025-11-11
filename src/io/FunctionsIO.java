package io;

import functions.Point;
import functions.TabulatedFunction;
import functions.TabulatedFunctionFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public final class FunctionsIO {
    private FunctionsIO() {
        throw new UnsupportedOperationException("FunctionsIO is a utility class and cannot be instantiated");
    }

    public static void writeTabulatedFunction(BufferedWriter writer, TabulatedFunction function) throws IOException {
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.println(function.getCount());
        for (Point point : function) {
            printWriter.printf("%f %f\n", point.x, point.y);
        }
        writer.flush();
    }

    public static TabulatedFunction readTabulatedFunction(BufferedReader reader, TabulatedFunctionFactory factory) throws IOException {
        String firstLine = reader.readLine();
        int count = Integer.parseInt(firstLine);
        double[] xValues = new double[count];
        double[] yValues = new double[count];
        NumberFormat formatter = NumberFormat.getInstance(Locale.forLanguageTag("ru"));
        for (int i = 0; i < count; i++) {
            String line = reader.readLine();
            String[] parts = line.split(" ");
            try {
                xValues[i] = formatter.parse(parts[0]).doubleValue();
                yValues[i] = formatter.parse(parts[1]).doubleValue();
            } catch (ParseException e) {
                throw new IOException(e);
            }
        }
        return factory.create(xValues, yValues);
    }
}


