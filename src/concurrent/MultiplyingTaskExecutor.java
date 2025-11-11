package concurrent;

import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.UnitFunction;

import java.util.ArrayList;
import java.util.List;

public class MultiplyingTaskExecutor {
    public static void main(String[] args) {
        UnitFunction unitFunction = new UnitFunction();
        TabulatedFunction function = new LinkedListTabulatedFunction(unitFunction, 1, 1000, 1000);
        
        List<Thread> threads = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            MultiplyingTask task = new MultiplyingTask(function);
            Thread thread = new Thread(task);
            threads.add(thread);
        }
        
        for (Thread thread : threads) {
            thread.start();
        }
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println(function.toString());
    }
}

