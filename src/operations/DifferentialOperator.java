package operations;

public interface DifferentialOperator<T> {
    T derive(T function);
}


