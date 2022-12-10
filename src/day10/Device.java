package day10;

import java.util.ArrayList;

public final class Device {
    private final ArrayList<Instruction> instructions;
    private int register;

    public Device(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
        this.register = 1;
    }

    public int getRegister() {
        return this.register;
    }

    public int executeInstructions() {
        int sum = 0;
        int instructionIndex = 0;
        AdderTimer currentTimer = null;

        for (int cycle = 1; cycle <= 220; cycle++) {
            if (cycle % 40 == 20) {
                sum += cycle * this.register;
            }

            if (currentTimer != null) {
                if (currentTimer.isTimedOut()) {
                    this.register += currentTimer.getAddend();
                    currentTimer = null;
                } else {
                    currentTimer.decrementTimer();
                }
            } else if (instructionIndex < this.instructions.size()) {
                final Instruction instruction = this.instructions.get(instructionIndex);
                instructionIndex++;

                switch (instruction.getCommand()) {
                    case ADDX:
                        currentTimer = new AdderTimer(instruction.getArgument(), 0);
                        break;
                    case NOOP:
                        break;
                }
            }
        }

        return sum;
    }

    public void draw() {
        int instructionIndex = 0;
        AdderTimer currentTimer = null;
        int currentCol = 0;
        int currentRow = 0;
        int spriteStart = 0;
        int spriteEnd = 2;

        while (currentRow <= 5) {
            final boolean isTimered = currentTimer != null;

            // cycle start
            if (!isTimered && instructionIndex < this.instructions.size()) {
                final Instruction instruction = this.instructions.get(instructionIndex);
                instructionIndex++;

                switch (instruction.getCommand()) {
                    case ADDX:
                        currentTimer = new AdderTimer(instruction.getArgument(), 0);
                        break;
                    case NOOP:
                        break;
                }
            }

            // during cycle
            if (currentCol <= spriteEnd && currentCol >= spriteStart) {
                System.out.print("#");
            } else {
                System.out.print(".");
            }

            // cycle end
            if (isTimered) {
                if (currentTimer.isTimedOut()) {
                    this.register += currentTimer.getAddend();

                    spriteStart = this.register - 1;
                    spriteEnd = this.register + 1;

                    currentTimer = null;
                } else {
                    currentTimer.decrementTimer();
                }
            }

            currentCol++;
            if (currentCol >= 40) {
                System.out.println();
                currentRow++;
                currentCol = 0;
            }
        }
    }

    public static Device parseFromLines(ArrayList<String> lines) {
        final ArrayList<Instruction> instructions = new ArrayList<Instruction>();

        for (final String line : lines) {
            final String[] parts = line.split(" ");
            final String commandStr = parts[0];

            switch (commandStr) {
                case "addx":
                    final int arg = Integer.parseInt(parts[1]);
                    final Instruction instructionAddx = new Instruction(Command.ADDX, arg);
                    instructions.add(instructionAddx);
                    break;
                case "noop":
                    final Instruction instructionNoop = new Instruction(Command.NOOP);
                    instructions.add(instructionNoop);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown command: " + commandStr);
            }
        }

        return new Device(instructions);
    }
}
