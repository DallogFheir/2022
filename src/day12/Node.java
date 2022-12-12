package day12;

public class Node {
    public final Point point;
    public final int length;

    public Node(final Point point, final int length) {
        this.point = point;
        this.length = length;
    }

    @Override
    public String toString() {
        return String.format("Node(%s, %d)", this.point, this.length);
    }
}
