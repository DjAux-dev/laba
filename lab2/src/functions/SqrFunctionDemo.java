package functions;

/**
 * Демонстрационный класс для тестирования SqrFunction
 */
public class SqrFunctionDemo {
    
    public static void main(String[] args) {
        SqrFunction sqrFunction = new SqrFunction();
        
        System.out.println("Тестирование SqrFunction:");
        System.out.println("sqrFunction.apply(2.0) = " + sqrFunction.apply(2.0));
        System.out.println("sqrFunction.apply(-3.0) = " + sqrFunction.apply(-3.0));
        System.out.println("sqrFunction.apply(0.0) = " + sqrFunction.apply(0.0));
        System.out.println("sqrFunction.apply(1.5) = " + sqrFunction.apply(1.5));
        System.out.println("sqrFunction.apply(0.001) = " + sqrFunction.apply(0.001));
        
        // Проверка, что результат соответствует ожидаемому
        assert sqrFunction.apply(2.0) == 4.0 : "2^2 должно быть 4.0";
        assert sqrFunction.apply(-3.0) == 9.0 : "(-3)^2 должно быть 9.0";
        assert sqrFunction.apply(0.0) == 0.0 : "0^2 должно быть 0.0";
        
        System.out.println("Все тесты прошли успешно!");
    }
}
