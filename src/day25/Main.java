package day25;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(final String[] args) {
        final File file = new File("./src/day25/input.txt");

        try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
            final ArrayList<String> lines = new ArrayList<String>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            System.out.println("Part 1: " + Main.part_1(lines));
        } catch (FileNotFoundException err) {
            System.out.println("File not found.");
        } catch (IOException err) {
            System.out.println("IO error happened.");
        }
    }

    private static String part_1(final ArrayList<String> lines) {
        long sum = 0;

        for (final String line : lines) {
            final Snafu snafu = new Snafu(line);
            sum += snafu.decimalValue;
        }

        return Snafu.fromDecimal(sum).number;
    }
}