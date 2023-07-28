package day01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        final File file = new File("./src/day01/input.txt");

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
        final ArrayList<Elf> elves = Main.parseInput(lines);

        Elf maxElf = elves.get(0);
        for (int i = 1; i < elves.size(); i++) {
            final Elf elf = elves.get(i);

            if (elf.getAllCalories() > maxElf.getAllCalories()) {
                maxElf = elf;
            }
        }

        return maxElf.getAllCalories();
    }

    private static int part_2(ArrayList<String> lines) {
        final ArrayList<Elf> elves = Main.parseInput(lines);
        elves.sort((elf_1, elf_2) -> elf_2.getAllCalories() - elf_1.getAllCalories());

        return elves.get(0).getAllCalories() + elves.get(1).getAllCalories() + elves.get(2).getAllCalories();
    }

    private static ArrayList<Elf> parseInput(ArrayList<String> lines) {
        final ArrayList<Elf> elves = new ArrayList<Elf>();

        ArrayList<Integer> currentCalories = new ArrayList<Integer>();
        for (String line : lines) {
            if (line.equals("")) {
                final Elf elf = new Elf(currentCalories);
                elves.add(elf);

                currentCalories = new ArrayList<Integer>();
            } else {
                currentCalories.add(Integer.parseInt(line));
            }
        }

        return elves;
    }
}