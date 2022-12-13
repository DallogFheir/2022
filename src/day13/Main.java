package day13;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        final File file = new File("./src/day13/input.txt");

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
        final ArrayList<Pair<Packet>> packetPairs = Main.parsePacketPairsFromLines(lines);

        int sum = 0;
        for (int i = 0; i < packetPairs.size(); i++) {
            final Pair<Packet> pair = packetPairs.get(i);

            if (pair.getFirst().compareTo(pair.getSecond()) <= 0) {
                sum += i + 1;
            }
        }

        return sum;
    }

    private static int part_2(final ArrayList<String> lines) {
        final ArrayList<Packet> packets = Main.parsePacketsFromLines(lines);
        final Packet divider1 = Packet.parseFromString("[[2]]");
        final Packet divider2 = Packet.parseFromString("[[6]]");
        packets.add(divider1);
        packets.add(divider2);

        Collections.sort(packets, (a, b) -> a.compareTo(b));

        final int index1 = packets.indexOf(divider1) + 1;
        final int index2 = packets.indexOf(divider2) + 1;

        return index1 * index2;
    }

    private static ArrayList<Pair<Packet>> parsePacketPairsFromLines(final ArrayList<String> lines) {
        final ArrayList<Pair<Packet>> result = new ArrayList<Pair<Packet>>();

        Pair<Packet> pair = new Pair<Packet>();
        for (final String line : lines) {
            if (line.equals("")) {
                result.add(pair);
                pair = new Pair<Packet>();
            } else {
                pair.setElement(Packet.parseFromString(line));
            }
        }
        result.add(pair);

        return result;
    }

    private static ArrayList<Packet> parsePacketsFromLines(final ArrayList<String> lines) {
        final ArrayList<Packet> result = new ArrayList<Packet>();

        for (final String line : lines) {
            if (!line.equals("")) {
                final Packet packet = Packet.parseFromString(line);
                result.add(packet);
            }
        }

        return result;
    }
}