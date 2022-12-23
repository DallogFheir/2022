package day23;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map.Entry;

public final class ElfMap {
    private HashSet<Point> elves;

    public ElfMap(final HashSet<Point> elves) {
        this.elves = elves;
    }

    public void draw() {
        final int[] boundaries = this.getBoundaries();

        for (int i = boundaries[2]; i <= boundaries[3]; i++) {
            for (int j = boundaries[0]; j <= boundaries[1]; j++) {
                System.out.print(this.elves.contains(new Point(j, i)) ? "#" : ".");
            }
            System.out.println();
        }
    }

    public int getFreeTiles() {
        int count = 0;
        final int[] boundaries = this.getBoundaries();

        for (int i = boundaries[2]; i <= boundaries[3]; i++) {
            for (int j = boundaries[0]; j <= boundaries[1]; j++) {
                if (!this.elves.contains(new Point(j, i))) {
                    count++;
                }
            }
        }

        return count;
    }

    public int simulateMovements() {
        return this.simulateMovements(Integer.MAX_VALUE);
    }

    public int simulateMovements(final int rounds) {
        final String[] directions = { "N", "S", "W", "E" };
        int roundCount = 0;

        for (; roundCount < rounds; roundCount++) {
            final DefaultHashMap<Point, Point> proposedMoves = new DefaultHashMap<Point, Point>();

            for (final Point elf : this.elves) {
                // consider eight directions
                boolean elfesAround = false;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (!(i == 0 && j == 0)) {
                            final Point consideredPoint = new Point(elf.x + i, elf.y + j);

                            if (this.elves.contains(consideredPoint)) {
                                elfesAround = true;
                                break;
                            }
                        }
                    }
                }

                if (!elfesAround) {
                    proposedMoves.put(elf, elf);
                    continue;
                }

                // propose move
                int directionCount = roundCount % directions.length;
                boolean continueLoop = true;
                for (int i = 0; i < 4 && continueLoop; i++) {
                    final String direction = directions[directionCount];

                    switch (direction) {
                        case "N":
                            final Point p1 = new Point(elf.x, elf.y - 1);
                            final Point p2 = new Point(elf.x + 1, elf.y - 1);
                            final Point p3 = new Point(elf.x - 1, elf.y - 1);
                            if (!this.elves.contains(p1) && !this.elves.contains(p2) && !this.elves.contains(p3)) {
                                proposedMoves.put(p1, elf);
                                continueLoop = false;
                            }
                            break;
                        case "S":
                            final Point p4 = new Point(elf.x, elf.y + 1);
                            final Point p5 = new Point(elf.x + 1, elf.y + 1);
                            final Point p6 = new Point(elf.x - 1, elf.y + 1);
                            if (!this.elves.contains(p4) && !this.elves.contains(p5) && !this.elves.contains(p6)) {
                                proposedMoves.put(p4, elf);
                                continueLoop = false;
                            }
                            break;
                        case "W":
                            final Point p7 = new Point(elf.x - 1, elf.y);
                            final Point p8 = new Point(elf.x - 1, elf.y + 1);
                            final Point p9 = new Point(elf.x - 1, elf.y - 1);
                            if (!this.elves.contains(p7) && !this.elves.contains(p8) && !this.elves.contains(p9)) {
                                proposedMoves.put(p7, elf);
                                continueLoop = false;
                            }
                            break;
                        case "E":
                            final Point p10 = new Point(elf.x + 1, elf.y);
                            final Point p11 = new Point(elf.x + 1, elf.y + 1);
                            final Point p12 = new Point(elf.x + 1, elf.y - 1);
                            if (!this.elves.contains(p10) && !this.elves.contains(p11) && !this.elves.contains(p12)) {
                                proposedMoves.put(p10, elf);
                                continueLoop = false;
                            }
                            break;
                        default:
                            throw new IllegalStateException("Unknown direction: " + direction);
                    }

                    directionCount = (directionCount + 1) % directions.length;
                }

                if (continueLoop) {
                    proposedMoves.put(elf, elf);
                }
            }

            // move
            final HashSet<Point> newElves = new HashSet<Point>();

            for (final Entry<Point, ArrayList<Point>> proposedMove : proposedMoves.entrySet()) {
                final ArrayList<Point> proposers = proposedMove.getValue();

                if (proposers.size() == 1) {
                    newElves.add(proposedMove.getKey());
                } else {
                    for (final Point p : proposers) {
                        newElves.add(p);
                    }
                }
            }

            if (newElves.equals(this.elves)) {
                return roundCount + 1;
            }

            this.elves = newElves;
        }

        return roundCount + 1;
    }

    public static ElfMap parseFromLines(final ArrayList<String> lines) {
        final HashSet<Point> elves = new HashSet<Point>();

        for (int i = 0; i < lines.size(); i++) {
            final String line = lines.get(i);

            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '#') {
                    elves.add(new Point(j, i));
                }
            }
        }

        return new ElfMap(elves);
    }

    private int[] getBoundaries() {
        int minX = Integer.MAX_VALUE;
        int maxX = -Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = -Integer.MAX_VALUE;

        for (final Point elf : this.elves) {
            if (elf.x < minX) {
                minX = elf.x;
            }

            if (elf.x > maxX) {
                maxX = elf.x;
            }

            if (elf.y < minY) {
                minY = elf.y;
            }

            if (elf.y > maxY) {
                maxY = elf.y;
            }
        }

        return new int[] { minX, maxX, minY, maxY };
    }
}
