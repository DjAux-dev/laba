package functions;

public class LinkedListInsertTest {
    public static void run() {
        testInsertIntoEmptyList();
        testInsertAtBeginning();
        testInsertAtEnd();
        testInsertInMiddle();
        testInsertExistingX();
        testInsertMaintainsOrder();
        testInsertWithComposition();
        testInsertEdgeCases();
    }

    private static void testInsertIntoEmptyList() {
        System.out.println("Testing insert into empty LinkedListTabulatedFunction...");
        
        LinkedListTabulatedFunction func = new LinkedListTabulatedFunction(new double[0], new double[0]);
        TestUtils.assertEquals(0, func.getCount());
        
        func.insert(5.0, 25.0);
        TestUtils.assertEquals(1, func.getCount());
        TestUtils.assertEquals(5.0, func.getX(0));
        TestUtils.assertEquals(25.0, func.getY(0));
        
        System.out.println("✓ Insert into empty list works");
    }

    private static void testInsertAtBeginning() {
        System.out.println("Testing insert at beginning...");
        
        double[] xValues = {2.0, 3.0, 4.0};
        double[] yValues = {4.0, 9.0, 16.0};
        LinkedListTabulatedFunction func = new LinkedListTabulatedFunction(xValues, yValues);
        
        TestUtils.assertEquals(3, func.getCount());
        TestUtils.assertEquals(2.0, func.leftBound());
        
        // Вставляем элемент в начало
        func.insert(1.0, 1.0);
        
        TestUtils.assertEquals(4, func.getCount());
        TestUtils.assertEquals(1.0, func.leftBound());
        TestUtils.assertEquals(1.0, func.getX(0));
        TestUtils.assertEquals(1.0, func.getY(0));
        TestUtils.assertEquals(2.0, func.getX(1));
        TestUtils.assertEquals(4.0, func.getY(1));
        
        System.out.println("✓ Insert at beginning works");
    }

    private static void testInsertAtEnd() {
        System.out.println("Testing insert at end...");
        
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        LinkedListTabulatedFunction func = new LinkedListTabulatedFunction(xValues, yValues);
        
        TestUtils.assertEquals(3, func.getCount());
        TestUtils.assertEquals(3.0, func.rightBound());
        
        // Вставляем элемент в конец
        func.insert(4.0, 16.0);
        
        TestUtils.assertEquals(4, func.getCount());
        TestUtils.assertEquals(4.0, func.rightBound());
        TestUtils.assertEquals(4.0, func.getX(3));
        TestUtils.assertEquals(16.0, func.getY(3));
        TestUtils.assertEquals(3.0, func.getX(2));
        TestUtils.assertEquals(9.0, func.getY(2));
        
        System.out.println("✓ Insert at end works");
    }

    private static void testInsertInMiddle() {
        System.out.println("Testing insert in middle...");
        
        double[] xValues = {1.0, 3.0, 5.0};
        double[] yValues = {1.0, 9.0, 25.0};
        LinkedListTabulatedFunction func = new LinkedListTabulatedFunction(xValues, yValues);
        
        TestUtils.assertEquals(3, func.getCount());
        
        // Вставляем элемент в середину
        func.insert(2.0, 4.0);
        
        TestUtils.assertEquals(4, func.getCount());
        TestUtils.assertEquals(1.0, func.getX(0));
        TestUtils.assertEquals(2.0, func.getX(1));
        TestUtils.assertEquals(4.0, func.getY(1));
        TestUtils.assertEquals(3.0, func.getX(2));
        TestUtils.assertEquals(5.0, func.getX(3));
        
        // Проверяем, что порядок сохранился
        for (int i = 0; i < func.getCount() - 1; i++) {
            if (func.getX(i) >= func.getX(i + 1)) {
                throw new AssertionError("Order not maintained after insert");
            }
        }
        
        System.out.println("✓ Insert in middle works");
    }

    private static void testInsertExistingX() {
        System.out.println("Testing insert with existing x...");
        
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        LinkedListTabulatedFunction func = new LinkedListTabulatedFunction(xValues, yValues);
        
        TestUtils.assertEquals(3, func.getCount());
        TestUtils.assertEquals(4.0, func.getY(1)); // y для x=2.0
        
        // Вставляем элемент с существующим x
        func.insert(2.0, 8.0);
        
        TestUtils.assertEquals(3, func.getCount()); // Количество не изменилось
        TestUtils.assertEquals(8.0, func.getY(1)); // y изменился
        
        System.out.println("✓ Insert with existing x works");
    }

