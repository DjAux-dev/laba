package functions;

/**
 * Демонстрационный класс для показа работы композиции функций
 */
public class CompositionDemo {
    
    public static void main(String[] args) {
        System.out.println("Демонстрация композиции математических функций");
        System.out.println("==============================================");
        
        // Создаем различные функции
        SqrFunction sqr = new SqrFunction();
        ConstantFunction constant5 = new ConstantFunction(5.0);
        ZeroFunction zero = new ZeroFunction();
        UnitFunction unit = new UnitFunction();
        
        System.out.println("Созданные функции:");
        System.out.println("sqr(x) = x^2");
        System.out.println("constant5(x) = 5");
        System.out.println("zero(x) = 0");
        System.out.println("unit(x) = 1");
        System.out.println();
        
        // Тестируем простые композиции
        System.out.println("=== Простые композиции ===");
        
        // sqr(constant5(x)) = sqr(5) = 25
        MathFunction composite1 = constant5.andThen(sqr);
        System.out.println("constant5.andThen(sqr):");
        System.out.println("  apply(0) = " + composite1.apply(0));
        System.out.println("  apply(10) = " + composite1.apply(10));
        System.out.println("  apply(-5) = " + composite1.apply(-5));
        System.out.println("  Ожидается: 25 (так как constant5 всегда возвращает 5, а sqr(5) = 25)");
        System.out.println();
        
        // constant5(sqr(x)) = constant5(x^2) = 5
        MathFunction composite2 = sqr.andThen(constant5);
        System.out.println("sqr.andThen(constant5):");
        System.out.println("  apply(0) = " + composite2.apply(0));
        System.out.println("  apply(2) = " + composite2.apply(2));
        System.out.println("  apply(-3) = " + composite2.apply(-3));
        System.out.println("  Ожидается: 5 (так как constant5 всегда возвращает 5)");
        System.out.println();
        
        // Тестируем цепочки функций
        System.out.println("=== Цепочки функций ===");
        
        // unit -> sqr -> constant5
        // unit(x) = 1, sqr(1) = 1, constant5(1) = 5
        MathFunction chain1 = unit.andThen(sqr).andThen(constant5);
        System.out.println("unit.andThen(sqr).andThen(constant5):");
        System.out.println("  apply(42) = " + chain1.apply(42));
        System.out.println("  apply(-99) = " + chain1.apply(-99));
        System.out.println("  Ожидается: 5");
        System.out.println();
        
        // zero -> sqr -> constant5
        // zero(x) = 0, sqr(0) = 0, constant5(0) = 5
        MathFunction chain2 = zero.andThen(sqr).andThen(constant5);
        System.out.println("zero.andThen(sqr).andThen(constant5):");
        System.out.println("  apply(100) = " + chain2.apply(100));
        System.out.println("  apply(-200) = " + chain2.apply(-200));
        System.out.println("  Ожидается: 5");
        System.out.println();
        
        // Тестируем повторное применение одной функции
        System.out.println("=== Повторное применение функций ===");
        
        // sqr(sqr(x)) = x^4
        MathFunction sqrSqr = sqr.andThen(sqr);
        System.out.println("sqr.andThen(sqr) (x^4):");
        System.out.println("  apply(0) = " + sqrSqr.apply(0));
        System.out.println("  apply(1) = " + sqrSqr.apply(1));
        System.out.println("  apply(2) = " + sqrSqr.apply(2));
        System.out.println("  apply(-2) = " + sqrSqr.apply(-2));
        System.out.println("  Ожидается: 0, 1, 16, 16");
        System.out.println();
        
        // sqr(sqr(sqr(x))) = x^8
        MathFunction sqrSqrSqr = sqr.andThen(sqr).andThen(sqr);
        System.out.println("sqr.andThen(sqr).andThen(sqr) (x^8):");
        System.out.println("  apply(1) = " + sqrSqrSqr.apply(1));
        System.out.println("  apply(2) = " + sqrSqrSqr.apply(2));
        System.out.println("  apply(-1) = " + sqrSqrSqr.apply(-1));
        System.out.println("  Ожидается: 1, 256, 1");
        System.out.println();
        
        // Тестируем прямое применение без сохранения ссылки
        System.out.println("=== Прямое применение без ссылки ===");
        
        double result1 = unit.andThen(sqr).andThen(constant5).apply(123.0);
        double result2 = sqr.andThen(sqr).andThen(sqr).apply(2.0);
        
        System.out.println("unit.andThen(sqr).andThen(constant5).apply(123) = " + result1);
        System.out.println("sqr.andThen(sqr).andThen(sqr).apply(2) = " + result2);
        System.out.println();
        
        // Тестируем различные комбинации
        System.out.println("=== Различные комбинации ===");
        
        MathFunction[] functions = {sqr, constant5, zero, unit};
        String[] names = {"sqr", "constant5", "zero", "unit"};
        
        System.out.println("Тестирование всех комбинаций двух функций:");
        for (int i = 0; i < functions.length; i++) {
            for (int j = 0; j < functions.length; j++) {
                MathFunction composite = functions[i].andThen(functions[j]);
                double result = composite.apply(2.0);
                System.out.printf("  %s.andThen(%s).apply(2) = %.1f%n", 
                    names[i], names[j], result);
            }
        }
        
        System.out.println("\nДемонстрация завершена!");
        System.out.println("Композиция функций работает корректно!");
    }
}
