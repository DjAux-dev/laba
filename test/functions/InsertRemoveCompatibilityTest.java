package functions;

public class InsertRemoveCompatibilityTest {
    public static void run() {
        testExistingTestCompatibility();
        testInterfaceCompliance();
        testCrossCompatibility();
    }

    private static void testExistingTestCompatibility() {
        System.out.println("Testing compatibility with existing tests...");
        
        // Тест совместимости с ArrayInsertTest (если существует)
        // Проверяем, что ArrayTabulatedFunction всё ещё работает как раньше
        double[] xValues = {0.0, 1.0, 2.0};
        double[] yValues = {0.0, 1.0, 4.0};
        ArrayTabulatedFunction arrayFunc = new ArrayTabulatedFunction(xValues, yValues);
        
        // Проверяем базовую функциональность
        TestUtils.assertEquals(3, arrayFunc.getCount());
        TestUtils.assertEquals(0.0, arrayFunc.getX(0));
        TestUtils.assertEquals(4.0, arrayFunc.getY(2));
        
        // Проверяем, что insert всё ещё работает
        arrayFunc.insert(1.5, 2.25);
        TestUtils.assertEquals(4, arrayFunc.getCount());
        TestUtils.assertEquals(1.5, arrayFunc.getX(1));
        TestUtils.assertEquals(2.25, arrayFunc.getY(1));
        
        // Проверяем, что remove работает
        arrayFunc.remove(1);
        TestUtils.assertEquals(3, arrayFunc.getCount());
        TestUtils.assertEquals(0.0, arrayFunc.getX(0));
        TestUtils.assertEquals(1.0, arrayFunc.getX(1));
        TestUtils.assertEquals(2.0, arrayFunc.getX(2));
        
        // Аналогично для LinkedListTabulatedFunction
        LinkedListTabulatedFunction listFunc = new LinkedListTabulatedFunction(xValues, yValues);
        
        TestUtils.assertEquals(3, listFunc.getCount());
        TestUtils.assertEquals(0.0, listFunc.getX(0));
        TestUtils.assertEquals(4.0, listFunc.getY(2));
        
        // Проверяем, что insert работает
        listFunc.insert(1.5, 2.25);
        TestUtils.assertEquals(4, listFunc.getCount());
        
        // Проверяем, что remove работает
        listFunc.remove(1);
        TestUtils.assertEquals(3, listFunc.getCount());
        
        System.out.println("✓ Existing test compatibility verified");
    }

    private static void testInterfaceCompliance() {
        System.out.println("Testing interface compliance...");
        
        // Тест, что ArrayTabulatedFunction реализует все необходимые интерфейсы
        ArrayTabulatedFunction arrayFunc = new ArrayTabulatedFunction(new SqrFunction(), 0.0, 2.0, 3);
        
        // Проверяем, что это MathFunction
        MathFunction mathFunc = arrayFunc;
        TestUtils.assertAlmostEquals(1.0, mathFunc.apply(1.0));
        
        // Проверяем, что это TabulatedFunction
        TabulatedFunction tabFunc = arrayFunc;
        TestUtils.assertEquals(3, tabFunc.getCount());
        
        // Проверяем, что это Insertable
        Insertable insertable = arrayFunc;
        insertable.insert(1.5, 2.25);
        TestUtils.assertEquals(4, tabFunc.getCount());
        
        // Проверяем, что это Removable
        Removable removable = arrayFunc;
        removable.remove(1);
        TestUtils.assertEquals(3, tabFunc.getCount());
        
        // Аналогично для LinkedListTabulatedFunction
        LinkedListTabulatedFunction listFunc = new LinkedListTabulatedFunction(new SqrFunction(), 0.0, 2.0, 3);
        
        MathFunction mathFunc2 = listFunc;
        TestUtils.assertAlmostEquals(1.0, mathFunc2.apply(1.0));
        
        TabulatedFunction tabFunc2 = listFunc;
        TestUtils.assertEquals(3, tabFunc2.getCount());
        
        // Проверяем, что это Insertable
        Insertable insertable2 = listFunc;
        insertable2.insert(1.5, 2.25);
        TestUtils.assertEquals(4, tabFunc2.getCount());
        
        // Проверяем, что это Removable
        Removable removable2 = listFunc;
        removable2.remove(1);
        TestUtils.assertEquals(3, tabFunc2.getCount());
        
        System.out.println("✓ Interface compliance verified");
    }

    private static void testCrossCompatibility() {
        System.out.println("Testing cross compatibility...");
        
        // Тест совместимости между ArrayTabulatedFunction и LinkedListTabulatedFunction
        ArrayTabulatedFunction arrayFunc = new ArrayTabulatedFunction(new SqrFunction(), 0.0, 2.0, 3);
        LinkedListTabulatedFunction listFunc = new LinkedListTabulatedFunction(new SqrFunction(), 0.0, 2.0, 3);
        
        // Создаём композиции
        MathFunction linear = new LinearFunction(2.0, 1.0);
        MathFunction composedArray = new CompositeFunction(arrayFunc, linear);
        MathFunction composedList = new CompositeFunction(listFunc, linear);
        
        // Проверяем, что результаты совпадают
        TestUtils.assertAlmostEquals(composedArray.apply(0.0), composedList.apply(0.0));
        TestUtils.assertAlmostEquals(composedArray.apply(1.0), composedList.apply(1.0));
        TestUtils.assertAlmostEquals(composedArray.apply(2.0), composedList.apply(2.0));
        
        // Добавляем элементы в обе функции
        arrayFunc.insert(1.5, 2.25);
        listFunc.insert(1.5, 2.25);
        
        // Проверяем, что результаты всё ещё совпадают
        TestUtils.assertAlmostEquals(composedArray.apply(1.5), composedList.apply(1.5));
        
        // Удаляем элементы из обеих функций
        arrayFunc.remove(1);
        listFunc.remove(1);
        
        // Проверяем, что результаты всё ещё совпадают
        TestUtils.assertAlmostEquals(composedArray.apply(0.0), composedList.apply(0.0));
        TestUtils.assertAlmostEquals(composedArray.apply(2.0), composedList.apply(2.0));
        
        // Тест с численными методами
        NewtonMethodFunction newtonArray = new NewtonMethodFunction(arrayFunc, 1.0, 1e-6, 100);
        NewtonMethodFunction newtonList = new NewtonMethodFunction(listFunc, 1.0, 1e-6, 100);
        
        // Проверяем, что численные методы работают с обеими реализациями
        double rootArray = newtonArray.apply(0.0);
        double rootList = newtonList.apply(0.0);
        
        TestUtils.assertAlmostEquals(rootArray, rootList, 1e-3);
        
        System.out.println("✓ Cross compatibility verified");
    }
}
