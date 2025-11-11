package concurrent;

import functions.Point;
import functions.TabulatedFunction;

import java.util.Iterator;

public class SynchronizedTabulatedFunction implements TabulatedFunction {
    private final TabulatedFunction function;
    private final Object mutex;

    public SynchronizedTabulatedFunction(TabulatedFunction function) {
        this.function = function;
        this.mutex = this;
    }

    @Override
    public synchronized int getCount() {
        return function.getCount();
    }

    @Override
    public synchronized double getX(int index) {
        return function.getX(index);
    }

    @Override
    public synchronized double getY(int index) {
        return function.getY(index);
    }

    @Override
    public synchronized void setY(int index, double value) {
        function.setY(index, value);
    }

    @Override
    public synchronized int indexOfX(double x) {
        return function.indexOfX(x);
    }

    @Override
    public synchronized int indexOfY(double y) {
        return function.indexOfY(y);
    }

    @Override
    public synchronized double leftBound() {
        return function.leftBound();
    }

    @Override
    public synchronized double rightBound() {
        return function.rightBound();
    }

    @Override
    public synchronized double apply(double x) {
        return function.apply(x);
    }

    @Override
    public synchronized Iterator<Point> iterator() {
        return new SynchronizedIterator(function.iterator());
    }

    public interface Operation<T> {
        T apply(SynchronizedTabulatedFunction function);
    }

    public <T> T doSynchronously(Operation<? extends T> operation) {
        synchronized (mutex) {
            return operation.apply(this);
        }
    }

    private class SynchronizedIterator implements Iterator<Point> {
        private final Iterator<Point> iterator;

        SynchronizedIterator(Iterator<Point> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            synchronized (mutex) {
                return iterator.hasNext();
            }
        }

        @Override
        public Point next() {
            synchronized (mutex) {
                return iterator.next();
            }
        }
    }
}

