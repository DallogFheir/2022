package day12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        final File file = new File("./src/day12/input.txt");

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
        final HeightMap heightMap = Main.parseInput(lines);
        return Main.findFewestSteps(heightMap);

    }

    private static int part_2(final ArrayList<String> lines) {
        final HeightMap heightMap = Main.parseInput(lines);

        final ArrayList<Point> lowestElevations = heightMap.findLowestElevations();
        int min = 0;

        for (Point lowestElevation : lowestElevations) {
            heightMap.setStart(lowestElevation);
            try {
                final int steps = Main.findFewestSteps(heightMap);

                if (min == 0 || steps < min) {
                    min = steps;
                }
            } catch (IllegalStateException err) {

            }
        }

        return min;
    }

    private static HeightMap parseInput(final ArrayList<String> lines) {
        final ArrayList<ArrayList<Integer>> heightMap = new ArrayList<ArrayList<Integer>>();
        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;

        for (int i = 0; i < lines.size(); i++) {
            final String line = lines.get(i);
            final ArrayList<Integer> currentRow = new ArrayList<Integer>();

            final char[] chars = line.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                final char c = chars[j];

                int height = (int) c - 97;
                if (c == 'S') {
                    height = 0;
                    startX = j;
                    startY = i;
                } else if (c == 'E') {
                    height = 26;
                    endX = j;
                    endY = i;
                }

                currentRow.add(height);
            }

            heightMap.add(currentRow);
        }

        return new HeightMap(heightMap, new Point(startX, startY), new Point(endX, endY));
    }

    private static int findFewestSteps(HeightMap heightMap) {
        final LinkedList<Node> queue = new LinkedList<Node>();
        queue.add(new Node(heightMap.getStart(), 0));
        final HashSet<Point> alreadyVisited = new HashSet<Point>();

        while (!queue.isEmpty()) {
            final Node node = queue.remove();

            if (alreadyVisited.contains(node.point)) {
                continue;
            }

            if (node.point.equals(heightMap.end)) {
                return node.length;
            }

            final ArrayList<Point> viableMoves = heightMap.getViableNeighbors(node.point);
            for (final Point viableMove : viableMoves) {
                if (!alreadyVisited.contains(viableMove)) {
                    queue.add(new Node(viableMove, node.length + 1));
                }
            }

            alreadyVisited.add(node.point);
        }

        throw new IllegalStateException("Impossible to get to the end at " + heightMap.end);
    }
}