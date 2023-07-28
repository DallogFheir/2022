package day09;

public final class Motion {
    private final Direction direction;
    private final int distance;

    public Motion(Direction direction, int distance) {
        this.direction = direction;
        this.distance = distance;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public int getDistance() {
        return this.distance;
    }
}
