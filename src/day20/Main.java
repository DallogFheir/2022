package day20;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(final String[] args) {
        final File file = new File("./src/day20/input.txt");

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

    private static long part_1(final ArrayList<String> lines) {
        final Coords coords = Coords.parseFromLines(lines);

        coords.mix();

        final int zeroIndex = coords.coords.indexOf(new CoordInteger(0));
        final int after1000 = (zeroIndex + 1000) % coords.coords.size();
        final int after2000 = (zeroIndex + 2000) % coords.coords.size();
        final int after3000 = (zeroIndex + 3000) % coords.coords.size();

        return coords.coords.get(after1000).number + coords.coords.get(after2000).number
                + coords.coords.get(after3000).number;
    }

    private static long part_2(final ArrayList<String> lines) {
        final ArrayList<CoordInteger> coordIntegers = new ArrayList<CoordInteger>();
        for (int i = 0; i < lines.size(); i++) {
            final long integer = Integer.parseInt(lines.get(i));
            coordIntegers.add(new CoordInteger(integer * 811589153, i));
        }

        final Coords coords = new Coords(coordIntegers);

        for (int i = 0; i < 10; i++) {
            coords.mix();
        }

        final int zeroIndex = coords.coords.indexOf(new CoordInteger(0));
        final int after1000 = (zeroIndex + 1000) % coords.coords.size();
        final int after2000 = (zeroIndex + 2000) % coords.coords.size();
        final int after3000 = (zeroIndex + 3000) % coords.coords.size();

        return coords.coords.get(after1000).number + coords.coords.get(after2000).number
                + coords.coords.get(after3000).number;
    }
}