package functions;

/**
 * Класс, реализующий константную функцию
 * Всегда возвращает одно и то же значение независимо от входного аргумента
 */
public class ConstantFunction implements MathFunction {
    
    private final double constant;
    
    /**
     * Конструктор константной функции
     * @param constant значение, которое будет возвращать функция
     */
    public ConstantFunction(double constant) {
        this.constant = constant;
    }
    
    /**
     * Возвращает константное значение независимо от входного аргумента
     * @param x входное значение (игнорируется)
     * @return константное значение
     */
    @Override
    public double apply(double x) {
        return constant;
    }
    
    /**
     * Возвращает константное значение функции
     * @return константное значение
     */
    public double getConstant() {
        return constant;
    }
}