    private static void testInsertMaintainsOrder() {
        System.out.println("Testing that insert maintains order...");
        
        LinkedListTabulatedFunction func = new LinkedListTabulatedFunction(new double[0], new double[0]);
        
        // Вставляем элементы в случайном порядке
        func.insert(5.0, 25.0);
        func.insert(1.0, 1.0);
        func.insert(3.0, 9.0);
        func.insert(2.0, 4.0);
        func.insert(4.0, 16.0);
        
        TestUtils.assertEquals(5, func.getCount());
        
        // Проверяем, что элементы упорядочены по x
        for (int i = 0; i < func.getCount() - 1; i++) {
            if (func.getX(i) >= func.getX(i + 1)) {
                throw new AssertionError("Order not maintained: " + func.getX(i) + " >= " + func.getX(i + 1));
            }
        }
        
        // Проверяем конкретные значения
        TestUtils.assertEquals(1.0, func.getX(0));
        TestUtils.assertEquals(2.0, func.getX(1));
        TestUtils.assertEquals(3.0, func.getX(2));
        TestUtils.assertEquals(4.0, func.getX(3));
        TestUtils.assertEquals(5.0, func.getX(4));
        
        System.out.println("✓ Insert maintains order");
    }

    private static void testInsertWithComposition() {
        System.out.println("Testing insert with function composition...");
        
        LinkedListTabulatedFunction func = new LinkedListTabulatedFunction(new double[0], new double[0]);
        func.insert(1.0, 1.0);
        func.insert(2.0, 4.0);
        func.insert(3.0, 9.0);
        
        // Создаём композицию с линейной функцией
        MathFunction linear = new LinearFunction(2.0, 1.0);
        MathFunction composed = new CompositeFunction(func, linear);
        
        // Проверяем, что композиция работает
        TestUtils.assertAlmostEquals(3.0, composed.apply(1.0)); // 2*1 + 1 = 3
        TestUtils.assertAlmostEquals(9.0, composed.apply(2.0)); // 2*4 + 1 = 9
        TestUtils.assertAlmostEquals(19.0, composed.apply(3.0)); // 2*9 + 1 = 19
        
        // Добавляем новый элемент
        func.insert(1.5, 2.25);
        
        // Проверяем, что композиция всё ещё работает
        TestUtils.assertAlmostEquals(5.5, composed.apply(1.5)); // 2*2.25 + 1 = 5.5
        
        System.out.println("✓ Insert with composition works");
    }

    private static void testInsertEdgeCases() {
        System.out.println("Testing insert edge cases...");
        
        // Тест с очень маленькими значениями
        LinkedListTabulatedFunction func1 = new LinkedListTabulatedFunction(new double[0], new double[0]);
        func1.insert(1e-10, 1e-20);
        TestUtils.assertEquals(1, func1.getCount());
        TestUtils.assertEquals(1e-10, func1.getX(0), 1e-15);
        TestUtils.assertEquals(1e-20, func1.getY(0), 1e-25);
        
        // Тест с очень большими значениями
        LinkedListTabulatedFunction func2 = new LinkedListTabulatedFunction(new double[0], new double[0]);
        func2.insert(1e10, 1e20);
        TestUtils.assertEquals(1, func2.getCount());
        TestUtils.assertEquals(1e10, func2.getX(0), 1e5);
        TestUtils.assertEquals(1e20, func2.getY(0), 1e15);
        
        // Тест с отрицательными значениями
        LinkedListTabulatedFunction func3 = new LinkedListTabulatedFunction(new double[0], new double[0]);
        func3.insert(-5.0, 25.0);
        func3.insert(-1.0, 1.0);
        func3.insert(-3.0, 9.0);
        
        TestUtils.assertEquals(3, func3.getCount());
        TestUtils.assertEquals(-5.0, func3.getX(0));
        TestUtils.assertEquals(-3.0, func3.getX(1));
        TestUtils.assertEquals(-1.0, func3.getX(2));
        
        // Тест с нулевыми значениями
        LinkedListTabulatedFunction func4 = new LinkedListTabulatedFunction(new double[0], new double[0]);
        func4.insert(0.0, 0.0);
        func4.insert(1.0, 1.0);
        func4.insert(-1.0, 1.0);
        
        TestUtils.assertEquals(3, func4.getCount());
        TestUtils.assertEquals(-1.0, func4.getX(0));
        TestUtils.assertEquals(0.0, func4.getX(1));
        TestUtils.assertEquals(1.0, func4.getX(2));
        
        System.out.println("✓ Insert edge cases work");
    }
}
