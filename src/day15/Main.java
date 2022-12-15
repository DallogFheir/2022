package day15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class Main {
    public static void main(final String[] args) {
        final File file = new File("./src/day15/input.txt");

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
        final TunnelMap tunnelMap = TunnelMap.parseFromLines(lines);

        return tunnelMap.findImpossibleBeacons(2000000).count();
    }

    private static BigInteger part_2(final ArrayList<String> lines) {
        final TunnelMap tunnelMap = TunnelMap.parseFromLines(lines);
        final Point onlyPossibleBeacon = tunnelMap.findPossibleBeacon(4000000);

        final BigInteger x = BigInteger.valueOf(onlyPossibleBeacon.x);
        final BigInteger y = BigInteger.valueOf(onlyPossibleBeacon.y);
        final BigInteger multiplicand = new BigInteger("4000000");
        return x.multiply(multiplicand).add(y);
    }
}