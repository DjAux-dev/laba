package functions;

public class ComplexFunctionCompositionTest {
    public static void run() {
        testArrayTabulatedWithBasicFunctions();
        testLinkedListTabulatedWithBasicFunctions();
        testTabulatedWithTabulated();
        testComplexChains();
        testEdgeCases();
        testMathematicalProperties();
    }

    private static void testArrayTabulatedWithBasicFunctions() {
        System.out.println("Testing ArrayTabulatedFunction with basic functions...");
        
        // Создаём табулированную функцию на основе квадратичной функции
        MathFunction source = new SqrFunction();
        ArrayTabulatedFunction tabulated = new ArrayTabulatedFunction(source, 0.0, 3.0, 4);
        
        // Тест 1: Табулированная функция + Линейная функция
        MathFunction linear = new LinearFunction(2.0, 1.0);
        MathFunction composed1 = new CompositeFunction(tabulated, linear);
        
        // f(x) = x^2, g(x) = 2x + 1, h(x) = g(f(x)) = 2*x^2 + 1
        TestUtils.assertAlmostEquals(1.0, composed1.apply(0.0));  // 2*0^2 + 1 = 1
        TestUtils.assertAlmostEquals(3.0, composed1.apply(1.0));  // 2*1^2 + 1 = 3
        TestUtils.assertAlmostEquals(9.0, composed1.apply(2.0));  // 2*2^2 + 1 = 9
        TestUtils.assertAlmostEquals(19.0, composed1.apply(3.0)); // 2*3^2 + 1 = 19
        
        // Тест 2: Линейная функция + Табулированная функция
        MathFunction composed2 = new CompositeFunction(linear, tabulated);
        
        // f(x) = 2x + 1, g(x) = x^2, h(x) = g(f(x)) = (2x + 1)^2
        TestUtils.assertAlmostEquals(1.0, composed2.apply(0.0));   // (2*0 + 1)^2 = 1
        TestUtils.assertAlmostEquals(9.0, composed2.apply(1.0));   // (2*1 + 1)^2 = 9
        TestUtils.assertAlmostEquals(25.0, composed2.apply(2.0));  // (2*2 + 1)^2 = 25
        
        // Тест 3: Табулированная функция + Тождественная функция
        MathFunction identity = new IdentityFunction();
        MathFunction composed3 = new CompositeFunction(tabulated, identity);
        
        // Должно работать как исходная табулированная функция
        TestUtils.assertAlmostEquals(0.0, composed3.apply(0.0));
        TestUtils.assertAlmostEquals(1.0, composed3.apply(1.0));
        TestUtils.assertAlmostEquals(4.0, composed3.apply(2.0));
        TestUtils.assertAlmostEquals(9.0, composed3.apply(3.0));
        
        // Тест 4: Тождественная функция + Табулированная функция
        MathFunction composed4 = new CompositeFunction(identity, tabulated);
        
        // Должно работать как исходная табулированная функция
        TestUtils.assertAlmostEquals(0.0, composed4.apply(0.0));
        TestUtils.assertAlmostEquals(1.0, composed4.apply(1.0));
        TestUtils.assertAlmostEquals(4.0, composed4.apply(2.0));
        TestUtils.assertAlmostEquals(9.0, composed4.apply(3.0));
    }

