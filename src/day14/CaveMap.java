package day14;

import java.util.ArrayList;
import java.util.HashSet;

public class CaveMap {
    private final HashSet<Point> rocks;
    private HashSet<Point> sand;

    public CaveMap(HashSet<Point> rocks) {
        this.rocks = rocks;
        this.sand = new HashSet<Point>();
    }

    @Override
    public String toString() {
        final int[] borders = this.getBorders();
        String result = "";

        for (int y = borders[0]; y <= borders[2]; y++) {
            for (int x = borders[3]; x <= borders[1]; x++) {
                final Point point = new Point(x, y);

                if (x == 500 && y == 0) {
                    result += "+";
                } else {
                    final char symbol = this.rocks.contains(point) ? '#' : this.sand.contains(point) ? 'o' : '.';
                    result += symbol;
                }
            }

            result += "\n";
        }

        return result.substring(0, result.length() - 1);
    }

    public int[] getBorders() {
        final int[] borders = new int[4];
        borders[0] = Integer.MAX_VALUE;
        borders[1] = -Integer.MAX_VALUE;
        borders[2] = -Integer.MAX_VALUE;
        borders[3] = Integer.MAX_VALUE;

        for (final Point rock : this.rocks) {
            if (rock.x < borders[3]) {
                borders[3] = rock.x;
            }
            if (rock.x > borders[1]) {
                borders[1] = rock.x;
            }
            if (rock.y < borders[0]) {
                borders[0] = rock.y;
            }
            if (rock.y > borders[2]) {
                borders[2] = rock.y;
            }
        }

        if (borders[0] > 0) {
            borders[0] = 0;
        }
        if (borders[1] < 500) {
            borders[1] = 500;
        }
        if (borders[2] < 0) {
            borders[2] = 0;
        }
        if (borders[3] > 500) {
            borders[3] = 500;
        }

        return borders;
    }

    public boolean isOutsideBorder(Point point) {
        final int[] borders = this.getBorders();

        return point.x < borders[3] || point.x > borders[1] || point.y > borders[2];
    }

    public int simulateSand() {
        final int startX = 500;
        final int startY = 0;

        final HashSet<Point> rocksAndSand = new HashSet<Point>(rocks);
        final HashSet<Point> sand = new HashSet<Point>();

        int count = 0;
        while (true) {
            boolean movePossible = true;
            int currentX = startX;
            int currentY = startY;

            while (movePossible) {
                Point nextPoint = new Point(currentX, currentY + 1);

                if (this.isOutsideBorder(nextPoint)) {
                    this.sand = sand;
                    return count;
                }

                if (!rocksAndSand.contains(nextPoint)) {
                    currentY++;
                    continue;
                }

                nextPoint = new Point(currentX - 1, currentY + 1);
                if (!rocksAndSand.contains(nextPoint)) {
                    currentX--;
                    currentY++;
                    continue;
                }

                nextPoint = new Point(currentX + 1, currentY + 1);
                if (!rocksAndSand.contains(nextPoint)) {
                    currentX++;
                    currentY++;
                    continue;
                }

                final Point currentPoint = new Point(currentX, currentY);
                sand.add(currentPoint);
                rocksAndSand.add(currentPoint);
                movePossible = false;
            }

            count++;
        }
    }

    public boolean isFloor(Point point) {
        final int floorY = this.getBorders()[2] + 2;

        return point.y == floorY;
    }

    public int simulateSandWithFloor() {
        final int startX = 500;
        final int startY = 0;

        final HashSet<Point> rocksAndSand = new HashSet<Point>(rocks);

        int count = 0;
        while (true) {
            boolean movePossible = true;
            int currentX = startX;
            int currentY = startY;

            while (movePossible) {
                Point nextPoint = new Point(currentX, currentY + 1);

                if (!rocksAndSand.contains(nextPoint) && !this.isFloor(nextPoint)) {
                    currentY++;
                    continue;
                }

                nextPoint = new Point(currentX - 1, currentY + 1);
                if (!rocksAndSand.contains(nextPoint) && !this.isFloor(nextPoint)) {
                    currentX--;
                    currentY++;
                    continue;
                }

                nextPoint = new Point(currentX + 1, currentY + 1);
                if (!rocksAndSand.contains(nextPoint) && !this.isFloor(nextPoint)) {
                    currentX++;
                    currentY++;
                    continue;
                }

                final Point currentPoint = new Point(currentX, currentY);

                if (currentPoint.equals(new Point(startX, startY))) {
                    count++;
                    return count;
                }

                rocksAndSand.add(currentPoint);
                movePossible = false;
            }

            count++;
        }
    }

    public static CaveMap parseFromScan(final ArrayList<String> lines) {
        final HashSet<Point> rocks = new HashSet<Point>();

        for (final String line : lines) {
            final String[] scans = line.split(" -> ");

            int prevX = -1;
            int prevY = -1;
            for (final String scan : scans) {
                final String[] parts = scan.split(",");
                final int currentXTemp = Integer.parseInt(parts[0]);
                final int currentYTemp = Integer.parseInt(parts[1]);

                if (prevX == -1 && prevY == -1) {
                    prevX = currentXTemp;
                    prevY = currentYTemp;
                    continue;
                }

                int currentX = currentXTemp;
                int currentY = currentYTemp;
                if (currentX == prevX) {
                    final int addend = currentY > prevY ? -1 : 1;

                    while (currentY != prevY) {
                        final Point point = new Point(currentX, currentY);
                        rocks.add(point);

                        currentY += addend;
                    }
                } else if (currentY == prevY) {
                    final int addend = currentX > prevX ? -1 : 1;

                    while (currentX != prevX) {
                        final Point point = new Point(currentX, currentY);
                        rocks.add(point);

                        currentX += addend;
                    }
                } else {
                    throw new IllegalStateException("Invalid scan: " + scan + ", in line: " + line);
                }

                final Point point = new Point(currentX, currentY);
                rocks.add(point);

                prevX = currentXTemp;
                prevY = currentYTemp;
            }
        }

        return new CaveMap(rocks);
    }
}
