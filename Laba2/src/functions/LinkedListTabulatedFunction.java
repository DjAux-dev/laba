package functions;

class Node {
    public Node next;
    public Node prev;
    public double x;
    public double y;
}

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Removable {
    private Node head;

    private void addNode(double x, double y) {
        Node node = new Node();
        node.x = x;
        node.y = y;

        if (head == null) {
            head = node;
            node.next = node;
            node.prev = node;
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
            throw new IllegalArgumentException("xValues and yValues must have same length");
        }
        for (int i = 0; i < xValues.length; i++) {
            addNode(xValues[i], yValues[i]);
        }
    }

    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (source == null) {
            throw new IllegalArgumentException("source must not be null");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("count must be positive");
        }
        double from = xFrom;
        double to = xTo;
        if (from > to) {
            double tmp = from;
            from = to;
            to = tmp;
        }
        if (count == 1) {
            addNode(from, source.apply(from));
            return;
        }
        double step = (to - from) / (count - 1);
        for (int i = 0; i < count; i++) {
            double x = from + i * step;
            addNode(x, source.apply(x));
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
        Node node = head;
        for (int i = 0; i < count; i++) {
            if (node.x == x) return i;
            node = node.next;
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        if (head == null) return -1;
        Node node = head;
        for (int i = 0; i < count; i++) {
            if (node.y == y) return i;
            node = node.next;
        }
        return -1;
    }

    @Override
    public double leftBound() {
        if (head == null) throw new IllegalStateException("empty");
        return head.x;
    }

    @Override
    public double rightBound() {
        if (head == null) throw new IllegalStateException("empty");
        return head.prev.x;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
        if (head == null) {
            throw new IllegalStateException("empty");
        }
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
    public void remove(int index) {
        Node node = getNode(index);
        if (count == 1) {
            head = null;
            count = 0;
            return;
        }
        Node prev = node.prev;
        Node next = node.next;
        prev.next = next;
        next.prev = prev;
        if (node == head) {
            head = next;
        }
        count--;
    }
}


