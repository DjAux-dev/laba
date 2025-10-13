package functions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса ZeroFunction
 */
public class ZeroFunctionTest {
    
    private ZeroFunction zeroFunction;
    
    @BeforeEach
    public void setUp() {
        zeroFunction = new ZeroFunction();
    }
    
    @Test
    public void testApplyReturnsZero() {
        assertEquals(0.0, zeroFunction.apply(0.0), 0.0001);
        assertEquals(0.0, zeroFunction.apply(1.0), 0.0001);
        assertEquals(0.0, zeroFunction.apply(-1.0), 0.0001);
        assertEquals(0.0, zeroFunction.apply(100.0), 0.0001);
        assertEquals(0.0, zeroFunction.apply(-100.0), 0.0001);
        assertEquals(0.0, zeroFunction.apply(Double.MAX_VALUE), 0.0001);
        assertEquals(0.0, zeroFunction.apply(Double.MIN_VALUE), 0.0001);
        assertEquals(0.0, zeroFunction.apply(Math.PI), 0.0001);
        assertEquals(0.0, zeroFunction.apply(Math.E), 0.0001);
    }
    
    @Test
    public void testGetConstantReturnsZero() {
        assertEquals(0.0, zeroFunction.getConstant(), 0.0001);
    }
    
    @Test
    public void testExtendsConstantFunction() {
        assertTrue(zeroFunction instanceof ConstantFunction);
    }
    
    @Test
    public void testImplementsMathFunction() {
        assertTrue(zeroFunction instanceof MathFunction);
    }
    
    @Test
    public void testConstructorNoArguments() {
        // Проверяем, что конструктор без аргументов работает корректно
        ZeroFunction newZeroFunction = new ZeroFunction();
        assertEquals(0.0, newZeroFunction.apply(42.0), 0.0001);
    }
}
