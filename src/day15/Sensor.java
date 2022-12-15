package day15;

public final class Sensor {
    public final Point sensor;
    public final Point closestBeacon;

    public Sensor(final Point sensor, final Point closestBeacon) {
        this.sensor = sensor;
        this.closestBeacon = closestBeacon;
    }

    @Override
    public String toString() {
        return String.format("Sensor[%s, %s]", this.sensor, this.closestBeacon);
    }
}
