package day12;

import java.util.ArrayList;

public class HeightMap {
    public final ArrayList<ArrayList<Integer>> heightMap;
    private Point start;
    public final Point end;

    public HeightMap(final ArrayList<ArrayList<Integer>> heightMap, final Point start, final Point end) {
        this.heightMap = heightMap;
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return this.start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return String.format("HeightMap(%s, %s, %s)", this.heightMap, this.start, this.end);
    }

    public int getHeightAtPoint(final Point point) {
        return this.heightMap.get(point.y).get(point.x);
    }

    public boolean isInMap(final Point point) {
        return (point.x >= 0 && point.x < this.heightMap.get(0).size())
                && (point.y >= 0 && point.y < this.heightMap.size());
    }

    public ArrayList<Point> getViableNeighbors(final Point point) {
        final int currentHeight = this.getHeightAtPoint(point);

        final ArrayList<Point> neighbors = new ArrayList<Point>();
        neighbors.add(new Point(1, 0));
        neighbors.add(new Point(0, 1));
        neighbors.add(new Point(-1, 0));
        neighbors.add(new Point(0, -1));

        final ArrayList<Point> viableNeighbors = new ArrayList<Point>();
        for (final Point neighbor : neighbors) {
            final Point newPoint = new Point(point.x + neighbor.x, point.y + neighbor.y);

            if (this.isInMap(newPoint) && this.getHeightAtPoint(newPoint) <= currentHeight + 1) {
                viableNeighbors.add(newPoint);
            }
        }

        return viableNeighbors;
    }

    public ArrayList<Point> findLowestElevations() {
        final ArrayList<Point> lowestElevations = new ArrayList<Point>();

        for (int i = 0; i < this.heightMap.size(); i++) {
            for (int j = 0; j < this.heightMap.get(i).size(); j++) {
                if (this.heightMap.get(i).get(j) == 0) {
                    lowestElevations.add(new Point(j, i));
                }
            }
        }

        return lowestElevations;
    }
}
