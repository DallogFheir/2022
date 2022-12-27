package day24;

import java.util.HashSet;

public final class BlizzardResult {
    public final int steps;
    public final HashSet<Blizzard> blizzards;

    public BlizzardResult(final int steps, final HashSet<Blizzard> blizzards) {
        this.steps = steps;
        this.blizzards = blizzards;
    }
}
