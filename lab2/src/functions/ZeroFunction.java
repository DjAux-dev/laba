package functions;

/**
 * Класс, реализующий функцию, всегда возвращающую 0
 */
public class ZeroFunction extends ConstantFunction {
    
    /**
     * Конструктор функции нуля
     * Инициализирует константное значение равным 0
     */
    public ZeroFunction() {
        super(0.0);
    }
}