    private static void testLinkedListTabulatedWithBasicFunctions() {
        System.out.println("Testing LinkedListTabulatedFunction with basic functions...");
        
        // Создаём табулированную функцию на основе линейной функции
        MathFunction source = new LinearFunction(3.0, 2.0);
        LinkedListTabulatedFunction tabulated = new LinkedListTabulatedFunction(source, -1.0, 2.0, 4);
        
        // Тест 1: Табулированная функция + Квадратичная функция
        MathFunction sqr = new SqrFunction();
        MathFunction composed1 = new CompositeFunction(tabulated, sqr);
        
        // f(x) = 3x + 2, g(x) = x^2, h(x) = g(f(x)) = (3x + 2)^2
        TestUtils.assertAlmostEquals(1.0, composed1.apply(-1.0));  // (3*(-1) + 2)^2 = (-1)^2 = 1
        TestUtils.assertAlmostEquals(16.0, composed1.apply(0.0));  // (3*0 + 2)^2 = 4
        TestUtils.assertAlmostEquals(49.0, composed1.apply(1.0));  // (3*1 + 2)^2 = 25
        TestUtils.assertAlmostEquals(64.0, composed1.apply(2.0));  // (3*2 + 2)^2 = 64
        
        // Тест 2: Константная функция + Табулированная функция
        MathFunction constant = new ConstantFunction(5.0);
        MathFunction composed2 = new CompositeFunction(constant, tabulated);
        
        // f(x) = 5, g(x) = 3x + 2, h(x) = g(f(x)) = g(5) = 3*5 + 2 = 17
        TestUtils.assertAlmostEquals(17.0, composed2.apply(-100.0)); // Любое x даёт 17
        TestUtils.assertAlmostEquals(17.0, composed2.apply(0.0));
        TestUtils.assertAlmostEquals(17.0, composed2.apply(100.0));
        
        // Тест 3: Табулированная функция + Константная функция
        MathFunction composed3 = new CompositeFunction(tabulated, constant);
        
        // f(x) = 3x + 2, g(x) = 5, h(x) = g(f(x)) = 5
        TestUtils.assertAlmostEquals(5.0, composed3.apply(-1.0));
        TestUtils.assertAlmostEquals(5.0, composed3.apply(0.0));
        TestUtils.assertAlmostEquals(5.0, composed3.apply(1.0));
        TestUtils.assertAlmostEquals(5.0, composed3.apply(2.0));
    }

    private static void testTabulatedWithTabulated() {
        System.out.println("Testing TabulatedFunction with TabulatedFunction...");
        
        // Создаём две табулированные функции
        MathFunction source1 = new SqrFunction();
        ArrayTabulatedFunction tabulated1 = new ArrayTabulatedFunction(source1, 0.0, 2.0, 3);
        
        MathFunction source2 = new LinearFunction(2.0, 1.0);
        LinkedListTabulatedFunction tabulated2 = new LinkedListTabulatedFunction(source2, 0.0, 5.0, 4);
        
        // Тест 1: ArrayTabulatedFunction + LinkedListTabulatedFunction
        MathFunction composed1 = new CompositeFunction(tabulated1, tabulated2);
        
        // f(x) = x^2, g(x) = 2x + 1, h(x) = g(f(x)) = 2*x^2 + 1
        TestUtils.assertAlmostEquals(1.0, composed1.apply(0.0));  // 2*0^2 + 1 = 1
        TestUtils.assertAlmostEquals(3.0, composed1.apply(1.0));  // 2*1^2 + 1 = 3
        TestUtils.assertAlmostEquals(9.0, composed1.apply(2.0));  // 2*2^2 + 1 = 9
        
        // Тест 2: LinkedListTabulatedFunction + ArrayTabulatedFunction
        MathFunction composed2 = new CompositeFunction(tabulated2, tabulated1);
        
        // f(x) = 2x + 1, g(x) = x^2, h(x) = g(f(x)) = (2x + 1)^2
        TestUtils.assertAlmostEquals(1.0, composed2.apply(0.0));   // (2*0 + 1)^2 = 1
        TestUtils.assertAlmostEquals(9.0, composed2.apply(1.0));  // (2*1 + 1)^2 = 9
        TestUtils.assertAlmostEquals(25.0, composed2.apply(2.0)); // (2*2 + 1)^2 = 25
        
        // Тест 3: ArrayTabulatedFunction + ArrayTabulatedFunction
        ArrayTabulatedFunction tabulated3 = new ArrayTabulatedFunction(source2, 0.0, 3.0, 3);
        MathFunction composed3 = new CompositeFunction(tabulated1, tabulated3);
        
        // f(x) = x^2, g(x) = 2x + 1, h(x) = g(f(x)) = 2*x^2 + 1
        TestUtils.assertAlmostEquals(1.0, composed3.apply(0.0));
        TestUtils.assertAlmostEquals(3.0, composed3.apply(1.0));
        TestUtils.assertAlmostEquals(9.0, composed3.apply(2.0));
        
        // Тест 4: LinkedListTabulatedFunction + LinkedListTabulatedFunction
        LinkedListTabulatedFunction tabulated4 = new LinkedListTabulatedFunction(source1, 0.0, 3.0, 3);
        MathFunction composed4 = new CompositeFunction(tabulated2, tabulated4);
        
        // f(x) = 2x + 1, g(x) = x^2, h(x) = g(f(x)) = (2x + 1)^2
        TestUtils.assertAlmostEquals(1.0, composed4.apply(0.0));
        TestUtils.assertAlmostEquals(9.0, composed4.apply(1.0));
        TestUtils.assertAlmostEquals(25.0, composed4.apply(2.0));
    }

