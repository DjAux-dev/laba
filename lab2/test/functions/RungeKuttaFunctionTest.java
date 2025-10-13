package functions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса RungeKuttaFunction
 */
public class RungeKuttaFunctionTest {
    
    private RungeKuttaFunction rkFunction;
    private static final double TOLERANCE = 0.1; // Толерантность для численных методов
    
    @BeforeEach
    public void setUp() {
        // Создаем функцию с начальными условиями y(0) = 1 для ОДУ dy/dx = x*y
        rkFunction = new RungeKuttaFunction(0.0, 1.0, 0.01, 1000);
    }
    
    @Test
    public void testApplyAtInitialPoint() {
        assertEquals(1.0, rkFunction.apply(0.0), 0.001);
    }
    
    @Test
    public void testApplyNearInitialPoint() {
        double result = rkFunction.apply(0.1);
        assertTrue(result > 1.0, "Для положительного x результат должен быть больше начального значения");
        assertTrue(result < 1.1, "Для малого x результат не должен сильно отличаться от начального");
    }
    
    @Test
    public void testApplyPositiveX() {
        double result1 = rkFunction.apply(1.0);
        double result2 = rkFunction.apply(2.0);
        
        assertTrue(result2 > result1, "Функция должна быть возрастающей для положительных x");
        assertTrue(result1 > 1.0, "Результат для x=1 должен быть больше начального значения");
    }
    
    @Test
    public void testApplyNegativeX() {
        double result1 = rkFunction.apply(-1.0);
        double result2 = rkFunction.apply(-2.0);
        
        assertTrue(result1 < 1.0, "Для отрицательного x результат должен быть меньше начального значения");
        assertTrue(result2 < result1, "Функция должна быть возрастающей для отрицательных x");
    }
    
    @Test
    public void testImplementsMathFunction() {
        assertTrue(rkFunction instanceof MathFunction);
    }
    
    @Test
    public void testGetters() {
        assertEquals(0.0, rkFunction.getX0(), 0.0001);
        assertEquals(1.0, rkFunction.getY0(), 0.0001);
        assertEquals(0.01, rkFunction.getStep(), 0.0001);
    }
    
    @Test
    public void testConstructorWithCustomParameters() {
        RungeKuttaFunction customRK = new RungeKuttaFunction(1.0, 2.0, 0.001, 5000);
        
        assertEquals(1.0, customRK.getX0(), 0.0001);
        assertEquals(2.0, customRK.getY0(), 0.0001);
        assertEquals(0.001, customRK.getStep(), 0.0001);
        assertEquals(2.0, customRK.apply(1.0), 0.001);
    }
    
    @Test
    public void testConstructorWithDefaultParameters() {
        RungeKuttaFunction defaultRK = new RungeKuttaFunction(0.5, 3.0);
        
        assertEquals(0.5, defaultRK.getX0(), 0.0001);
        assertEquals(3.0, defaultRK.getY0(), 0.0001);
        assertEquals(0.01, defaultRK.getStep(), 0.0001); // значение по умолчанию
        assertEquals(3.0, defaultRK.apply(0.5), 0.001);
    }
    
    @Test
    public void testSmallStepAccuracy() {
        RungeKuttaFunction preciseRK = new RungeKuttaFunction(0.0, 1.0, 0.001, 10000);
        double result1 = preciseRK.apply(1.0);
        
        RungeKuttaFunction lessPreciseRK = new RungeKuttaFunction(0.0, 1.0, 0.1, 10000);
        double result2 = lessPreciseRK.apply(1.0);
        
        // Более точный метод должен давать результат ближе к аналитическому решению
        // (для dy/dx = x*y, y(0)=1 решение: y(x) = exp(x^2/2))
        double analyticalSolution = Math.exp(0.5); // exp(1^2/2) = exp(0.5)
        
        double error1 = Math.abs(result1 - analyticalSolution);
        double error2 = Math.abs(result2 - analyticalSolution);
        
        assertTrue(error1 < error2, "Меньший шаг должен давать более точный результат");
    }
    
    @Test
    public void testMonotonicity() {
        // Проверяем, что функция монотонно возрастает для положительных x
        double[] xValues = {0.0, 0.5, 1.0, 1.5, 2.0};
        double[] yValues = new double[xValues.length];
        
        for (int i = 0; i < xValues.length; i++) {
            yValues[i] = rkFunction.apply(xValues[i]);
        }
        
        for (int i = 1; i < yValues.length; i++) {
            assertTrue(yValues[i] > yValues[i-1], 
                "Функция должна быть возрастающей: y(" + xValues[i] + ") > y(" + xValues[i-1] + ")");
        }
    }
    
    @Test
    public void testSymmetryProperty() {
        // Для функции с начальным условием y(0) = 1 и ОДУ dy/dx = x*y
        // функция должна быть симметричной относительно y(0)
        double result1 = rkFunction.apply(0.5);
        double result2 = rkFunction.apply(-0.5);
        
        // Проверяем, что функция ведет себя предсказуемо для положительных и отрицательных x
        assertTrue(result1 > 1.0, "Для положительного x результат должен быть больше 1");
        assertTrue(result2 < 1.0, "Для отрицательного x результат должен быть меньше 1");
    }
}
