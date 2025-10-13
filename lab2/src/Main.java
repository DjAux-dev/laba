import functions.*;

/**
 * Главный класс для демонстрации работы математических функций
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Демонстрация математических функций");
        System.out.println("===================================");
        
        // Создаем различные функции
        SqrFunction sqr = new SqrFunction();
        ConstantFunction constant = new ConstantFunction(5.0);
        ZeroFunction zero = new ZeroFunction();
        UnitFunction unit = new UnitFunction();
        
        // Тестируем основные функции
        System.out.println("Основные функции:");
        System.out.println("sqr(3) = " + sqr.apply(3.0));
        System.out.println("constant(10) = " + constant.apply(10.0));
        System.out.println("zero(5) = " + zero.apply(5.0));
        System.out.println("unit(7) = " + unit.apply(7.0));
        
        // Тестируем композицию функций
        System.out.println("\nКомпозиция функций:");
        MathFunction composite1 = sqr.andThen(constant);
        System.out.println("sqr.andThen(constant).apply(2) = " + composite1.apply(2.0));
        
        MathFunction composite2 = unit.andThen(sqr).andThen(constant);
        System.out.println("unit.andThen(sqr).andThen(constant).apply(10) = " + composite2.apply(10.0));
        
        System.out.println("\nВсе функции работают корректно!");
    }
}
