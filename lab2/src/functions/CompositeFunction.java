package functions;

/**
 * Класс, реализующий композицию двух математических функций
 * 
 * Если f и g - математические функции, то CompositeFunction(f, g) 
 * представляет функцию h(x) = g(f(x))
 */
public class CompositeFunction implements MathFunction {
    
    private final MathFunction firstFunction;
    private final MathFunction secondFunction;
    
    /**
     * Конструктор композиции функций
     * @param firstFunction первая функция (применяется первой)
     * @param secondFunction вторая функция (применяется к результату первой)
     */
    public CompositeFunction(MathFunction firstFunction, MathFunction secondFunction) {
        this.firstFunction = firstFunction;
        this.secondFunction = secondFunction;
    }
    
    /**
     * Применяет композицию функций: result = secondFunction(firstFunction(x))
     * @param x входное значение
     * @return результат применения композиции функций
     */
    @Override
    public double apply(double x) {
        double intermediateResult = firstFunction.apply(x);
        return secondFunction.apply(intermediateResult);
    }
    
    /**
     * Возвращает первую функцию
     * @return первая функция
     */
    public MathFunction getFirstFunction() {
        return firstFunction;
    }
    
    /**
     * Возвращает вторую функцию
     * @return вторая функция
     */
    public MathFunction getSecondFunction() {
        return secondFunction;
    }
    
    @Override
    public String toString() {
        return "CompositeFunction(" + firstFunction + ", " + secondFunction + ")";
    }
}
