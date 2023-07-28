package day09;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public final class RopeBridge {
    private final ArrayList<Motion> motions;
    private final HashSet<Point> tailLocations;
    private final int howManyKnots;

    public RopeBridge(ArrayList<Motion> motions, int howManyKnots) {
        this.motions = motions;
        this.tailLocations = new HashSet<Point>();
        this.tailLocations.add(new Point(0, 0));
        this.howManyKnots = howManyKnots;
        this.simulateMotions();
    }

    public HashSet<Point> getTailLocations() {
        return this.tailLocations;
    }

    private void simulateMotions() {
        final Point[] knots = new Point[this.howManyKnots];
        for (int i = 0; i < knots.length; i++) {
            knots[i] = new Point(0, 0);
        }

        for (Motion motion : this.motions) {
            final Direction direction = motion.getDirection();
            final int distance = motion.getDistance();

            for (int i = 0; i < distance; i++) {
                knots[0] = knots[0].getPointAfterMotion(direction);

                for (int j = 0; j < knots.length - 1; j++) {
                    if (!knots[j + 1].isTouching(knots[j])) {
                        knots[j + 1] = knots[j + 1].moveTowards(knots[j]);

                        if (j + 1 == knots.length - 1) {
                            this.tailLocations.add(knots[j + 1]);
                        }
                    }
                }
            }
        }
    }

    public static RopeBridge parseFromMotions(ArrayList<String> motions, int howManyKnots) {
        final ArrayList<Motion> parsedMotions = new ArrayList<Motion>();

        final HashMap<String, Direction> dirTrans = new HashMap<String, Direction>();
        dirTrans.put("U", Direction.UP);
        dirTrans.put("D", Direction.DOWN);
        dirTrans.put("L", Direction.LEFT);
        dirTrans.put("R", Direction.RIGHT);

        for (String motion : motions) {
            final String[] parts = motion.split(" ");
            final Direction direction = dirTrans.get(parts[0]);
            final int distance = Integer.parseInt(parts[1]);

            final Motion parsedMotion = new Motion(direction, distance);
            parsedMotions.add(parsedMotion);
        }

        return new RopeBridge(parsedMotions, howManyKnots);
    }
}
