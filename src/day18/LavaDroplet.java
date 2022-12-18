package day18;

import java.util.ArrayList;
import java.util.HashSet;

public final class LavaDroplet {
    private final HashSet<Cube> lava;
    private final HashSet<Cube> borderLava;
    private HashSet<Cube> airPockets;
    private HashSet<Cube> externalAir;
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private int minZ;
    private int maxZ;

    public LavaDroplet(final HashSet<Cube> lava) {
        this.lava = lava;
        this.borderLava = new HashSet<Cube>();
    }

    public HashSet<Cube> getAirPockets() {
        return this.airPockets;
    }

    public HashSet<Cube> getExternalAir() {
        return this.externalAir;
    }

    public int findSurface() {
        final ArrayList<Cube> lava = new ArrayList<Cube>(this.lava);

        int sum = 0;
        for (int i = 0; i < lava.size(); i++) {
            int sides = 6;
            for (int j = 0; j < lava.size(); j++) {
                if (i != j && lava.get(i).connectedTo(lava.get(j))) {
                    sides--;
                }
            }

            sum += sides;
        }

        return sum;
    }

    public int findExteriorSurface() {
        this.findAirPockets();

        int sum = 0;
        for (final Cube air : this.externalAir) {
            final HashSet<Cube> neighbors = new HashSet<Cube>();
            neighbors.add(new Cube(air.x + 1, air.y, air.z));
            neighbors.add(new Cube(air.x - 1, air.y, air.z));
            neighbors.add(new Cube(air.x, air.y + 1, air.z));
            neighbors.add(new Cube(air.x, air.y - 1, air.z));
            neighbors.add(new Cube(air.x, air.y, air.z + 1));
            neighbors.add(new Cube(air.x, air.y, air.z - 1));

            for (final Cube neighbor : neighbors) {
                if (this.lava.contains(neighbor)) {
                    sum++;
                }
            }
        }

        return sum + this.borderLava.size();
    }

    private void findMinMax() {
        int minX = Integer.MAX_VALUE;
        int maxX = -Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = -Integer.MAX_VALUE;
        int minZ = Integer.MAX_VALUE;
        int maxZ = -Integer.MAX_VALUE;
        for (final Cube cube : lava) {
            if (cube.x > maxX) {
                maxX = cube.x;
            }
            if (cube.x < minX) {
                minX = cube.x;
            }
            if (cube.y > maxY) {
                maxY = cube.y;
            }
            if (cube.y < minY) {
                minY = cube.y;
            }
            if (cube.z > maxZ) {
                maxZ = cube.z;
            }
            if (cube.z < minZ) {
                minZ = cube.z;
            }
        }

        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    private void findAirPockets() {
        this.findMinMax();

        final HashSet<Cube> potentialAirPockets = new HashSet<Cube>();
        final HashSet<Cube> externalAirs = new HashSet<Cube>();
        for (int i = this.minX; i <= this.maxX; i++) {
            for (int j = this.minY; j <= this.maxY; j++) {
                for (int k = this.minZ; k <= this.maxZ; k++) {
                    final Cube potentialAirPocket = new Cube(i, j, k);

                    if (i == this.minX || i == this.maxX || j == this.minY || j == this.maxY || k == this.minZ
                            || k == this.maxZ) {
                        if (!this.lava.contains(potentialAirPocket)) {
                            externalAirs.add(potentialAirPocket);
                        } else {
                            this.borderLava.add(potentialAirPocket);
                        }
                    }

                    if (!this.lava.contains(potentialAirPocket)) {
                        potentialAirPockets.add(potentialAirPocket);
                    }
                }
            }
        }
        this.airPockets = potentialAirPockets;
        for (final Cube air : externalAirs) {
            this.fillExternalAir(air);
        }
    }

    private void fillExternalAir(final Cube air) {
        this.externalAir = new HashSet<Cube>();
        this.externalAir.add(air);

        final ArrayList<Cube> queue = new ArrayList<Cube>();
        queue.add(air);
        while (!queue.isEmpty()) {
            final Cube potentialExternalAir = queue.remove(queue.size() - 1);

            final HashSet<Cube> neighbors = new HashSet<Cube>();
            neighbors.add(new Cube(potentialExternalAir.x + 1, potentialExternalAir.y, potentialExternalAir.z));
            neighbors.add(new Cube(potentialExternalAir.x - 1, potentialExternalAir.y, potentialExternalAir.z));
            neighbors.add(new Cube(potentialExternalAir.x, potentialExternalAir.y + 1, potentialExternalAir.z));
            neighbors.add(new Cube(potentialExternalAir.x, potentialExternalAir.y - 1, potentialExternalAir.z));
            neighbors.add(new Cube(potentialExternalAir.x, potentialExternalAir.y, potentialExternalAir.z + 1));
            neighbors.add(new Cube(potentialExternalAir.x, potentialExternalAir.y, potentialExternalAir.z - 1));

            neighbors.removeIf((c) -> c.x < this.minX || c.x > this.maxX || c.y < this.minY || c.y > this.maxY
                    || c.z < this.minZ || c.z > this.maxZ);

            for (final Cube neighbor : neighbors) {
                if (!this.lava.contains(neighbor) && !this.externalAir.contains(neighbor)) {
                    this.airPockets.remove(neighbor);
                    this.externalAir.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }
}
