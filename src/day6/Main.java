package day6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import day6.errors.NoMarkerFoundError;

public class Main {
    public static void main(String[] args) {
        final File file = new File("./src/day6/input.txt");

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

    private static int part_1(ArrayList<String> lines) {
        final Signal signal = new Signal(lines.get(0));

        try {
            return signal.findPacketMarker();
        } catch (NoMarkerFoundError err) {
            System.out.println(err.getMessage());
            return -1;
        }
    }

    private static int part_2(ArrayList<String> lines) {
        final Signal signal = new Signal(lines.get(0));

        try {
            return signal.findMessageMarker();
        } catch (NoMarkerFoundError err) {
            System.out.println(err.getMessage());
            return -1;
        }
    }
}