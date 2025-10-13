package functions;

/**
 * Класс, реализующий функцию квадрата числа
 */
public class SqrFunction implements MathFunction {
    
    /**
     * Возвращает квадрат входного значения x
     * @param x входное значение
     * @return квадрат x (x^2)
     */
    @Override
    public double apply(double x) {
        return Math.pow(x, 2);
    }
}
