package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CompositeFunctionTest {

    @Test
    void compose_simpleFunctions() {
        MathFunction f = x -> x + 2.0; // first
        MathFunction g = x -> 3.0 * x; // second
        MathFunction h = new CompositeFunction(f, g); // g(f(x)) = 3*(x+2)

        assertEquals(3.0 * (0.0 + 2.0), h.apply(0.0));
        assertEquals(3.0 * (1.0 + 2.0), h.apply(1.0));
        assertEquals(3.0 * (-4.0 + 2.0), h.apply(-4.0));
    }

    @Test
    void compose_withIdentity_returnsOriginal() {
        MathFunction id = new IdentityFunction();
        MathFunction f = x -> x * x; // square
        MathFunction h1 = new CompositeFunction(f, id); // id(f(x)) = f(x)
        MathFunction h2 = new CompositeFunction(id, f); // f(id(x)) = f(x)

        assertEquals(f.apply(3.0), h1.apply(3.0));
        assertEquals(f.apply(3.0), h2.apply(3.0));
    }

    @Test
    void compose_compositeWithComposite() {
        MathFunction add1 = x -> x + 1.0;
        MathFunction times2 = x -> 2.0 * x;
        MathFunction square = x -> x * x;

        MathFunction a = new CompositeFunction(add1, times2); // 2*(x+1)
        MathFunction b = new CompositeFunction(square, add1); // (x^2)+1

        MathFunction h = new CompositeFunction(a, b); // b(a(x)) = (2(x+1))^2 + 1

        double x = 2.5;
        double expected = Math.pow(2.0 * (x + 1.0), 2.0) + 1.0;
        assertEquals(expected, h.apply(x), 1e-12);
    }
}


