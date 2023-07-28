package day04;

public final class SectionRange {
    private int start;
    private int end;

    public SectionRange(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public boolean contains(SectionRange other) {
        return this.start <= other.start && this.end >= other.end;
    }

    public boolean overlapsWith(SectionRange other) {
        return (this.end >= other.start && this.start <= other.end)
                || (other.end >= this.start && other.start <= this.end);
    }

    public static SectionRange parseFromString(String str) {
        String[] parts = str.split("-");
        final int start = Integer.parseInt(parts[0]);
        final int end = Integer.parseInt(parts[1]);

        return new SectionRange(start, end);
    }
}
