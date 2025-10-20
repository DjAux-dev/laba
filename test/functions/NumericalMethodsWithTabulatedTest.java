package functions;

public class NumericalMethodsWithTabulatedTest {
    public static void run() {
        testNewtonMethodWithTabulated();
        testRungeKuttaWithTabulated();
        testNumericalMethodsChains();
        testErrorHandling();
    }

    private static void testNewtonMethodWithTabulated() {
        System.out.println("Testing Newton method with tabulated functions...");
        
        // Создаём табулированную функцию для f(x) = x^2 - 4 (корни: x = ±2)
        MathFunction source = new MathFunction() {
            @Override
            public double apply(double x) {
                return x * x - 4.0;
            }
        };
        
        ArrayTabulatedFunction tabulated = new ArrayTabulatedFunction(source, -3.0, 3.0, 7);
        
        // Тест метода Ньютона с табулированной функцией
        NewtonMethodFunction newton = new NewtonMethodFunction(tabulated, 1.0, 1e-6, 100);
        
        // Проверяем, что метод находит корень близко к 2
        double root = newton.apply(0.0); // Начальное приближение не важно для этого теста
        TestUtils.assertAlmostEquals(2.0, root, 1e-3);
        
        // Тест с LinkedListTabulatedFunction
        LinkedListTabulatedFunction tabulated2 = new LinkedListTabulatedFunction(source, -3.0, 3.0, 7);
        NewtonMethodFunction newton2 = new NewtonMethodFunction(tabulated2, 1.0, 1e-6, 100);
        
        double root2 = newton2.apply(0.0);
        TestUtils.assertAlmostEquals(2.0, root2, 1e-3);
        
        // Тест композиции: NewtonMethod + TabulatedFunction
        MathFunction linear = new LinearFunction(2.0, 1.0);
        MathFunction composed = new CompositeFunction(newton, linear);
        
        // Если newton возвращает корень ≈ 2, то composed(0) ≈ linear(2) = 2*2 + 1 = 5
        double result = composed.apply(0.0);
        TestUtils.assertAlmostEquals(5.0, result, 1e-3);
    }

    private static void testRungeKuttaWithTabulated() {
        System.out.println("Testing Runge-Kutta method with tabulated functions...");
        
        // Создаём табулированную функцию для дифференциального уравнения dy/dx = x
        // Решение: y = x^2/2 + C
        MathFunction derivative = new MathFunction() {
            @Override
            public double apply(double x) {
                return x; // dy/dx = x
            }
        };
        
        ArrayTabulatedFunction tabulatedDerivative = new ArrayTabulatedFunction(derivative, 0.0, 2.0, 5);
        
        // Тест метода Рунге-Кутты с табулированной производной
        RungeKuttaFunction rk = new RungeKuttaFunction(tabulatedDerivative, 0.0, 0.0, 0.1);
        
        // Проверяем решение в точке x = 1: y(1) = 1^2/2 = 0.5
        double solution = rk.apply(1.0);
        TestUtils.assertAlmostEquals(0.5, solution, 1e-2);
        
        // Проверяем решение в точке x = 2: y(2) = 2^2/2 = 2.0
        double solution2 = rk.apply(2.0);
        TestUtils.assertAlmostEquals(2.0, solution2, 1e-2);
        
        // Тест с LinkedListTabulatedFunction
        LinkedListTabulatedFunction tabulatedDerivative2 = new LinkedListTabulatedFunction(derivative, 0.0, 2.0, 5);
        RungeKuttaFunction rk2 = new RungeKuttaFunction(tabulatedDerivative2, 0.0, 0.0, 0.1);
        
        double solution3 = rk2.apply(1.0);
        TestUtils.assertAlmostEquals(0.5, solution3, 1e-2);
        
        // Тест композиции: RungeKutta + LinearFunction
        MathFunction linear = new LinearFunction(3.0, 2.0);
        MathFunction composed = new CompositeFunction(rk, linear);
        
        // Если rk(1) ≈ 0.5, то composed(1) ≈ linear(0.5) = 3*0.5 + 2 = 3.5
        double result = composed.apply(1.0);
        TestUtils.assertAlmostEquals(3.5, result, 1e-2);
    }

