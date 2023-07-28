package day07;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        final File file = new File("./src/day07/input.txt");

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

    private static long part_1(ArrayList<String> lines) {
        final ArrayList<Long> sizes = Main.getDirSizes(lines);
        sizes.removeIf((el) -> el > 100000);

        long sum = 0;
        for (long size : sizes) {
            sum += size;
        }
        return sum;
    }

    private static long part_2(ArrayList<String> lines) {
        final ArrayList<Long> sizes = Main.getDirSizes(lines);
        Collections.sort(sizes, (a, b) -> b.compareTo(a));

        final long rootSize = sizes.get(0);
        final long unusedSpace = 70000000 - rootSize;
        final long diff = 30000000 - unusedSpace;

        Collections.reverse(sizes);
        for (long size : sizes) {
            if (size >= diff) {
                return size;
            }
        }

        return -1;
    }

    private static ArrayList<Long> getDirSizes(ArrayList<String> lines) {
        final DiskDirectory root = DiskDirectory.parseFromCommands(lines);

        final ArrayList<Long> sizes = new ArrayList<Long>();
        final ArrayList<DiskDirectory> queue = new ArrayList<DiskDirectory>();
        queue.add(root);

        while (!queue.isEmpty()) {
            final DiskDirectory dir = queue.remove(queue.size() - 1);
            sizes.add(dir.getSize());

            for (DiskEntity subentity : dir.getSubentities()) {
                if (subentity instanceof DiskDirectory) {
                    final DiskDirectory subdir = (DiskDirectory) subentity;

                    queue.add(subdir);
                }
            }
        }

        return sizes;
    }
}