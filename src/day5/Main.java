package day5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        final File file = new File("./src/day5/input.txt");

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

    private static String part_1(ArrayList<String> lines) {
        Crane crane = Main.parseInput(lines, "9000");
        crane.executeInstructions();

        return crane.readMessage();
    }

    private static String part_2(ArrayList<String> lines) {
        Crane9001 crane = (Crane9001) Main.parseInput(lines, "9001");
        crane.executeInstructions();

        return crane.readMessage();
    }

    private static Crane parseInput(ArrayList<String> lines, String version) {
        final ArrayList<String> crates = new ArrayList<String>();
        final ArrayList<String> instructions = new ArrayList<String>();

        ArrayList<String> arrToaddTo = crates;
        for (String line : lines) {
            if (line.equals("")) {
                arrToaddTo = instructions;
            } else {
                arrToaddTo.add(line);
            }

        }

        switch (version) {
            case "9000":
                return Crane.parseFromStrings(crates, instructions);
            case "9001":
                return Crane9001.parseFromStrings(crates, instructions);
            default:
                throw new IllegalArgumentException("Unknown version: " + version);
        }
    }
}