package day24;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

public final class BlizzardMap {
    private class Node {
        public final int x;
        public final int y;
        public final int steps;
        public final HashSet<Blizzard> blizzards;

        public Node(final int x, final int y, final int steps, final HashSet<Blizzard> blizzards) {
            this.x = x;
            this.y = y;
            this.steps = steps;
            this.blizzards = blizzards;
        }

        @Override
        public String toString() {
            return String.format("(%s, %s, %s steps)", this.x, this.y, this.steps);
        }

        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof Node)) {
                return false;
            }

            final Node other = (Node) obj;
            return this.x == other.x && this.y == other.y && this.blizzards.equals(other.blizzards);
        }

        @Override
        public int hashCode() {
            int result = 1;
            result = 31 * result + Integer.hashCode(this.x);
            result = 31 * result + Integer.hashCode(this.y);
            result = 31 * result + this.blizzards.hashCode();
            return result;
        }

        public int getDistance() {
            return Math.abs(this.x - sizeX) + Math.abs(this.y - sizeY);
        }
    }

    private HashSet<Blizzard> blizzards;
    private HashMap<HashSet<Blizzard>, HashSet<Blizzard>> blizzardCache;
    public final int sizeX;
    public final int sizeY;

    public BlizzardMap(final HashSet<Blizzard> blizzards, final int sizeX, final int sizeY) {
        this.blizzards = blizzards;
        this.blizzardCache = new HashMap<HashSet<Blizzard>, HashSet<Blizzard>>();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void draw() {
        final HashMap<Direction, Character> directionTrans = new HashMap<Direction, Character>();
        directionTrans.put(Direction.LEFT, '<');
        directionTrans.put(Direction.RIGHT, '>');
        directionTrans.put(Direction.UP, '^');
        directionTrans.put(Direction.DOWN, 'v');

        for (int i = 0; i < this.sizeY; i++) {
            for (int j = 0; j < this.sizeX; j++) {
                if ((i == 0 && j == 1) || (i == this.sizeY - 1 && j == this.sizeX - 2)) {
                    System.out.print(".");
                    continue;
                }

                if (i == 0 || i == this.sizeY - 1 || j == 0 || j == this.sizeX - 1) {
                    System.out.print("#");
                    continue;
                }

                int blizzardCount = 0;
                Direction blizzardToDraw = null;
                for (final Blizzard blizzard : this.blizzards) {
                    if (blizzard.x == j && blizzard.y == i) {
                        blizzardToDraw = blizzard.direction;
                        blizzardCount++;
                    }
                }

                if (blizzardCount == 0) {
                    System.out.print(".");
                } else if (blizzardCount > 1) {
                    System.out.print(blizzardCount);
                } else {
                    System.out.print(directionTrans.get(blizzardToDraw));
                }
            }
            System.out.println();
        }
    }

    public HashSet<Blizzard> moveBlizzards(final HashSet<Blizzard> blizzards) {
        final HashSet<Blizzard> newBlizzards = new HashSet<Blizzard>();

        for (final Blizzard blizzard : blizzards) {
            int x = blizzard.x;
            int y = blizzard.y;

            switch (blizzard.direction) {
                case UP:
                    y--;
                    break;
                case DOWN:
                    y++;
                    break;
                case LEFT:
                    x--;
                    break;
                case RIGHT:
                    x++;
                    break;
            }

            if (x == 0) {
                x = this.sizeX - 2;
            } else if (x == this.sizeX - 1) {
                x = 1;
            }

            if (y == 0) {
                y = this.sizeY - 2;
            } else if (y == this.sizeY - 1) {
                y = 1;
            }

            final Blizzard newBlizzard = new Blizzard(x, y, blizzard.direction);
            newBlizzards.add(newBlizzard);
        }

        return newBlizzards;
    }

    public void simulateMovement() {
        while (true) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (InterruptedException | IOException err) {
                System.out.println(err.getMessage());
            }

            this.draw();
            this.blizzards = this.moveBlizzards(this.blizzards);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException err) {
                System.out.println(err.getMessage());
            }
        }
    }

    public BlizzardResult findShortestPath(final Point start, final Point end) {
        return this.findShortestPath(start, end, this.blizzards);
    }

    public BlizzardResult findShortestPath(final Point start, final Point end, final HashSet<Blizzard> startBlizzards) {
        final PriorityQueue<Node> pq = new PriorityQueue<Node>(
                (a, b) -> (a.steps + a.getDistance()) - (b.steps + b.getDistance()));
        final HashSet<Node> visited = new HashSet<Node>();
        pq.add(new Node(start.x, start.y, 0, startBlizzards));

        while (!pq.isEmpty()) {
            final Node node = pq.poll();

            if (visited.contains(node)) {
                continue;
            }
            visited.add(node);

            if (node.x == end.x && node.y == end.y) {
                return new BlizzardResult(node.steps, node.blizzards);
            }

            if (!this.blizzardCache.containsKey(node.blizzards)) {
                this.blizzardCache.put(node.blizzards, this.moveBlizzards(node.blizzards));
            }
            final HashSet<Blizzard> movedBlizzards = this.blizzardCache.get(node.blizzards);

            final ArrayList<Point> possibleMoves = this.getPossibleMoves(node.x, node.y,
                    movedBlizzards);
            for (final Point possibleMove : possibleMoves) {
                pq.add(new Node(possibleMove.x, possibleMove.y, node.steps + 1, movedBlizzards));
            }
        }

        throw new IllegalStateException("Cannot find shortest path.");
    }

    public static BlizzardMap parseFromLines(final ArrayList<String> lines) {
        final HashMap<Character, Direction> directionTrans = new HashMap<Character, Direction>();
        directionTrans.put('<', Direction.LEFT);
        directionTrans.put('>', Direction.RIGHT);
        directionTrans.put('^', Direction.UP);
        directionTrans.put('v', Direction.DOWN);

        final HashSet<Blizzard> blizzards = new HashSet<Blizzard>();
        for (int i = 0; i < lines.size(); i++) {
            final String line = lines.get(i);

            for (int j = 0; j < line.length(); j++) {
                final char tile = line.charAt(j);

                if ("<>^v".contains(Character.toString(tile))) {
                    blizzards.add(new Blizzard(j, i, directionTrans.get(tile)));
                }
            }
        }

        return new BlizzardMap(blizzards, lines.get(0).length(), lines.size());
    }

    private ArrayList<Point> getPossibleMoves(final int x, final int y, final HashSet<Blizzard> blizzards) {
        final ArrayList<Point> possibleMoves = new ArrayList<Point>();
        final Point waitPoint = new Point(x, y);
        possibleMoves.add(waitPoint);
        possibleMoves.add(new Point(x + 1, y));
        possibleMoves.add(new Point(x - 1, y));
        possibleMoves.add(new Point(x, y + 1));
        possibleMoves.add(new Point(x, y - 1));

        possibleMoves.removeIf((p) -> (p.x <= 0 || p.x >= this.sizeX - 1 || p.y <= 0 || p.y >= this.sizeY - 1)
                && !(p.x == 1 && p.y == 0) && !(p.x == this.sizeX - 2 && p.y == this.sizeY - 1));

        for (final Blizzard blizzard : blizzards) {
            Point toRemove = null;

            for (final Point possibleMove : possibleMoves) {
                if (possibleMove.x == blizzard.x && possibleMove.y == blizzard.y) {
                    toRemove = possibleMove;
                    break;
                }
            }

            possibleMoves.remove(toRemove);
        }

        return possibleMoves;
    }
}
