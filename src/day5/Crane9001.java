package day5;

import java.util.ArrayList;
import java.util.Collections;

public final class Crane9001 extends Crane {
    public Crane9001(ArrayList<Stack<Character>> crates, ArrayList<Instruction> instructions) {
        super(crates, instructions);
    }

    @Override
    public void executeInstructions() {
        for (Instruction instruction : this.instructions) {
            ArrayList<Character> cratesInOrder = new ArrayList<Character>();

            for (int i = 0; i < instruction.getAmount(); i++) {
                final char crate = this.crates.get(instruction.getFrom() - 1).pop();
                cratesInOrder.add(crate);
            }

            Collections.reverse(cratesInOrder);
            for (char crate : cratesInOrder) {
                this.crates.get(instruction.getTo() - 1).push(crate);
            }
        }
    }

    public static Crane9001 parseFromStrings(ArrayList<String> crates, ArrayList<String> instructions) {
        final ArrayList<Instruction> parsedInstructions = Crane.parseInstructionsFromString(instructions);
        final ArrayList<Stack<Character>> parsedCrates = Crane.parseCratesFromString(crates);

        return new Crane9001(parsedCrates, parsedInstructions);
    }
}
