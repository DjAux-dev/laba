package functions;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Insertable {
    private static final class Node {
        final double x;
        double y;
        Node prev;
        Node next;
        Node(double x, double y) { this.x = x; this.y = y; }
    }

    private Node head;
    private Node tail;
    protected int count;

    public LinkedListTabulatedFunction() {
        this.head = null;
        this.tail = null;
        this.count = 0;
    }

    public LinkedListTabulatedFunction(double[] xValues, double[] yValues) {
        if (xValues == null || yValues == null) {
            throw new IllegalArgumentException("xValues and yValues must not be null");
        }
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException("xValues and yValues must be the same length");
        }
        if (xValues.length == 0) {
            throw new IllegalArgumentException("xValues and yValues must not be empty");
        }
        this.count = 0;
        for (int i = 0; i < xValues.length; i++) {
            append(xValues[i], yValues[i]);
        }
    }

    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (source == null) {
            throw new IllegalArgumentException("source function must not be null");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("count must be positive");
        }
        if (xFrom > xTo) {
            double tmp = xFrom; xFrom = xTo; xTo = tmp;
        }
        this.count = 0;
        if (count == 1) {
            append(xFrom, source.apply(xFrom));
            return;
        }
        if (xFrom == xTo) {
            double y = source.apply(xFrom);
            for (int i = 0; i < count; i++) {
                append(xFrom, y);
            }
            return;
        }
        double step = (xTo - xFrom) / (count - 1);
        for (int i = 0; i < count; i++) {
            double x = xFrom + step * i;
            append(x, source.apply(x));
        }
    }

    private void append(double x, double y) {
        Node node = new Node(x, y);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        count++;
    }

    public int getCount() { return count; }

    public double getX(int index) {
        return nodeAt(index).x;
    }

    public double getY(int index) {
        return nodeAt(index).y;
    }

    public void setY(int index, double value) {
        nodeAt(index).y = value;
    }

    protected double leftBound() { return head.x; }

    protected double rightBound() { return tail.x; }

    protected int indexOfX(double x) {
        int i = 0;
        for (Node n = head; n != null; n = n.next, i++) {
            if (n.x == x) return i;
        }
        return -1;
    }

    protected int indexOfY(double y) {
        int i = 0;
        for (Node n = head; n != null; n = n.next, i++) {
            if (n.y == y) return i;
        }
        return -1;
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (count == 1) return 0;
        if (x < head.x) return 0;
        if (x > tail.x) return count;
        int i = 0;
        Node n = head;
        while (n != null && n.x <= x) {
            if (n.x == x) return i;
            n = n.next;
            i++;
        }
        return Math.max(0, i - 1);
    }

    @Override
    protected double extrapolateLeft(double x) {
        if (count == 1) return head.y;
        Node n0 = head;
        Node n1 = head.next;
        return interpolate(x, n0.x, n1.x, n0.y, n1.y);
    }

    @Override
    protected double extrapolateRight(double x) {
        if (count == 1) return tail.y;
        Node n0 = tail.prev;
        Node n1 = tail;
        return interpolate(x, n0.x, n1.x, n0.y, n1.y);
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        if (count == 1) return head.y;
        Node left = nodeAt(floorIndex);
        Node right = left.next != null ? left.next : left;
        if (left.x == right.x) return left.y;
        return interpolate(x, left.x, right.x, left.y, right.y);
    }

    private Node nodeAt(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + count);
        }
        // select direction for efficiency
        Node n;
        if (index <= (count - 1) / 2) {
            n = head;
            for (int i = 0; i < index; i++) n = n.next;
        } else {
            n = tail;
            for (int i = count - 1; i > index; i--) n = n.prev;
        }
        return n;
    }

    @Override
    public void insert(double x, double y) {
        // empty list: append
        if (head == null) {
            append(x, y);
            return;
        }
        // traverse to find position or replacement
        Node current = head;
        while (current != null) {
            if (current.x == x) {
                current.y = y; // replace
                return;
            }
            if (x < current.x) {
                // insert before current
                Node node = new Node(x, y);
                node.next = current;
                node.prev = current.prev;
                if (current.prev != null) {
                    current.prev.next = node;
                } else {
                    head = node; // new head
                }
                current.prev = node;
                count++;
                return;
            }
            current = current.next;
        }
        // if we are here, x is greater than all existing, append at tail
        append(x, y);
    }
}


