package functions;

public class ComprehensiveTestRunner {
    public static void main(String[] args) {
        System.out.println("=== Running Comprehensive Function Tests ===");
        
        try {
            System.out.println("\n1. Testing ArrayTabulatedFunction...");
            ArrayTabulatedFunctionTest.run();
            System.out.println("✓ ArrayTabulatedFunction tests passed");
            
            System.out.println("\n2. Testing Complex Function Compositions...");
            ComplexFunctionCompositionTest.run();
            System.out.println("✓ Complex composition tests passed");
            
            System.out.println("\n3. Testing Numerical Methods with Tabulated Functions...");
            NumericalMethodsWithTabulatedTest.run();
            System.out.println("✓ Numerical methods tests passed");
            
            System.out.println("\n4. Testing Performance and Stress Scenarios...");
            PerformanceAndStressTest.run();
            System.out.println("✓ Performance and stress tests passed");
            
            System.out.println("\n5. Testing Compatibility...");
            CompatibilityTest.run();
            System.out.println("✓ Compatibility tests passed");
            
            System.out.println("\n=== All Tests Completed Successfully! ===");
            
        } catch (Exception e) {
            System.out.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
