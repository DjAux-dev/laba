package functions;

public final class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Removable, Insertable {
    private static final long serialVersionUID = 3L;

    static class Node implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        public Node next;
        public Node prev;
        public double x;
        public double y;
    }

    private Node head;

    private void addNode(double x, double y) {
        Node node = new Node();
        node.x = x;
        node.y = y;
        if (head == null) {
            head = node;
            head.next = head;
            head.prev = head;
        } else {
            Node last = head.prev;
            last.next = node;
            node.prev = last;
            node.next = head;
            head.prev = node;
        }
        count++;
    }

    public LinkedListTabulatedFunction(double[] xValues, double[] yValues) {
        if (xValues == null || yValues == null) {
            throw new IllegalArgumentException("xValues and yValues must not be null");
        }
        if (xValues.length < 2) {
            throw new IllegalArgumentException("Tabulated function length must be at least 2 points");
        }
        checkLengthIsTheSame(xValues, yValues);
        checkSorted(xValues);
        for (int i = 0; i < xValues.length; i++) {
            addNode(xValues[i], yValues[i]);
        }
    }

    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (source == null) throw new IllegalArgumentException("source is null");
        if (count < 2) throw new IllegalArgumentException("Tabulated function length must be at least 2 points");
        double a = xFrom;
        double b = xTo;
        if (a > b) {
            double t = a; a = b; b = t;
        }
        double step = (b - a) / (count - 1);
        for (int i = 0; i < count; i++) {
            double x = a + i * step;
            addNode(x, source.apply(x));
        }
    }

    private Node getNode(int index) {
        if (index < 0 || index >= count) throw new IllegalArgumentException("Index out of bounds: " + index);
        if (head == null) throw new IllegalStateException("Empty list");
        if (index <= count / 2) {
            Node cur = head;
            for (int i = 0; i < index; i++) cur = cur.next;
            return cur;
        } else {
            Node cur = head.prev;
            for (int i = count - 1; i > index; i--) cur = cur.prev;
            return cur;
        }
    }

    @Override
    public double getX(int index) {
        if (index < 0 || index >= count) throw new IllegalArgumentException("Index out of bounds: " + index);
        return getNode(index).x;
    }

    @Override
    public double getY(int index) {
        if (index < 0 || index >= count) throw new IllegalArgumentException("Index out of bounds: " + index);
        return getNode(index).y;
    }

    @Override
    public void setY(int index, double value) {
        if (index < 0 || index >= count) throw new IllegalArgumentException("Index out of bounds: " + index);
        getNode(index).y = value;
    }

    @Override
    public int indexOfX(double x) {
        if (head == null) return -1;
        Node cur = head;
        for (int i = 0; i < count; i++) {
            if (cur.x == x) return i;
            cur = cur.next;
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        if (head == null) return -1;
        Node cur = head;
        for (int i = 0; i < count; i++) {
            if (cur.y == y) return i;
            cur = cur.next;
        }
        return -1;
    }

    @Override
    public double leftBound() {
        if (head == null) throw new IllegalStateException("Empty list");
        return head.x;
    }

    @Override
    public double rightBound() {
        if (head == null) throw new IllegalStateException("Empty list");
        return head.prev.x;
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= count) throw new IllegalArgumentException("Index out of bounds: " + index);
        if (head == null) throw new IllegalStateException("Empty list");
        Node node = getNode(index);
        if (count == 1) {
            head = null;
            count = 0;
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        if (node == head) {
            head = node.next;
        }
        count--;
    }

    @Override
    public void insert(double x, double y) {
        // Если список пустой, просто добавляем узел
        if (head == null) {
            addNode(x, y);
            return;
        }
        
        // Проверяем, есть ли уже узел с таким x
        int existingIndex = indexOfX(x);
        if (existingIndex >= 0) {
            // Если узел с таким x уже существует, заменяем y
            getNode(existingIndex).y = y;
            return;
        }
        
        // Находим место для вставки
        Node newNode = new Node();
        newNode.x = x;
        newNode.y = y;
        
        // Если новый узел должен быть в начале (x меньше всех существующих)
        if (x < head.x) {
            newNode.next = head;
            newNode.prev = head.prev;
            head.prev.next = newNode;
            head.prev = newNode;
            head = newNode; // Обновляем головную ссылку
            count++;
            return;
        }
        
        // Ищем место для вставки
        Node current = head;
        while (current.next != head && current.next.x < x) {
            current = current.next;
        }
        
        // Вставляем узел после current
        newNode.next = current.next;
        newNode.prev = current;
        current.next.prev = newNode;
        current.next = newNode;
        count++;
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (count == 0) return -1;
        if (x < head.x) throw new IllegalArgumentException("x is less than left bound");
        if (x >= head.prev.x) return count - 1;
        
        Node cur = head;
        for (int i = 0; i < count - 1; i++) {
            if (x >= cur.x && x < cur.next.x) {
                return i;
            }
            cur = cur.next;
        }
        return count - 1;
    }

    @Override
    protected double extrapolateLeft(double x) {
        return interpolate(x, head.x, head.next.x, head.y, head.next.y);
    }

    @Override
    protected double extrapolateRight(double x) {
        Node last = head.prev;
        Node prevLast = last.prev;
        return interpolate(x, prevLast.x, last.x, prevLast.y, last.y);
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        if (floorIndex < 0) return extrapolateLeft(x);
        if (floorIndex >= count - 1) return extrapolateRight(x);

        Node leftNode = getNode(floorIndex);
        Node rightNode = leftNode.next;
        if (x < leftNode.x || x > rightNode.x) {
            throw new exceptions.InterpolationException("x is out of interpolation interval");
        }
        return interpolate(x, leftNode.x, rightNode.x, leftNode.y, rightNode.y);
    }

    @Override
    public java.util.Iterator<Point> iterator() {
        return new java.util.Iterator<Point>() {
            private Node node = head;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public Point next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException("No more points");
                }
                Point p = new Point(node.x, node.y);
                // advance node; if we reached the last (head.prev), null out to stop
                if (node.next == head) {
                    node = null;
                } else {
                    node = node.next;
                }
                return p;
            }
        };
    }
}


