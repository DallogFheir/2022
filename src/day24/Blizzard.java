package day24;

public final class Blizzard {
    public final int x;
    public final int y;
    public final Direction direction;

    public Blizzard(final int x, final int y, final Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s)", this.x, this.y, this.direction);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Blizzard)) {
            return false;
        }

        Blizzard other = (Blizzard) obj;
        return this.x == other.x && this.y == other.y && this.direction == other.direction;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + Integer.hashCode(this.x);
        result = 31 * result + Integer.hashCode(this.y);
        result = 31 * result + this.direction.hashCode();
        return result;
    }
}
