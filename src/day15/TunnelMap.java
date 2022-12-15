package day15;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TunnelMap {
    public final HashSet<Sensor> sensors;
    public final HashSet<Point> beacons;

    public TunnelMap(final HashSet<Sensor> sensors) {
        this.sensors = sensors;
        this.beacons = new HashSet<Point>();

        for (final Sensor sensor : sensors) {
            this.beacons.add(sensor.closestBeacon);
        }
    }

    @Override
    public String toString() {
        return String.format("TunnelMap[%s]", this.sensors);
    }

    public Ranges findImpossibleBeacons(int index) {
        return this.findImpossibleBeacons(index, null, null);
    }

    public Ranges findImpossibleBeacons(int index, Integer searchRangeMin, Integer searchRangeMax) {
        final Ranges ranges = (searchRangeMin != null && searchRangeMax != null)
                ? new Ranges(searchRangeMax, searchRangeMin)
                : new Ranges();

        for (final Sensor sensor : this.sensors) {
            final Point sensorPoint = sensor.sensor;
            final Point beaconPoint = sensor.closestBeacon;

            final int distance = sensorPoint.distanceTo(beaconPoint);

            if (index <= sensorPoint.y + distance && index >= sensorPoint.y - distance) {
                final int yLine = Math.abs(sensorPoint.y - index);
                int xRange = distance - yLine;

                int xStart = sensorPoint.x - xRange;
                int xEnd = sensorPoint.x + xRange;
                if (beaconPoint.x == xStart) {
                    xStart++;
                } else if (beaconPoint.x == xEnd) {
                    xEnd--;
                }

                ranges.add(new Range(xStart, xEnd));
            }

        }

        return ranges;
    }

    public Point findPossibleBeacon(int searchRangeMax) {
        for (int i = 0; i <= searchRangeMax; i++) {
            Ranges impossibleBeaconsRanges = this.findImpossibleBeacons(i, 0, searchRangeMax);

            if (impossibleBeaconsRanges.count() != searchRangeMax + 1) {
                final ArrayList<Integer> gaps = impossibleBeaconsRanges.findGaps();

                for (final int gap : gaps) {
                    final Point point = new Point(gap, i);

                    if (!this.beacons.contains(point)) {
                        return point;
                    }
                }
            }
        }

        throw new IllegalStateException("No possible place for beacon found.");
    }

    public static TunnelMap parseFromLines(ArrayList<String> lines) {
        final HashSet<Sensor> sensors = new HashSet<Sensor>();

        final Pattern pattern = Pattern.compile(
                "^Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)$");
        for (final String line : lines) {
            final Matcher matcher = pattern.matcher(line);
            matcher.matches();

            final int sensorX = Integer.parseInt(matcher.group(1));
            final int sensorY = Integer.parseInt(matcher.group(2));
            final Point sensorPoint = new Point(sensorX, sensorY);

            final int beaconX = Integer.parseInt(matcher.group(3));
            final int beaconY = Integer.parseInt(matcher.group(4));
            final Point beaconPoint = new Point(beaconX, beaconY);

            final Sensor sensor = new Sensor(sensorPoint, beaconPoint);
            sensors.add(sensor);
        }

        return new TunnelMap(sensors);
    }
}
