package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IdentityFunctionTest {

    @Test
    void apply_returnsSameValue() {
        MathFunction f = new IdentityFunction();

        assertEquals(0.0, f.apply(0.0));
        assertEquals(1.0, f.apply(1.0));
        assertEquals(-1.0, f.apply(-1.0));
        assertEquals(123.456, f.apply(123.456));
        assertEquals(-98765.4321, f.apply(-98765.4321));

        double posInf = Double.POSITIVE_INFINITY;
        assertEquals(posInf, f.apply(posInf));

        double negInf = Double.NEGATIVE_INFINITY;
        assertEquals(negInf, f.apply(negInf));

        double nan = Double.NaN;
        assertTrue(Double.isNaN(f.apply(nan)));
    }
}


