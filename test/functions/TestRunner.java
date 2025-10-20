package functions;

public class TestRunner {
    public static void main(String[] args) {
        Class<?>[] tests = new Class<?>[]{
            IdentityFunctionTest.class,
            LinearFunctionTest.class,
            CompositeOfCompositeTest.class,
            NewtonMethodTest.class,
            NewtonZeroDerivativeTest.class,
            LinkedListConstructorAndBasicOpsTest.class,
            LinkedListDiscretizationTest.class,
            CompositionWithTabulatedTest.class,
            ArrayInsertTest.class,
            LinkedListRemoveTest.class
        };
        int passed = 0;
        int failed = 0;
        for (Class<?> t : tests) {
            try {
                t.getMethod("run").invoke(null);
                System.out.println(t.getSimpleName() + ": PASS");
                passed++;
            } catch (Throwable ex) {
                System.out.println(t.getSimpleName() + ": FAIL");
                ex.printStackTrace();
                failed++;
            }
        }
        System.out.println("Tests passed: " + passed);
        System.out.println("Tests failed: " + failed);
    }
}


