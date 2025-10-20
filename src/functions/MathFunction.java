package functions;

/**
 * Represents a mathematical function that maps a real number to a real number.
 */
public interface MathFunction {
    /**
     * Applies this function to the given argument.
     *
     * @param x input value
     * @return output value of the function at x
     */
    double apply(double x);
}


