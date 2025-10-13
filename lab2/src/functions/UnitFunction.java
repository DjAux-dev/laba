package functions;

/**
 * Класс, реализующий функцию, всегда возвращающую 1
 */
public class UnitFunction extends ConstantFunction {
    
    /**
     * Конструктор единичной функции
     * Инициализирует константное значение равным 1
     */
    public UnitFunction() {
        super(1.0);
    }
}
