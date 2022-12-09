package day9;

public final class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) {
            return false;
        }

        Point other = (Point) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.x ^ (this.x >>> 32));
        result = prime * result + (this.y ^ (this.y >>> 32));
        return result;
    }

    public Point getPointAfterMotion(Direction direction) {
        int newX = this.x;
        int newY = this.y;

        switch (direction) {
            case UP:
                newY += 1;
                break;
            case DOWN:
                newY -= 1;
                break;
            case LEFT:
                newX -= 1;
                break;
            case RIGHT:
                newX += 1;
                break;
        }

        return new Point(newX, newY);
    }

    public boolean isTouching(Point other) {
        if (this.x == other.x) {
            return Math.abs(this.y - other.y) <= 1;
        } else if (this.y == other.y) {
            return Math.abs(this.x - other.x) <= 1;
        } else {
            return Math.abs(this.x - other.x) + Math.abs(this.y - other.y) <= 2;
        }
    }

    public Point moveTowards(Point other) {
        int newX = this.x;
        int newY = this.y;

        if (this.x < other.x) {
            newX += 1;
        } else if (this.x > other.x) {
            newX -= 1;
        }

        if (this.y < other.y) {
            newY += 1;
        } else if (this.y > other.y) {
            newY -= 1;
        }

        return new Point(newX, newY);
    }
}
