package day20;

import java.util.ArrayList;

public final class Coords {
    public final ArrayList<CoordInteger> coords;

    public Coords(final ArrayList<CoordInteger> coords) {
        this.coords = coords;
    }

    @Override
    public String toString() {
        return this.coords.toString();
    }

    public void mix() {
        int order = 0;

        while (true) {
            int index = -1;
            for (int i = 0; i < this.coords.size(); i++) {
                if (this.coords.get(i).order == order) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                break;
            }

            final CoordInteger valueToMove = this.coords.get(index);

            this.coords.remove(index);
            final int indexToMoveTo = (int) ((index + valueToMove.number) % this.coords.size());

            if (indexToMoveTo > 0) {
                this.coords.add(indexToMoveTo, valueToMove);
            } else {
                this.coords.add(this.coords.size() + indexToMoveTo, valueToMove);
            }
            valueToMove.setMoved();

            order++;
        }
    }

    public static Coords parseFromLines(final ArrayList<String> lines) {
        final ArrayList<CoordInteger> coords = new ArrayList<CoordInteger>();

        for (int i = 0; i < lines.size(); i++) {
            final int integer = Integer.parseInt(lines.get(i));
            coords.add(new CoordInteger(integer, i));
        }

        return new Coords(coords);
    }
}
