package day18;

public final class Cube {
    public final int x;
    public final int y;
    public final int z;

    public Cube(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d, %d)", this.x, this.y, this.z);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Cube)) {
            return false;
        }

        Cube other = (Cube) obj;
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + Integer.hashCode(this.x);
        result = 31 * result + Integer.hashCode(this.y);
        result = 31 * result + Integer.hashCode(this.z);
        return result;
    }

    public boolean connectedTo(final Cube other) {
        return (this.x == other.x && this.y == other.y && Math.abs(this.z - other.z) == 1) ||
                (this.y == other.y && this.z == other.z && Math.abs(this.x - other.x) == 1) ||
                (this.x == other.x && this.z == other.z && Math.abs(this.y - other.y) == 1);
    }

    public static Cube parseFromCoordinates(final String coords) {
        final String[] split = coords.split(",");
        return new Cube(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

}
