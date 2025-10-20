package functions;

public class ArrayRemoveTest {
    public static void run() {
        testRemoveFromBeginning();
        testRemoveFromEnd();
        testRemoveFromMiddle();
        testRemoveSingleElement();
        testRemoveAllElements();
        testRemoveWithComposition();
        testRemoveEdgeCases();
        testRemoveErrorHandling();
    }

    private static void testRemoveFromBeginning() {
        System.out.println("Testing remove from beginning...");
        
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {1.0, 4.0, 9.0, 16.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues, yValues);
        
        TestUtils.assertEquals(4, func.getCount());
        TestUtils.assertEquals(1.0, func.leftBound());
        
        // Удаляем первый элемент
        func.remove(0);
        
        TestUtils.assertEquals(3, func.getCount());
        TestUtils.assertEquals(2.0, func.leftBound());
        TestUtils.assertEquals(2.0, func.getX(0));
        TestUtils.assertEquals(4.0, func.getY(0));
        TestUtils.assertEquals(3.0, func.getX(1));
        TestUtils.assertEquals(9.0, func.getY(1));
        TestUtils.assertEquals(4.0, func.getX(2));
        TestUtils.assertEquals(16.0, func.getY(2));
        
        System.out.println("✓ Remove from beginning works");
    }

    private static void testRemoveFromEnd() {
        System.out.println("Testing remove from end...");
        
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {1.0, 4.0, 9.0, 16.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues, yValues);
        
        TestUtils.assertEquals(4, func.getCount());
        TestUtils.assertEquals(4.0, func.rightBound());
        
        // Удаляем последний элемент
        func.remove(3);
        
        TestUtils.assertEquals(3, func.getCount());
        TestUtils.assertEquals(3.0, func.rightBound());
        TestUtils.assertEquals(1.0, func.getX(0));
        TestUtils.assertEquals(1.0, func.getY(0));
        TestUtils.assertEquals(2.0, func.getX(1));
        TestUtils.assertEquals(4.0, func.getY(1));
        TestUtils.assertEquals(3.0, func.getX(2));
        TestUtils.assertEquals(9.0, func.getY(2));
        
        System.out.println("✓ Remove from end works");
    }

    private static void testRemoveFromMiddle() {
        System.out.println("Testing remove from middle...");
        
        double[] xValues = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] yValues = {1.0, 4.0, 9.0, 16.0, 25.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues, yValues);
        
        TestUtils.assertEquals(5, func.getCount());
        
        // Удаляем элемент из середины (индекс 2)
        func.remove(2);
        
        TestUtils.assertEquals(4, func.getCount());
        TestUtils.assertEquals(1.0, func.getX(0));
        TestUtils.assertEquals(2.0, func.getX(1));
        TestUtils.assertEquals(4.0, func.getX(2)); // Было 3.0, стало 4.0
        TestUtils.assertEquals(16.0, func.getY(2)); // Было 9.0, стало 16.0
        TestUtils.assertEquals(5.0, func.getX(3));
        
        // Проверяем, что порядок сохранился
        for (int i = 0; i < func.getCount() - 1; i++) {
            if (func.getX(i) >= func.getX(i + 1)) {
                throw new AssertionError("Order not maintained after remove");
            }
        }
        
