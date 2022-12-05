package day5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Crane {
    final protected ArrayList<Stack<Character>> crates;
    final protected ArrayList<Instruction> instructions;

    public Crane(ArrayList<Stack<Character>> crates, ArrayList<Instruction> instructions) {
        this.crates = crates;
        this.instructions = instructions;
    }

    public void executeInstructions() {
        for (Instruction instruction : this.instructions) {
            for (int i = 0; i < instruction.getAmount(); i++) {
                final char crate = this.crates.get(instruction.getFrom() - 1).pop();
                this.crates.get(instruction.getTo() - 1).push(crate);
            }
        }
    }

    public String readMessage() {
        String message = "";

        for (Stack<Character> crate : this.crates) {
            message += crate.peek();
        }

        return message;
    }

    public static ArrayList<Instruction> parseInstructionsFromString(ArrayList<String> instructions) {
        final ArrayList<Instruction> parsedInstructions = new ArrayList<Instruction>();
        for (String line : instructions) {
            final Instruction instruction = Instruction.parseFromString(line);

            parsedInstructions.add(instruction);
        }

        return parsedInstructions;
    }

    public static ArrayList<Stack<Character>> parseCratesFromString(ArrayList<String> lines) {
        ArrayList<Stack<Character>> crates = new ArrayList<Stack<Character>>();

        final String lastLine = lines.get(lines.size() - 1).trim();
        final int length = Character.getNumericValue(lastLine.charAt(lastLine.length() - 1));

        for (int i = 0; i < length; i++) {
            crates.add(new Stack<Character>());
        }

        final List<String> cratesList = lines.subList(0, lines.size() - 1);
        Collections.reverse(cratesList);
        for (String line : cratesList) {
            for (int i = 0; i < line.length(); i += 4) {
                final char letter = line.charAt(i + 1);

                if (letter != ' ') {
                    crates.get(i / 4).push(letter);
                }
            }
        }

        return crates;
    }

    public static Crane parseFromStrings(ArrayList<String> crates, ArrayList<String> instructions) {
        final ArrayList<Instruction> parsedInstructions = Crane.parseInstructionsFromString(instructions);
        final ArrayList<Stack<Character>> parsedCrates = Crane.parseCratesFromString(crates);

        return new Crane(parsedCrates, parsedInstructions);
    }
}
