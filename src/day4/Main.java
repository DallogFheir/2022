package day4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        final File file = new File("./src/day4/input.txt");

        try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
            final ArrayList<String> lines = new ArrayList<String>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            System.out.println(Main.part_1(lines));
            System.out.println(Main.part_2(lines));
        } catch (FileNotFoundException err) {
            System.out.println("File not found.");
        } catch (IOException err) {
            System.out.println("IO error happened.");
        }
    }

    private static int part_1(ArrayList<String> lines) {
        return Main.getResult(lines, (r1, r2) -> r1.contains(r2) || r2.contains(r1));
    }

    private static int part_2(ArrayList<String> lines) {
        return Main.getResult(lines, (r1, r2) -> r1.overlapsWith(r2));
    }

    private static int getResult(ArrayList<String> lines, Callback function) {
        final ArrayList<SectionRange[]> ranges = Main.parseInput(lines);

        int count = 0;
        for (SectionRange[] range : ranges) {
            final SectionRange range1 = range[0];
            final SectionRange range2 = range[1];

            if (function.call(range1, range2)) {
                count++;
            }
        }

        return count;
    }

    private static ArrayList<SectionRange[]> parseInput(ArrayList<String> lines) {
        final ArrayList<SectionRange[]> result = new ArrayList<SectionRange[]>();

        for (String line : lines) {
            final String[] parts = line.split(",");
            final SectionRange[] ranges = new SectionRange[2];
            ranges[0] = SectionRange.parseFromString(parts[0]);
            ranges[1] = SectionRange.parseFromString(parts[1]);

            result.add(ranges);
        }

        return result;
    }
}