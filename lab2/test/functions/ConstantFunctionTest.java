package functions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса ConstantFunction
 */
public class ConstantFunctionTest {
    
    private ConstantFunction constantFunction;
    private final double TEST_CONSTANT = 5.5;
    
    @BeforeEach
    public void setUp() {
        constantFunction = new ConstantFunction(TEST_CONSTANT);
    }
    
    @Test
    public void testApplyReturnsConstant() {
        assertEquals(TEST_CONSTANT, constantFunction.apply(0.0), 0.0001);
        assertEquals(TEST_CONSTANT, constantFunction.apply(1.0), 0.0001);
        assertEquals(TEST_CONSTANT, constantFunction.apply(-1.0), 0.0001);
        assertEquals(TEST_CONSTANT, constantFunction.apply(100.0), 0.0001);
        assertEquals(TEST_CONSTANT, constantFunction.apply(-100.0), 0.0001);
        assertEquals(TEST_CONSTANT, constantFunction.apply(Double.MAX_VALUE), 0.0001);
        assertEquals(TEST_CONSTANT, constantFunction.apply(Double.MIN_VALUE), 0.0001);
    }
    
    @Test
    public void testGetConstant() {
        assertEquals(TEST_CONSTANT, constantFunction.getConstant(), 0.0001);
    }
    
    @Test
    public void testImplementsMathFunction() {
        assertTrue(constantFunction instanceof MathFunction);
    }
    
    @Test
    public void testConstructorWithZero() {
        ConstantFunction zeroFunction = new ConstantFunction(0.0);
        assertEquals(0.0, zeroFunction.apply(123.0), 0.0001);
        assertEquals(0.0, zeroFunction.getConstant(), 0.0001);
    }
    
    @Test
    public void testConstructorWithNegative() {
        ConstantFunction negativeFunction = new ConstantFunction(-3.14);
        assertEquals(-3.14, negativeFunction.apply(456.0), 0.0001);
        assertEquals(-3.14, negativeFunction.getConstant(), 0.0001);
    }
    
    @Test
    public void testConstructorWithLargeNumber() {
        ConstantFunction largeFunction = new ConstantFunction(1e6);
        assertEquals(1e6, largeFunction.apply(0.0), 0.0001);
        assertEquals(1e6, largeFunction.getConstant(), 0.0001);
    }
}