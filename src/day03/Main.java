package day03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import day03.errors.InvalidContentError;
import day03.errors.InvalidItemError;

public class Main {
    public static void main(String[] args) {
        final File file = new File("./src/day03/input.txt");

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
        final ArrayList<Rucksack> rucksacks = Main.parseInput(lines);

        int sum = 0;
        for (Rucksack rucksack : rucksacks) {
            char item;
            try {
                item = rucksack.findRepeating();
            } catch (InvalidContentError err) {
                System.out.println(err.getMessage());
                return -1;
            }

            try {
                sum += Rucksack.getValueOfItem(item);
            } catch (InvalidItemError err) {
                System.out.println(err.getMessage());
                return -1;
            }
        }

        return sum;
    }

    private static int part_2(ArrayList<String> lines) {
        final ArrayList<Rucksack> rucksacks = Main.parseInput(lines);

        int sum = 0;
        for (int i = 0; i < rucksacks.size(); i += 3) {
            final Rucksack rucksack_1 = rucksacks.get(i);
            final Rucksack rucksack_2 = rucksacks.get(i + 1);
            final Rucksack rucksack_3 = rucksacks.get(i + 2);

            char item;
            try {
                item = Rucksack.findCommon(rucksack_1, rucksack_2, rucksack_3);
            } catch (InvalidContentError err) {
                System.out.println(err.getMessage());
                return -1;
            }

            try {
                sum += Rucksack.getValueOfItem(item);
            } catch (InvalidItemError err) {
                System.out.println(err.getMessage());
                return -1;
            }
        }

        return sum;
    }

    private static ArrayList<Rucksack> parseInput(ArrayList<String> lines) {
        final ArrayList<Rucksack> rucksacks = new ArrayList<Rucksack>();

        for (String line : lines) {
            rucksacks.add(Rucksack.parseFromString(line));
        }

        return rucksacks;
    }
}