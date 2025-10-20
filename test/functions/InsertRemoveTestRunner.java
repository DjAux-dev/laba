package functions;

public class InsertRemoveTestRunner {
    public static void main(String[] args) {
        System.out.println("=== Running Insert/Remove Method Tests ===");
        
        try {
            System.out.println("\n1. Testing LinkedListTabulatedFunction.insert()...");
            LinkedListInsertTest.run();
            System.out.println("✓ LinkedList insert tests passed");
            
            System.out.println("\n2. Testing ArrayTabulatedFunction.remove()...");
            ArrayRemoveTest.run();
            System.out.println("✓ Array remove tests passed");
            
            System.out.println("\n3. Testing integration with existing functionality...");
            testIntegration();
            System.out.println("✓ Integration tests passed");
            
            System.out.println("\n4. Testing compatibility...");
            InsertRemoveCompatibilityTest.run();
            System.out.println("✓ Compatibility tests passed");
            
            System.out.println("\n=== All Insert/Remove Tests Completed Successfully! ===");
            
        } catch (Exception e) {
            System.out.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private static void testIntegration() {
        // Тест интеграции insert и remove с композициями функций
        LinkedListTabulatedFunction listFunc = new LinkedListTabulatedFunction(new double[0], new double[0]);
        ArrayTabulatedFunction arrayFunc = new ArrayTabulatedFunction(new SqrFunction(), 0.0, 3.0, 4);
        
        // Добавляем элементы в список
        listFunc.insert(1.0, 1.0);
        listFunc.insert(2.0, 4.0);
        listFunc.insert(3.0, 9.0);
        
        // Создаём композиции
        MathFunction linear = new LinearFunction(2.0, 1.0);
        MathFunction composedList = new CompositeFunction(listFunc, linear);
        MathFunction composedArray = new CompositeFunction(arrayFunc, linear);
        
        // Проверяем работу композиций
        TestUtils.assertAlmostEquals(3.0, composedList.apply(1.0)); // 2*1 + 1 = 3
        TestUtils.assertAlmostEquals(9.0, composedList.apply(2.0)); // 2*4 + 1 = 9
        TestUtils.assertAlmostEquals(19.0, composedList.apply(3.0)); // 2*9 + 1 = 19
        
        TestUtils.assertAlmostEquals(1.0, composedArray.apply(0.0)); // 2*0 + 1 = 1
        TestUtils.assertAlmostEquals(3.0, composedArray.apply(1.0)); // 2*1 + 1 = 3
        TestUtils.assertAlmostEquals(9.0, composedArray.apply(2.0)); // 2*4 + 1 = 9
        TestUtils.assertAlmostEquals(19.0, composedArray.apply(3.0)); // 2*9 + 1 = 19
        
        // Удаляем элемент из массива
        arrayFunc.remove(1); // Удаляем x=1.0, y=1.0
        
        // Проверяем, что композиция всё ещё работает
        TestUtils.assertAlmostEquals(1.0, composedArray.apply(0.0)); // 2*0 + 1 = 1
        TestUtils.assertAlmostEquals(9.0, composedArray.apply(2.0)); // 2*4 + 1 = 9
        TestUtils.assertAlmostEquals(19.0, composedArray.apply(3.0)); // 2*9 + 1 = 19
        
        // Добавляем элемент в список
        listFunc.insert(1.5, 2.25);
        
        // Проверяем, что композиция работает с новым элементом
        TestUtils.assertAlmostEquals(5.5, composedList.apply(1.5)); // 2*2.25 + 1 = 5.5
        
        System.out.println("✓ Integration tests completed");
    }
}
