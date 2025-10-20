package functions;

public class PerformanceAndStressTest {
    public static void run() {
        testLargeTabulatedFunctions();
        testDeepCompositionChains();
        testMemoryEfficiency();
        testStressScenarios();
    }

    private static void testLargeTabulatedFunctions() {
        System.out.println("Testing large tabulated functions...");
        
        // Создаём большую табулированную функцию
        MathFunction source = new SqrFunction();
        ArrayTabulatedFunction largeArray = new ArrayTabulatedFunction(source, 0.0, 100.0, 1001);
        LinkedListTabulatedFunction largeList = new LinkedListTabulatedFunction(source, 0.0, 100.0, 1001);
        
        // Тест производительности поиска в середине
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            largeArray.apply(50.0);
        }
        long arrayTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            largeList.apply(50.0);
        }
        long listTime = System.nanoTime() - startTime;
        
        System.out.println("ArrayTabulatedFunction time: " + arrayTime / 1_000_000.0 + " ms");
        System.out.println("LinkedListTabulatedFunction time: " + listTime / 1_000_000.0 + " ms");
        
        // Проверяем корректность результатов
        TestUtils.assertAlmostEquals(2500.0, largeArray.apply(50.0));
        TestUtils.assertAlmostEquals(2500.0, largeList.apply(50.0));
        
        // Тест композиции больших функций
        MathFunction linear = new LinearFunction(2.0, 1.0);
        MathFunction composedArray = new CompositeFunction(largeArray, linear);
        MathFunction composedList = new CompositeFunction(largeList, linear);
        
        TestUtils.assertAlmostEquals(5001.0, composedArray.apply(50.0)); // 2*2500 + 1
        TestUtils.assertAlmostEquals(5001.0, composedList.apply(50.0));
    }

    private static void testDeepCompositionChains() {
        System.out.println("Testing deep composition chains...");
        
        // Создаём цепочку из 10 функций
        MathFunction[] functions = new MathFunction[10];
        functions[0] = new ArrayTabulatedFunction(new SqrFunction(), 0.0, 2.0, 3);
        
        for (int i = 1; i < functions.length; i++) {
            if (i % 2 == 1) {
                functions[i] = new LinearFunction(i, i);
            } else {
                functions[i] = new LinkedListTabulatedFunction(new SqrFunction(), 0.0, 10.0, 3);
            }
        }
        
        // Создаём глубокую цепочку композиций
        MathFunction deepChain = functions[0];
        for (int i = 1; i < functions.length; i++) {
            deepChain = new CompositeFunction(deepChain, functions[i]);
        }
        
        // Тестируем глубокую цепочку
        double result = deepChain.apply(1.0);
        
        // Проверяем, что результат разумен
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            throw new AssertionError("Deep composition chain produced invalid result: " + result);
        }
        
        System.out.println("Deep composition chain result: " + result);
        
        // Тест обратной цепочки
        MathFunction reverseChain = functions[functions.length - 1];
        for (int i = functions.length - 2; i >= 0; i--) {
            reverseChain = new CompositeFunction(reverseChain, functions[i]);
        }
        
        double reverseResult = reverseChain.apply(1.0);
        if (Double.isNaN(reverseResult) || Double.isInfinite(reverseResult)) {
            throw new AssertionError("Reverse deep composition chain produced invalid result: " + reverseResult);
        }
        
        System.out.println("Reverse deep composition chain result: " + reverseResult);
    }

    private static void testMemoryEfficiency() {
        System.out.println("Testing memory efficiency...");
        
        // Создаём много табулированных функций
        ArrayTabulatedFunction[] arrayFunctions = new ArrayTabulatedFunction[100];
        LinkedListTabulatedFunction[] listFunctions = new LinkedListTabulatedFunction[100];
        
        for (int i = 0; i < 100; i++) {
            MathFunction source = new LinearFunction(i + 1, i);
            arrayFunctions[i] = new ArrayTabulatedFunction(source, 0.0, 10.0, 11);
            listFunctions[i] = new LinkedListTabulatedFunction(source, 0.0, 10.0, 11);
        }
        
        // Создаём композиции
        MathFunction arrayComposition = arrayFunctions[0];
        MathFunction listComposition = listFunctions[0];
        
        for (int i = 1; i < 10; i++) {
            arrayComposition = new CompositeFunction(arrayComposition, arrayFunctions[i]);
            listComposition = new CompositeFunction(listComposition, listFunctions[i]);
        }
        
        // Тестируем композиции
        double arrayResult = arrayComposition.apply(5.0);
        double listResult = listComposition.apply(5.0);
        
        if (Double.isNaN(arrayResult) || Double.isInfinite(arrayResult)) {
            throw new AssertionError("Array composition produced invalid result: " + arrayResult);
        }
        
        if (Double.isNaN(listResult) || Double.isInfinite(listResult)) {
            throw new AssertionError("List composition produced invalid result: " + listResult);
        }
        
        System.out.println("Memory efficiency test completed successfully");
    }

    private static void testStressScenarios() {
        System.out.println("Testing stress scenarios...");
        
        // Тест 1: Очень маленькие значения
        MathFunction tinySource = new MathFunction() {
            @Override
            public double apply(double x) {
                return x * 1e-10;
            }
        };
        
        ArrayTabulatedFunction tinyArray = new ArrayTabulatedFunction(tinySource, 0.0, 1e-6, 3);
        LinkedListTabulatedFunction tinyList = new LinkedListTabulatedFunction(tinySource, 0.0, 1e-6, 3);
        
        TestUtils.assertAlmostEquals(0.0, tinyArray.apply(0.0), 1e-15);
        TestUtils.assertAlmostEquals(0.0, tinyList.apply(0.0), 1e-15);
        
        // Тест 2: Очень большие значения
        MathFunction hugeSource = new MathFunction() {
            @Override
            public double apply(double x) {
                return x * 1e10;
            }
        };
        
        ArrayTabulatedFunction hugeArray = new ArrayTabulatedFunction(hugeSource, 0.0, 1e6, 3);
        LinkedListTabulatedFunction hugeList = new LinkedListTabulatedFunction(hugeSource, 0.0, 1e6, 3);
        
        TestUtils.assertAlmostEquals(1e10, hugeArray.apply(1.0), 1e5);
        TestUtils.assertAlmostEquals(1e10, hugeList.apply(1.0), 1e5);
        
        // Тест 3: Композиция с экстремальными значениями
        MathFunction extremeComposition = new CompositeFunction(tinyArray, hugeArray);
        double extremeResult = extremeComposition.apply(1e-6);
        
        if (Double.isNaN(extremeResult) || Double.isInfinite(extremeResult)) {
            System.out.println("Extreme composition handled gracefully");
        } else {
            System.out.println("Extreme composition result: " + extremeResult);
        }
        
        // Тест 4: Множественные вставки и удаления
        ArrayTabulatedFunction mutableArray = new ArrayTabulatedFunction(new SqrFunction(), 0.0, 2.0, 3);
        LinkedListTabulatedFunction mutableList = new LinkedListTabulatedFunction(new SqrFunction(), 0.0, 2.0, 3);
        
        // Добавляем много точек
        for (int i = 0; i < 10; i++) {
            mutableArray.insert(0.1 * i, 0.01 * i * i);
            mutableList.insert(0.1 * i, 0.01 * i * i);
        }
        
        // Проверяем, что функции всё ещё работают
        double arrayResult = mutableArray.apply(1.0);
        double listResult = mutableList.apply(1.0);
        
        if (Double.isNaN(arrayResult) || Double.isInfinite(arrayResult)) {
            throw new AssertionError("Mutable array function failed after insertions");
        }
        
        if (Double.isNaN(listResult) || Double.isInfinite(listResult)) {
            throw new AssertionError("Mutable list function failed after insertions");
        }
        
        // Удаляем точки из списка
        for (int i = 5; i < 10; i++) {
            mutableList.remove(5); // Удаляем элемент с индексом 5
        }
        
        // Проверяем, что функция всё ещё работает после удалений
        double finalResult = mutableList.apply(1.0);
        if (Double.isNaN(finalResult) || Double.isInfinite(finalResult)) {
            throw new AssertionError("Mutable list function failed after removals");
        }
        
        System.out.println("Stress test completed successfully");
    }
}