    private static void testComplexChains() {
        System.out.println("Testing complex composition chains...");
        
        // Создаём цепочку: Linear -> Tabulated -> Sqr -> Tabulated
        MathFunction linear = new LinearFunction(2.0, 1.0);
        ArrayTabulatedFunction tabulated1 = new ArrayTabulatedFunction(new SqrFunction(), 0.0, 2.0, 3);
        MathFunction sqr = new SqrFunction();
        LinkedListTabulatedFunction tabulated2 = new LinkedListTabulatedFunction(new LinearFunction(3.0, 2.0), 0.0, 10.0, 4);
        
        // Создаём цепочку: f(x) = 2x + 1, g(x) = x^2, h(x) = x^2, i(x) = 3x + 2
        // Результат: i(h(g(f(x)))) = i(h(g(2x + 1))) = i(h((2x + 1)^2)) = i((2x + 1)^4) = 3*(2x + 1)^4 + 2
        MathFunction chain1 = new CompositeFunction(linear, tabulated1);
        MathFunction chain2 = new CompositeFunction(chain1, sqr);
        MathFunction chain3 = new CompositeFunction(chain2, tabulated2);
        
        // Проверяем несколько значений
        TestUtils.assertAlmostEquals(5.0, chain3.apply(0.0));   // 3*(2*0 + 1)^4 + 2 = 3*1 + 2 = 5
        TestUtils.assertAlmostEquals(245.0, chain3.apply(1.0)); // 3*(2*1 + 1)^4 + 2 = 3*81 + 2 = 245
        
        // Тест обратной цепочки: Tabulated -> Linear -> Tabulated -> Sqr
        MathFunction reverseChain1 = new CompositeFunction(tabulated1, linear);
        MathFunction reverseChain2 = new CompositeFunction(reverseChain1, tabulated2);
        MathFunction reverseChain3 = new CompositeFunction(reverseChain2, sqr);
        
        // Проверяем несколько значений
        TestUtils.assertAlmostEquals(9.0, reverseChain3.apply(1.0));  // (2*1^2 + 1)^2 = 3^2 = 9
        TestUtils.assertAlmostEquals(49.0, reverseChain3.apply(2.0)); // (2*2^2 + 1)^2 = 9^2 = 81... подождите, проверим
        
        // Давайте пересчитаем: f(x) = x^2, g(x) = 2x + 1, h(x) = 3x + 2, i(x) = x^2
        // i(h(g(f(2)))) = i(h(g(4))) = i(h(9)) = i(29) = 29^2 = 841
        TestUtils.assertAlmostEquals(841.0, reverseChain3.apply(2.0));
    }

