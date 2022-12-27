package day24;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(final String[] args) {
        final File file = new File("./src/day24/input.txt");

        try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
            final ArrayList<String> lines = new ArrayList<String>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            System.out.println("Part 1: " + Main.part_1(lines));
            System.out.println("Part 2: " + Main.part_2(lines));
        } catch (FileNotFoundException err) {
            System.out.println("File not found.");
        } catch (IOException err) {
            System.out.println("IO error happened.");
        }
    }

    private static int part_1(final ArrayList<String> lines) {
        final BlizzardMap blizzardMap = BlizzardMap.parseFromLines(lines);

        final Point start = new Point(1, 0);
        final Point end = new Point(blizzardMap.sizeX - 2, blizzardMap.sizeY - 1);

        return blizzardMap.findShortestPath(start, end).steps;
    }

    private static int part_2(final ArrayList<String> lines) {
        final BlizzardMap blizzardMap = BlizzardMap.parseFromLines(lines);

        final Point start = new Point(1, 0);
        final Point end = new Point(blizzardMap.sizeX - 2, blizzardMap.sizeY - 1);

        final BlizzardResult firstToEnd = blizzardMap.findShortestPath(start, end);
        final BlizzardResult backToStart = blizzardMap.findShortestPath(end, start,
                firstToEnd.blizzards);
        final BlizzardResult secondToEnd = blizzardMap.findShortestPath(start, end,
                backToStart.blizzards);

        return firstToEnd.steps + backToStart.steps + secondToEnd.steps;
    }
}