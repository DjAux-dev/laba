package functions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса SqrFunction
 */
public class SqrFunctionTest {
    
    private SqrFunction sqrFunction;
    
    @BeforeEach
    public void setUp() {
        sqrFunction = new SqrFunction();
    }
    
    @Test
    public void testApplyWithPositiveNumber() {
        assertEquals(4.0, sqrFunction.apply(2.0), 0.0001);
        assertEquals(9.0, sqrFunction.apply(3.0), 0.0001);
        assertEquals(25.0, sqrFunction.apply(5.0), 0.0001);
    }
    
    @Test
    public void testApplyWithNegativeNumber() {
        assertEquals(4.0, sqrFunction.apply(-2.0), 0.0001);
        assertEquals(9.0, sqrFunction.apply(-3.0), 0.0001);
        assertEquals(25.0, sqrFunction.apply(-5.0), 0.0001);
    }
    
    @Test
    public void testApplyWithZero() {
        assertEquals(0.0, sqrFunction.apply(0.0), 0.0001);
    }
    
    @Test
    public void testApplyWithDecimalNumber() {
        assertEquals(2.25, sqrFunction.apply(1.5), 0.0001);
        assertEquals(0.25, sqrFunction.apply(0.5), 0.0001);
        assertEquals(6.25, sqrFunction.apply(-2.5), 0.0001);
    }
    
    @Test
    public void testApplyWithLargeNumber() {
        assertEquals(1000000.0, sqrFunction.apply(1000.0), 0.0001);
        assertEquals(1000000.0, sqrFunction.apply(-1000.0), 0.0001);
    }
    
    @Test
    public void testApplyWithSmallNumber() {
        assertEquals(0.000001, sqrFunction.apply(0.001), 0.0000001);
        assertEquals(0.000001, sqrFunction.apply(-0.001), 0.0000001);
    }
    
    @Test
    public void testImplementsMathFunction() {
        assertTrue(sqrFunction instanceof MathFunction);
    }
}