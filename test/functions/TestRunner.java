package functions;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("Running ArrayTabulatedFunction tests...");
        try {
            ArrayTabulatedFunctionTest.run();
            System.out.println("All tests passed!");
        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
