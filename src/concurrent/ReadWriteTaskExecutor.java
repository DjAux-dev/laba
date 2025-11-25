package concurrent;

import functions.ConstantFunction;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;

public class ReadWriteTaskExecutor {
    public static void main(String[] args) {
        TabulatedFunction function = new LinkedListTabulatedFunction(
                new ConstantFunction(-1.0),
                1.0,
                1000.0,
                1000
        );

        Thread reader = new Thread(new ReadTask(function));
        Thread writer = new Thread(new WriteTask(function, 0.5));

        reader.start();
        writer.start();
    }
}