    private static void testNumericalMethodsChains() {
        System.out.println("Testing numerical methods in composition chains...");
        
        // Создаём сложную цепочку: Tabulated -> Newton -> RungeKutta -> Linear
        MathFunction source = new MathFunction() {
            @Override
            public double apply(double x) {
                return x * x - 1.0; // Корни: x = ±1
            }
        };
        
        ArrayTabulatedFunction tabulated = new ArrayTabulatedFunction(source, -2.0, 2.0, 5);
        NewtonMethodFunction newton = new NewtonMethodFunction(tabulated, 0.5, 1e-6, 100);
        
        // Производная для Runge-Kutta: dy/dx = y (решение: y = C*e^x)
        MathFunction derivative = new MathFunction() {
            @Override
            public double apply(double x) {
                return x; // Упрощённая производная
            }
        };
        
        LinkedListTabulatedFunction tabulatedDerivative = new LinkedListTabulatedFunction(derivative, 0.0, 1.0, 3);
        RungeKuttaFunction rk = new RungeKuttaFunction(tabulatedDerivative, 0.0, 1.0, 0.1);
        
        MathFunction linear = new LinearFunction(2.0, 3.0);
        
        // Создаём цепочку: newton -> rk -> linear
        MathFunction chain = new CompositeFunction(newton, new CompositeFunction(rk, linear));
        
        // Тестируем цепочку
        double result = chain.apply(0.0);
        // newton(0) ≈ 1 (корень), rk(1) ≈ решение дифура, linear(rk(1)) ≈ результат
        
        // Проверяем, что результат разумен (не NaN, не Infinity)
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            throw new AssertionError("Numerical method chain produced invalid result: " + result);
        }
        
        // Тест обратной цепочки: Linear -> RungeKutta -> Newton -> Tabulated
        MathFunction reverseChain = new CompositeFunction(linear, new CompositeFunction(rk, newton));
        
        double reverseResult = reverseChain.apply(0.0);
        if (Double.isNaN(reverseResult) || Double.isInfinite(reverseResult)) {
            throw new AssertionError("Reverse numerical method chain produced invalid result: " + reverseResult);
        }
    }

    private static void testErrorHandling() {
        System.out.println("Testing error handling in numerical methods...");
        
        // Тест с функцией, не имеющей корней в заданном диапазоне
        MathFunction noRoots = new MathFunction() {
            @Override
            public double apply(double x) {
                return x * x + 1.0; // Всегда положительна, корней нет
            }
        };
        
        ArrayTabulatedFunction tabulatedNoRoots = new ArrayTabulatedFunction(noRoots, 0.0, 2.0, 3);
        NewtonMethodFunction newton = new NewtonMethodFunction(tabulatedNoRoots, 1.0, 1e-6, 10); // Малый лимит итераций
        
        // Метод Ньютона должен обработать отсутствие корней корректно
        try {
            double result = newton.apply(0.0);
            // Если метод не выбросил исключение, результат должен быть разумным
            if (Double.isNaN(result) || Double.isInfinite(result)) {
                System.out.println("Newton method handled no-roots case gracefully");
            }
        } catch (Exception e) {
            System.out.println("Newton method threw exception for no-roots case: " + e.getMessage());
        }
        
        // Тест с очень маленьким шагом в Runge-Kutta
        MathFunction derivative = new ConstantFunction(1.0);
        LinkedListTabulatedFunction tabulatedDerivative = new LinkedListTabulatedFunction(derivative, 0.0, 1.0, 2);
        RungeKuttaFunction rk = new RungeKuttaFunction(tabulatedDerivative, 0.0, 0.0, 1e-10);
        
        try {
            double result = rk.apply(1.0);
            if (Double.isNaN(result) || Double.isInfinite(result)) {
                System.out.println("Runge-Kutta handled very small step gracefully");
            }
        } catch (Exception e) {
            System.out.println("Runge-Kutta threw exception for very small step: " + e.getMessage());
        }
        
        // Тест с композицией численных методов и обычных функций
        MathFunction source = new SqrFunction();
        ArrayTabulatedFunction tabulated = new ArrayTabulatedFunction(source, 0.0, 2.0, 3);
        NewtonMethodFunction newton2 = new NewtonMethodFunction(tabulated, 1.0, 1e-6, 100);
        
        MathFunction linear = new LinearFunction(0.0, 1.0); // f(x) = 1
        MathFunction composed = new CompositeFunction(newton2, linear);
        
        // Проверяем, что композиция работает корректно
        try {
            double result = composed.apply(0.0);
            if (!Double.isNaN(result) && !Double.isInfinite(result)) {
                System.out.println("Composition of numerical method with regular function works");
            }
        } catch (Exception e) {
            System.out.println("Composition failed: " + e.getMessage());
        }
    }
}
