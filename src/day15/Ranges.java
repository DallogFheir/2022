package day15;

import java.util.ArrayList;
import java.util.Collections;

public final class Ranges {
    private final ArrayList<Range> ranges;
    private final Integer upperLimit;
    private final Integer lowerLimit;

    public Ranges() {
        this(null, null);
    }

    public Ranges(final Integer upperLimit, final Integer lowerLimit) {
        this.ranges = new ArrayList<Range>();
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
    }

    @Override
    public String toString() {
        return this.ranges.toString();
    }

    public void add(Range range) {
        if (upperLimit != null && lowerLimit != null) {
            final int newStart = range.start < lowerLimit ? lowerLimit : range.start;
            final int newEnd = range.end > upperLimit ? upperLimit : range.end;

            range = new Range(newStart, newEnd);
        }

        ranges.add(range);

        Collections.sort(this.ranges);

        boolean thereWereChanges = true;
        while (thereWereChanges) {
            thereWereChanges = false;

            for (int i = 0; i < this.ranges.size() - 1; i++) {
                final Range range1 = this.ranges.get(i);
                final Range range2 = this.ranges.get(i + 1);

                if (range2.start <= range1.end + 1) {
                    final int lowerStart = range1.start > range2.start ? range2.start : range1.start;
                    final int higherEnd = range1.end > range2.end ? range1.end : range2.end;
                    final Range mergedRange = new Range(lowerStart, higherEnd);

                    this.ranges.remove(i + 1);
                    this.ranges.remove(i);
                    this.ranges.add(i, mergedRange);

                    thereWereChanges = true;

                    break;
                }
            }
        }

    }

    public int count() {
        int count = 0;

        for (final Range range : this.ranges) {
            count += range.end - range.start + 1;
        }

        return count;
    }

    public ArrayList<Integer> findGaps() {
        final ArrayList<Integer> gaps = new ArrayList<Integer>();

        for (int i = 0; i < this.ranges.size() - 1; i++) {
            int start = this.ranges.get(i).end + 1;
            final int end = this.ranges.get(i + 1).start - 1;

            while (start <= end) {
                gaps.add(start);

                start++;
            }
        }

        return gaps;
    }
}
