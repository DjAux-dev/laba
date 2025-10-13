package functions;

/**
 * Демонстрационный класс для тестирования константных функций
 */
public class ConstantFunctionsDemo {
    
    public static void main(String[] args) {
        System.out.println("Тестирование константных функций:");
        
        // Тестирование ConstantFunction
        System.out.println("\n=== ConstantFunction ===");
        ConstantFunction constant5 = new ConstantFunction(5.0);
        System.out.println("ConstantFunction(5.0):");
        System.out.println("  apply(0.0) = " + constant5.apply(0.0));
        System.out.println("  apply(10.0) = " + constant5.apply(10.0));
        System.out.println("  apply(-5.0) = " + constant5.apply(-5.0));
        System.out.println("  getConstant() = " + constant5.getConstant());
        
        ConstantFunction constantPi = new ConstantFunction(Math.PI);
        System.out.println("ConstantFunction(Math.PI):");
        System.out.println("  apply(100.0) = " + constantPi.apply(100.0));
        System.out.println("  getConstant() = " + constantPi.getConstant());
        
        // Тестирование ZeroFunction
        System.out.println("\n=== ZeroFunction ===");
        ZeroFunction zeroFunc = new ZeroFunction();
        System.out.println("ZeroFunction():");
        System.out.println("  apply(0.0) = " + zeroFunc.apply(0.0));
        System.out.println("  apply(42.0) = " + zeroFunc.apply(42.0));
        System.out.println("  apply(-99.0) = " + zeroFunc.apply(-99.0));
        System.out.println("  getConstant() = " + zeroFunc.getConstant());
        
        // Тестирование UnitFunction
        System.out.println("\n=== UnitFunction ===");
        UnitFunction unitFunc = new UnitFunction();
        System.out.println("UnitFunction():");
        System.out.println("  apply(0.0) = " + unitFunc.apply(0.0));
        System.out.println("  apply(15.0) = " + unitFunc.apply(15.0));
        System.out.println("  apply(-7.0) = " + unitFunc.apply(-7.0));
        System.out.println("  getConstant() = " + unitFunc.getConstant());
        
        // Проверка наследования
        System.out.println("\n=== Проверка наследования ===");
        System.out.println("ZeroFunction instanceof ConstantFunction: " + (zeroFunc instanceof ConstantFunction));
        System.out.println("UnitFunction instanceof ConstantFunction: " + (unitFunc instanceof ConstantFunction));
        System.out.println("ZeroFunction instanceof MathFunction: " + (zeroFunc instanceof MathFunction));
        System.out.println("UnitFunction instanceof MathFunction: " + (unitFunc instanceof MathFunction));
        
        System.out.println("\nВсе тесты прошли успешно!");
    }
}
