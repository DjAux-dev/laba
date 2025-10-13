package functions;

/**
 * Демонстрационный класс для тестирования RungeKuttaFunction
 */
public class RungeKuttaDemo {
    
    public static void main(String[] args) {
        System.out.println("Демонстрация метода Рунге-Кутты для решения ОДУ");
        System.out.println("Дифференциальное уравнение: dy/dx = x*y");
        System.out.println("Начальное условие: y(0) = 1");
        System.out.println("Аналитическое решение: y(x) = exp(x^2/2)");
        System.out.println("================================================");
        
        // Создаем функцию с разными точностями
        RungeKuttaFunction rkCoarse = new RungeKuttaFunction(0.0, 1.0, 0.1, 1000);
        RungeKuttaFunction rkFine = new RungeKuttaFunction(0.0, 1.0, 0.01, 1000);
        RungeKuttaFunction rkVeryFine = new RungeKuttaFunction(0.0, 1.0, 0.001, 1000);
        
        double[] testPoints = {-2.0, -1.0, -0.5, 0.0, 0.5, 1.0, 1.5, 2.0};
        
        System.out.printf("%-8s %-12s %-12s %-12s %-12s %-12s%n", 
            "x", "Аналитическое", "RK (0.1)", "RK (0.01)", "RK (0.001)", "Погрешность");
        System.out.println("----------------------------------------------------------------");
        
        for (double x : testPoints) {
            double analytical = Math.exp(x * x / 2.0);
            double coarse = rkCoarse.apply(x);
            double fine = rkFine.apply(x);
            double veryFine = rkVeryFine.apply(x);
            double error = Math.abs(veryFine - analytical);
            
            System.out.printf("%-8.1f %-12.6f %-12.6f %-12.6f %-12.6f %-12.6f%n",
                x, analytical, coarse, fine, veryFine, error);
        }
        
        System.out.println("\n================================================");
        System.out.println("Тестирование граничных случаев:");
        
        // Тестирование начальной точки
        System.out.println("y(0) = " + rkFine.apply(0.0) + " (ожидается 1.0)");
        
        // Тестирование малых значений
        System.out.println("y(0.01) = " + rkFine.apply(0.01));
        System.out.println("y(-0.01) = " + rkFine.apply(-0.01));
        
        // Тестирование больших значений
        System.out.println("y(3.0) = " + rkFine.apply(3.0));
        System.out.println("y(-3.0) = " + rkFine.apply(-3.0));
        
        System.out.println("\n================================================");
        System.out.println("Проверка монотонности функции:");
        
        double prev = rkFine.apply(0.0);
        for (double x = 0.1; x <= 2.0; x += 0.1) {
            double current = rkFine.apply(x);
            if (current <= prev) {
                System.out.println("ОШИБКА: Функция не монотонно возрастает в точке x = " + x);
            }
            prev = current;
        }
        System.out.println("Функция монотонно возрастает для положительных x");
        
        System.out.println("\nДемонстрация завершена!");
    }
}
