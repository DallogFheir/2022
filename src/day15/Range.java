package day15;

public final class Range implements Comparable<Range> {
    public final int start;
    public final int end;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return String.format("Range(%d, %d)", this.start, this.end);
    }

    @Override
    public int compareTo(Range other) {
        return this.start == other.start ? this.end - other.end : this.start - other.start;
    }
}
