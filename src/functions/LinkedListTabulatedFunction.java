package functions;

public final class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Removable {
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
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException("xValues and yValues lengths differ");
        }
        for (int i = 0; i < xValues.length; i++) {
            addNode(xValues[i], yValues[i]);
        }
    }

    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (source == null) throw new IllegalArgumentException("source is null");
        if (count <= 0) throw new IllegalArgumentException("count must be positive");
        double a = xFrom;
        double b = xTo;
        if (a > b) {
            double t = a; a = b; b = t;
        }
        if (count == 1) {
            addNode(a, source.apply(a));
            return;
        }
        double step = (count == 1) ? 0.0 : (b - a) / (count - 1);
        for (int i = 0; i < count; i++) {
            double x = a + i * step;
            addNode(x, source.apply(x));
        }
    }

    private Node getNode(int index) {
        if (index < 0 || index >= count) throw new IndexOutOfBoundsException();
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
        return getNode(index).x;
    }

    @Override
    public double getY(int index) {
        return getNode(index).y;
    }

    @Override
    public void setY(int index, double value) {
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
        if (index < 0 || index >= count) throw new IndexOutOfBoundsException();
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
}