        System.out.println("✓ Remove from middle works");
    }

    private static void testRemoveSingleElement() {
        System.out.println("Testing remove single element...");
        
        double[] xValues = {5.0};
        double[] yValues = {25.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues, yValues);
        
        TestUtils.assertEquals(1, func.getCount());
        
        // Удаляем единственный элемент
        func.remove(0);
        
        TestUtils.assertEquals(0, func.getCount());
        
        // Проверяем, что функция всё ещё работает (должна выбрасывать исключение)
        try {
            func.getX(0);
            throw new AssertionError("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // Ожидаемое поведение
        }
        
        System.out.println("✓ Remove single element works");
    }

    private static void testRemoveAllElements() {
        System.out.println("Testing remove all elements...");
        
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues, yValues);
        
        TestUtils.assertEquals(3, func.getCount());
        
        // Удаляем все элементы по одному
        func.remove(1); // Удаляем средний элемент
        TestUtils.assertEquals(2, func.getCount());
        
        func.remove(0); // Удаляем первый элемент
        TestUtils.assertEquals(1, func.getCount());
        
        func.remove(0); // Удаляем последний элемент
        TestUtils.assertEquals(0, func.getCount());
        
        System.out.println("✓ Remove all elements works");
    }

    private static void testRemoveWithComposition() {
        System.out.println("Testing remove with function composition...");
        
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {1.0, 4.0, 9.0, 16.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues, yValues);
        
        // Создаём композицию с линейной функцией
        MathFunction linear = new LinearFunction(2.0, 1.0);
        MathFunction composed = new CompositeFunction(func, linear);
        
        // Проверяем, что композиция работает
        TestUtils.assertAlmostEquals(3.0, composed.apply(1.0)); // 2*1 + 1 = 3
        TestUtils.assertAlmostEquals(9.0, composed.apply(2.0)); // 2*4 + 1 = 9
        TestUtils.assertAlmostEquals(19.0, composed.apply(3.0)); // 2*9 + 1 = 19
        TestUtils.assertAlmostEquals(33.0, composed.apply(4.0)); // 2*16 + 1 = 33
        
        // Удаляем элемент
        func.remove(1); // Удаляем x=2.0, y=4.0
        
        // Проверяем, что композиция всё ещё работает
        TestUtils.assertAlmostEquals(3.0, composed.apply(1.0)); // 2*1 + 1 = 3
        TestUtils.assertAlmostEquals(19.0, composed.apply(3.0)); // 2*9 + 1 = 19
        TestUtils.assertAlmostEquals(33.0, composed.apply(4.0)); // 2*16 + 1 = 33
        
        // Проверяем, что удалённый элемент больше недоступен
        try {
            composed.apply(2.0);
            // Если не выбросило исключение, проверяем, что результат разумен
            // (может быть интерполяция или экстраполяция)
        } catch (Exception e) {
            // Это тоже нормально
        }
        
        System.out.println("✓ Remove with composition works");
    }

    private static void testRemoveEdgeCases() {
        System.out.println("Testing remove edge cases...");
        
        // Тест с очень маленькими значениями
        double[] xValues1 = {1e-10, 2e-10, 3e-10};
        double[] yValues1 = {1e-20, 4e-20, 9e-20};
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(xValues1, yValues1);
        
        func1.remove(1);
        TestUtils.assertEquals(2, func1.getCount());
        TestUtils.assertEquals(1e-10, func1.getX(0), 1e-15);
        TestUtils.assertEquals(3e-10, func1.getX(1), 1e-15);
        
        // Тест с очень большими значениями
        double[] xValues2 = {1e10, 2e10, 3e10};
        double[] yValues2 = {1e20, 4e20, 9e20};
        ArrayTabulatedFunction func2 = new ArrayTabulatedFunction(xValues2, yValues2);
        
        func2.remove(1);
        TestUtils.assertEquals(2, func2.getCount());
        TestUtils.assertEquals(1e10, func2.getX(0), 1e5);
        TestUtils.assertEquals(3e10, func2.getX(1), 1e5);
        
        // Тест с отрицательными значениями
        double[] xValues3 = {-3.0, -2.0, -1.0};
        double[] yValues3 = {9.0, 4.0, 1.0};
        ArrayTabulatedFunction func3 = new ArrayTabulatedFunction(xValues3, yValues3);
        
        func3.remove(1);
        TestUtils.assertEquals(2, func3.getCount());
        TestUtils.assertEquals(-3.0, func3.getX(0));
        TestUtils.assertEquals(-1.0, func3.getX(1));
        
        System.out.println("✓ Remove edge cases work");
    }

    private static void testRemoveErrorHandling() {
        System.out.println("Testing remove error handling...");
        
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues, yValues);
        
        // Тест с отрицательным индексом
        try {
            func.remove(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // Ожидаемое поведение
        }
        
        // Тест с индексом, равным размеру массива
        try {
            func.remove(3);
            throw new AssertionError("Expected IndexOutOfBoundsException for index >= count");
        } catch (IndexOutOfBoundsException e) {
            // Ожидаемое поведение
        }
        
        // Тест с очень большим индексом
        try {
            func.remove(100);
            throw new AssertionError("Expected IndexOutOfBoundsException for large index");
        } catch (IndexOutOfBoundsException e) {
            // Ожидаемое поведение
        }
        
        // Проверяем, что функция не изменилась после неудачных попыток удаления
        TestUtils.assertEquals(3, func.getCount());
        TestUtils.assertEquals(1.0, func.getX(0));
        TestUtils.assertEquals(2.0, func.getX(1));
        TestUtils.assertEquals(3.0, func.getX(2));
        
        System.out.println("✓ Remove error handling works");
    }
}
