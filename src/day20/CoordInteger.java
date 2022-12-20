package day20;

public final class CoordInteger {
    public final long number;
    private boolean moved;
    public final int order;

    public CoordInteger(final long number) {
        this.number = number;
        this.moved = false;
        this.order = -1;
    }

    public CoordInteger(final long number, final int order) {
        this.number = number;
        this.moved = false;
        this.order = order;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof CoordInteger)) {
            return false;
        }

        CoordInteger other = (CoordInteger) obj;
        return this.number == other.number;
    }

    @Override
    public String toString() {
        return Long.toString(this.number);
    }

    public boolean getMoved() {
        return this.moved;
    }

    public void setMoved() {
        this.moved = true;
    }
}
