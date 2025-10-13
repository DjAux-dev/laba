package functions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для композиции функций и метода andThen
 */
public class CompositeFunctionTest {
    
    private SqrFunction sqrFunction;
    private ConstantFunction constantFunction;
    private ZeroFunction zeroFunction;
    private UnitFunction unitFunction;
    
    @BeforeEach
    public void setUp() {
        sqrFunction = new SqrFunction();
        constantFunction = new ConstantFunction(5.0);
        zeroFunction = new ZeroFunction();
        unitFunction = new UnitFunction();
    }
    
    @Test
    public void testCompositeFunctionConstructor() {
        CompositeFunction composite = new CompositeFunction(sqrFunction, constantFunction);
        
        assertNotNull(composite.getFirstFunction());
        assertNotNull(composite.getSecondFunction());
        assertTrue(composite.getFirstFunction() instanceof SqrFunction);
        assertTrue(composite.getSecondFunction() instanceof ConstantFunction);
    }
    
    @Test
    public void testCompositeFunctionApply() {
        // Тестируем композицию: constantFunction(sqrFunction(x))
        // constantFunction всегда возвращает 5, независимо от входа
        CompositeFunction composite = new CompositeFunction(sqrFunction, constantFunction);
        
        assertEquals(5.0, composite.apply(0.0), 0.0001);
        assertEquals(5.0, composite.apply(1.0), 0.0001);
        assertEquals(5.0, composite.apply(10.0), 0.0001);
        assertEquals(5.0, composite.apply(-5.0), 0.0001);
    }
    
    @Test
    public void testAndThenMethod() {
        // Тестируем метод andThen: sqrFunction.andThen(constantFunction)
        MathFunction composite = sqrFunction.andThen(constantFunction);
        
        assertTrue(composite instanceof CompositeFunction);
        assertEquals(5.0, composite.apply(2.0), 0.0001);
        assertEquals(5.0, composite.apply(-3.0), 0.0001);
    }
    
    @Test
    public void testChainOfComposition() {
        // Тестируем цепочку: unitFunction.andThen(sqrFunction).andThen(constantFunction)
        // Это эквивалентно: constantFunction(sqrFunction(unitFunction(x)))
        // unitFunction(x) = 1, sqrFunction(1) = 1, constantFunction(1) = 5
        MathFunction chain = unitFunction.andThen(sqrFunction).andThen(constantFunction);
        
        assertEquals(5.0, chain.apply(0.0), 0.0001);
        assertEquals(5.0, chain.apply(100.0), 0.0001);
        assertEquals(5.0, chain.apply(-50.0), 0.0001);
    }
    
    @Test
    public void testSqrAfterConstant() {
        // Тестируем: sqrFunction(constantFunction(x))
        // constantFunction(x) = 5, sqrFunction(5) = 25
        MathFunction composite = constantFunction.andThen(sqrFunction);
        
        assertEquals(25.0, composite.apply(0.0), 0.0001);
        assertEquals(25.0, composite.apply(10.0), 0.0001);
        assertEquals(25.0, composite.apply(-5.0), 0.0001);
    }
    
    @Test
    public void testSqrAfterSqr() {
        // Тестируем: sqrFunction(sqrFunction(x)) = x^4
        MathFunction composite = sqrFunction.andThen(sqrFunction);
        
        assertEquals(0.0, composite.apply(0.0), 0.0001);
        assertEquals(1.0, composite.apply(1.0), 0.0001);
        assertEquals(16.0, composite.apply(2.0), 0.0001);
        assertEquals(81.0, composite.apply(3.0), 0.0001);
        assertEquals(16.0, composite.apply(-2.0), 0.0001);
    }
    
    @Test
    public void testZeroFunctionComposition() {
        // Тестируем композиции с ZeroFunction
        // ZeroFunction всегда возвращает 0
        
        // sqrFunction(zeroFunction(x)) = sqrFunction(0) = 0
        MathFunction composite1 = zeroFunction.andThen(sqrFunction);
        assertEquals(0.0, composite1.apply(5.0), 0.0001);
        
        // zeroFunction(sqrFunction(x)) = zeroFunction(x^2) = 0
        MathFunction composite2 = sqrFunction.andThen(zeroFunction);
        assertEquals(0.0, composite2.apply(5.0), 0.0001);
        
        // zeroFunction(zeroFunction(x)) = zeroFunction(0) = 0
        MathFunction composite3 = zeroFunction.andThen(zeroFunction);
        assertEquals(0.0, composite3.apply(5.0), 0.0001);
    }
    
    @Test
    public void testUnitFunctionComposition() {
        // Тестируем композиции с UnitFunction
        // UnitFunction всегда возвращает 1
        
        // sqrFunction(unitFunction(x)) = sqrFunction(1) = 1
        MathFunction composite1 = unitFunction.andThen(sqrFunction);
        assertEquals(1.0, composite1.apply(5.0), 0.0001);
        
        // unitFunction(sqrFunction(x)) = unitFunction(x^2) = 1
        MathFunction composite2 = sqrFunction.andThen(unitFunction);
        assertEquals(1.0, composite2.apply(5.0), 0.0001);
        
        // unitFunction(unitFunction(x)) = unitFunction(1) = 1
        MathFunction composite3 = unitFunction.andThen(unitFunction);
        assertEquals(1.0, composite3.apply(5.0), 0.0001);
    }
    
    @Test
    public void testComplexChain() {
        // Тестируем сложную цепочку: f(g(h(x))) где f=constant, g=sqr, h=unit
        // h(x) = 1, g(1) = 1, f(1) = 5
        MathFunction complexChain = unitFunction.andThen(sqrFunction).andThen(constantFunction);
        
        assertEquals(5.0, complexChain.apply(0.0), 0.0001);
        assertEquals(5.0, complexChain.apply(100.0), 0.0001);
        assertEquals(5.0, complexChain.apply(-50.0), 0.0001);
    }
    
    @Test
    public void testDirectApplyWithoutReference() {
        // Тестируем прямое применение без сохранения ссылки
        // f.andThen(g).andThen(h).apply(x)
        double result = unitFunction.andThen(sqrFunction).andThen(constantFunction).apply(42.0);
        
        assertEquals(5.0, result, 0.0001);
    }
    
    @Test
    public void testLongChain() {
        // Тестируем длинную цепочку функций
        // zero -> unit -> sqr -> constant
        // 0 -> 1 -> 1 -> 5
        MathFunction longChain = zeroFunction
            .andThen(unitFunction)
            .andThen(sqrFunction)
            .andThen(constantFunction);
        
        assertEquals(5.0, longChain.apply(999.0), 0.0001);
    }
    
    @Test
    public void testIdentityComposition() {
        // Тестируем композицию с одинаковыми функциями
        MathFunction sqrSqr = sqrFunction.andThen(sqrFunction);
        MathFunction sqrSqrSqr = sqrSqr.andThen(sqrFunction);
        
        // sqrSqrSqr(x) = x^8
        assertEquals(0.0, sqrSqrSqr.apply(0.0), 0.0001);
        assertEquals(1.0, sqrSqrSqr.apply(1.0), 0.0001);
        assertEquals(256.0, sqrSqrSqr.apply(2.0), 0.0001);
    }
    
    @Test
    public void testToString() {
        CompositeFunction composite = new CompositeFunction(sqrFunction, constantFunction);
        String str = composite.toString();
        
        assertTrue(str.contains("CompositeFunction"));
        assertTrue(str.contains("SqrFunction"));
        assertTrue(str.contains("ConstantFunction"));
    }
}
