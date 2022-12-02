package day2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        final File file = new File("./src/day2/input.txt");

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
        return Main.calculateScore(lines, Mode.PLAYER_CHOICE);
    }

    private static int part_2(ArrayList<String> lines) {
        return Main.calculateScore(lines, Mode.RESULT);
    }

    private static ArrayList<Round> parseInput(ArrayList<String> lines, Mode mode) {
        final ArrayList<Round> rounds = new ArrayList<Round>();

        for (String line : lines) {
            rounds.add(Round.parseLine(line, mode));
        }

        return rounds;
    }

    private static int calculateScore(ArrayList<String> lines, Mode mode) {
        final ArrayList<Round> rounds = Main.parseInput(lines, mode);
        int score = 0;

        for (Round round : rounds) {
            score += round.calculateScore();
        }

        return score;
    }
}