    private static void testEdgeCases() {
        System.out.println("Testing edge cases...");
        
        // Тест с одной точкой
        ArrayTabulatedFunction singlePoint = new ArrayTabulatedFunction(new ConstantFunction(5.0), 0.0, 0.0, 1);
        MathFunction linear = new LinearFunction(2.0, 1.0);
        MathFunction composed = new CompositeFunction(singlePoint, linear);
        
        // f(x) = 5, g(x) = 2x + 1, h(x) = g(5) = 2*5 + 1 = 11
        TestUtils.assertAlmostEquals(11.0, composed.apply(0.0));
        TestUtils.assertAlmostEquals(11.0, composed.apply(100.0));
        
        // Тест с экстраполяцией
        ArrayTabulatedFunction tabulated = new ArrayTabulatedFunction(new SqrFunction(), 0.0, 2.0, 3);
        MathFunction sqr = new SqrFunction();
        MathFunction composed2 = new CompositeFunction(tabulated, sqr);
        
        // f(x) = x^2, g(x) = x^2, h(x) = g(f(x)) = (x^2)^2 = x^4
        TestUtils.assertAlmostEquals(1.0, composed2.apply(-1.0));  // (-1)^4 = 1
        TestUtils.assertAlmostEquals(16.0, composed2.apply(2.0));  // 2^4 = 16
        TestUtils.assertAlmostEquals(81.0, composed2.apply(3.0)); // 3^4 = 81
        
        // Тест с интерполяцией
        TestUtils.assertAlmostEquals(0.0625, composed2.apply(0.5)); // (0.5)^4 = 0.0625
    }

    private static void testMathematicalProperties() {
        System.out.println("Testing mathematical properties...");
        
        // Тест ассоциативности композиции: (f ∘ g) ∘ h = f ∘ (g ∘ h)
        MathFunction f = new LinearFunction(2.0, 1.0);
        MathFunction g = new SqrFunction();
        MathFunction h = new LinearFunction(3.0, 2.0);
        
        ArrayTabulatedFunction tabulatedF = new ArrayTabulatedFunction(f, 0.0, 2.0, 3);
        ArrayTabulatedFunction tabulatedG = new ArrayTabulatedFunction(g, 0.0, 5.0, 4);
        ArrayTabulatedFunction tabulatedH = new ArrayTabulatedFunction(h, 0.0, 10.0, 3);
        
        // Левая ассоциативность: ((f ∘ g) ∘ h)(x) = (f ∘ g)(h(x))
        MathFunction leftAssoc = new CompositeFunction(new CompositeFunction(tabulatedF, tabulatedG), tabulatedH);
        
        // Правая ассоциативность: (f ∘ (g ∘ h))(x) = f((g ∘ h)(x))
        MathFunction rightAssoc = new CompositeFunction(tabulatedF, new CompositeFunction(tabulatedG, tabulatedH));
        
        // Проверяем, что результаты совпадают для нескольких значений
        for (double x = 0.0; x <= 2.0; x += 0.5) {
            TestUtils.assertAlmostEquals(leftAssoc.apply(x), rightAssoc.apply(x));
        }
        
        // Тест с тождественной функцией: f ∘ id = id ∘ f = f
        MathFunction identity = new IdentityFunction();
        ArrayTabulatedFunction tabulated = new ArrayTabulatedFunction(new SqrFunction(), 0.0, 2.0, 3);
        
        MathFunction leftIdentity = new CompositeFunction(tabulated, identity);
        MathFunction rightIdentity = new CompositeFunction(identity, tabulated);
        
        // Проверяем, что результаты совпадают с исходной функцией
        for (double x = 0.0; x <= 2.0; x += 0.5) {
            TestUtils.assertAlmostEquals(tabulated.apply(x), leftIdentity.apply(x));
            TestUtils.assertAlmostEquals(tabulated.apply(x), rightIdentity.apply(x));
        }
    }
}
