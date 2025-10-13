package functions;

/**
 * Класс, реализующий численное решение дифференциального уравнения
 * методом Рунге-Кутты 4-го порядка
 * 
 * Решает ОДУ вида: dy/dx = f(x,y) с начальным условием y(x0) = y0
 * Для данного x возвращает приближенное значение y(x)
 */
public class RungeKuttaFunction implements MathFunction {
    
    private final double x0;           // начальная точка
    private final double y0;           // начальное значение y(x0)
    private final double step;         // шаг интегрирования
    private final int maxSteps;        // максимальное количество шагов
    
    /**
     * Конструктор для метода Рунге-Кутты
     * @param x0 начальная точка
     * @param y0 начальное значение y(x0)
     * @param step шаг интегрирования (чем меньше, тем точнее)
     * @param maxSteps максимальное количество шагов для предотвращения бесконечного цикла
     */
    public RungeKuttaFunction(double x0, double y0, double step, int maxSteps) {
        this.x0 = x0;
        this.y0 = y0;
        this.step = step;
        this.maxSteps = maxSteps;
    }
    
    /**
     * Конструктор с параметрами по умолчанию
     * @param x0 начальная точка
     * @param y0 начальное значение
     */
    public RungeKuttaFunction(double x0, double y0) {
        this(x0, y0, 0.01, 10000);
    }
    
    /**
     * Вычисляет значение функции y в точке x, используя метод Рунге-Кутты
     * для решения ОДУ dy/dx = x*y (пример: экспоненциальный рост)
     * 
     * @param x точка, в которой нужно найти значение функции
     * @return приближенное значение y(x)
     */
    @Override
    public double apply(double x) {
        if (Math.abs(x - x0) < 1e-10) {
            return y0;
        }
        
        double currentX = x0;
        double currentY = y0;
        double h = (x > x0) ? Math.abs(step) : -Math.abs(step);
        int steps = 0;
        
        while (Math.abs(currentX - x) > Math.abs(h) && steps < maxSteps) {
            // Метод Рунге-Кутты 4-го порядка
            double k1 = h * derivative(currentX, currentY);
            double k2 = h * derivative(currentX + h/2, currentY + k1/2);
            double k3 = h * derivative(currentX + h/2, currentY + k2/2);
            double k4 = h * derivative(currentX + h, currentY + k3);
            
            currentY = currentY + (k1 + 2*k2 + 2*k3 + k4) / 6;
            currentX += h;
            steps++;
        }
        
        // Финальная корректировка для точного попадания в точку x
        if (Math.abs(currentX - x) > 1e-10 && steps < maxSteps) {
            double finalH = x - currentX;
            double k1 = finalH * derivative(currentX, currentY);
            double k2 = finalH * derivative(currentX + finalH/2, currentY + k1/2);
            double k3 = finalH * derivative(currentX + finalH/2, currentY + k2/2);
            double k4 = finalH * derivative(currentX + finalH, currentY + k3);
            
            currentY = currentY + (k1 + 2*k2 + 2*k3 + k4) / 6;
        }
        
        return currentY;
    }
    
    /**
     * Правая часть дифференциального уравнения dy/dx = f(x,y)
     * В данном примере: f(x,y) = x*y (экспоненциальный рост)
     * 
     * @param x текущая координата x
     * @param y текущее значение y
     * @return значение производной dy/dx
     */
    private double derivative(double x, double y) {
        return x * y;  // dy/dx = x*y
    }
    
    /**
     * Возвращает начальную точку
     * @return x0
     */
    public double getX0() {
        return x0;
    }
    
    /**
     * Возвращает начальное значение
     * @return y0
     */
    public double getY0() {
        return y0;
    }
    
    /**
     * Возвращает шаг интегрирования
     * @return step
     */
    public double getStep() {
        return step;
    }
}
