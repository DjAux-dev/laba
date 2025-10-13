package functions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса UnitFunction
 */
public class UnitFunctionTest {
    
    private UnitFunction unitFunction;
    
    @BeforeEach
    public void setUp() {
        unitFunction = new UnitFunction();
    }
    
    @Test
    public void testApplyReturnsOne() {
        assertEquals(1.0, unitFunction.apply(0.0), 0.0001);
        assertEquals(1.0, unitFunction.apply(1.0), 0.0001);
        assertEquals(1.0, unitFunction.apply(-1.0), 0.0001);
        assertEquals(1.0, unitFunction.apply(100.0), 0.0001);
        assertEquals(1.0, unitFunction.apply(-100.0), 0.0001);
        assertEquals(1.0, unitFunction.apply(Double.MAX_VALUE), 0.0001);
        assertEquals(1.0, unitFunction.apply(Double.MIN_VALUE), 0.0001);
        assertEquals(1.0, unitFunction.apply(Math.PI), 0.0001);
        assertEquals(1.0, unitFunction.apply(Math.E), 0.0001);
    }
    
    @Test
    public void testGetConstantReturnsOne() {
        assertEquals(1.0, unitFunction.getConstant(), 0.0001);
    }
    
    @Test
    public void testExtendsConstantFunction() {
        assertTrue(unitFunction instanceof ConstantFunction);
    }
    
    @Test
    public void testImplementsMathFunction() {
        assertTrue(unitFunction instanceof MathFunction);
    }
    
    @Test
    public void testConstructorNoArguments() {
        // Проверяем, что конструктор без аргументов работает корректно
        UnitFunction newUnitFunction = new UnitFunction();
        assertEquals(1.0, newUnitFunction.apply(42.0), 0.0001);
    }
}