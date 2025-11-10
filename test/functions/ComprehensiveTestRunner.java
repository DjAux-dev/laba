package functions;

public class ComprehensiveTestRunner {
    public static void main(String[] args) {
        System.out.println("=== Running Comprehensive Function Tests ===");
        
        try {
            System.out.println("\n1. Testing ArrayTabulatedFunction...");
            ArrayTabulatedFunctionTest.run();
            System.out.println("✓ ArrayTabulatedFunction tests passed");
            
            System.out.println("\n2. Testing Validation and Exceptions...");
            ValidationAndExceptionsTest.run();
            System.out.println("✓ Validation and exception tests passed");
            
            System.out.println("\n3. Testing Array Iterator...");
            ArrayIteratorTest.run();
            System.out.println("✓ Array iterator tests passed");
            
            System.out.println("\n4. Testing Operations Service...");
            OperationsServiceTest.run();
            System.out.println("✓ Operations service tests passed");
            
            System.out.println("\n5. Testing Operations Service Binary Ops...");
            OperationsServiceBinaryOpsTest.run();
            System.out.println("✓ Operations service binary ops tests passed");
            
            System.out.println("\n6. Testing Differential Operators...");
            DifferentialOperatorsTest.run();
            System.out.println("✓ Differential operators tests passed");
            
            System.out.println("\n7. Testing Complex Function Compositions...");
            ComplexFunctionCompositionTest.run();
            System.out.println("✓ Complex composition tests passed");
            
            System.out.println("\n8. Testing Numerical Methods with Tabulated Functions...");
            NumericalMethodsWithTabulatedTest.run();
            System.out.println("✓ Numerical methods tests passed");
            
            System.out.println("\n9. Testing Performance and Stress Scenarios...");
            PerformanceAndStressTest.run();
            System.out.println("✓ Performance and stress tests passed");
            
            System.out.println("\n10. Testing Compatibility...");
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
