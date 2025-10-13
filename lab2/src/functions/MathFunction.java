package functions;

/**
 * Интерфейс для математических функций
 */
public interface MathFunction {
    /**
     * Применяет функцию к заданному значению x
     * @param x входное значение
     * @return результат применения функции
     */
    double apply(double x);
    
    /**
     * Создает композицию функций: this.andThen(afterFunction) эквивалентно afterFunction(this(x))
     * @param afterFunction функция, которая будет применена к результату текущей функции
     * @return композиция функций
     */
    default CompositeFunction andThen(MathFunction afterFunction) {
        return new CompositeFunction(this, afterFunction);
    }
}
