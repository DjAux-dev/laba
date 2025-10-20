package functions;

public class CompatibilityTest {
    public static void run() {
        testExistingTestCompatibility();
        testInterfaceCompliance();
        testBackwardCompatibility();
    }

    private static void testExistingTestCompatibility() {
        System.out.println("Testing compatibility with existing tests...");
        
        // Тест совместимости с CompositionWithTabulatedTest
        TabulatedFunction tf = new ArrayTabulatedFunction(
            new double[]{0.0, 1.0, 2.0}, new double[]{0.0, 1.0, 4.0}
        );
        MathFunction linear = new LinearFunction(2.0, -1.0);
        MathFunction composed = new CompositeFunction(tf, linear);
        TestUtils.assertAlmostEquals(4.0, composed.apply(1.5));
        
        MathFunction composed2 = new CompositeFunction(linear, tf);
        TestUtils.assertAlmostEquals(7.0, composed2.apply(2.0));
        
        // Тест с LinkedListTabulatedFunction
        TabulatedFunction tf2 = new LinkedListTabulatedFunction(
            new double[]{0.0, 1.0, 2.0}, new double[]{0.0, 1.0, 4.0}
        );
        MathFunction composed3 = new CompositeFunction(tf2, linear);
        TestUtils.assertAlmostEquals(4.0, composed3.apply(1.5));
        
        MathFunction composed4 = new CompositeFunction(linear, tf2);
        TestUtils.assertAlmostEquals(7.0, composed4.apply(2.0));
        
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
        TestUtils.assertEquals(0.0, tabFunc.getX(0));
        TestUtils.assertEquals(4.0, tabFunc.getY(2));
        
        // Проверяем, что это Insertable
        Insertable insertable = arrayFunc;
        insertable.insert(1.5, 2.25);
        TestUtils.assertEquals(4, tabFunc.getCount());
        
        // Аналогично для LinkedListTabulatedFunction
        LinkedListTabulatedFunction listFunc = new LinkedListTabulatedFunction(new SqrFunction(), 0.0, 2.0, 3);
        
        MathFunction mathFunc2 = listFunc;
        TestUtils.assertAlmostEquals(1.0, mathFunc2.apply(1.0));
        
        TabulatedFunction tabFunc2 = listFunc;
        TestUtils.assertEquals(3, tabFunc2.getCount());
        
        // Проверяем, что это Removable
        Removable removable = listFunc;
        removable.remove(1);
        TestUtils.assertEquals(2, tabFunc2.getCount());
        
        System.out.println("✓ Interface compliance verified");
    }

    private static void testBackwardCompatibility() {
        System.out.println("Testing backward compatibility...");
        
        // Тест, что старые функции работают с новыми табулированными функциями
        MathFunction oldFunction = new SqrFunction();
        ArrayTabulatedFunction newTabulated = new ArrayTabulatedFunction(oldFunction, 0.0, 2.0, 3);
        
        // Композиция старой функции с новой табулированной
        MathFunction composed1 = new CompositeFunction(oldFunction, newTabulated);
        TestUtils.assertAlmostEquals(1.0, composed1.apply(1.0)); // (1^2)^2 = 1
        
        // Композиция новой табулированной функции со старой
        MathFunction composed2 = new CompositeFunction(newTabulated, oldFunction);
        TestUtils.assertAlmostEquals(1.0, composed2.apply(1.0)); // (1^2)^2 = 1
        
        // Тест с LinkedListTabulatedFunction
        LinkedListTabulatedFunction newTabulated2 = new LinkedListTabulatedFunction(oldFunction, 0.0, 2.0, 3);
        
        MathFunction composed3 = new CompositeFunction(oldFunction, newTabulated2);
        TestUtils.assertAlmostEquals(1.0, composed3.apply(1.0));
        
        MathFunction composed4 = new CompositeFunction(newTabulated2, oldFunction);
        TestUtils.assertAlmostEquals(1.0, composed4.apply(1.0));
        
        // Тест, что численные методы работают с новыми табулированными функциями
        NewtonMethodFunction newton = new NewtonMethodFunction(newTabulated, 1.0, 1e-6, 100);
        double root = newton.apply(0.0);
        TestUtils.assertAlmostEquals(0.0, root, 1e-3);
        
        RungeKuttaFunction rk = new RungeKuttaFunction(newTabulated2, 0.0, 0.0, 0.1);
        double solution = rk.apply(1.0);
        TestUtils.assertAlmostEquals(0.5, solution, 1e-2);
        
        System.out.println("✓ Backward compatibility verified");
    }
}
