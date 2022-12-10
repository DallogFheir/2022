package day10;

public class Instruction {
    private final Command command;
    private final int argument;

    public Instruction(Command command, int argument) {
        this.command = command;
        this.argument = argument;
    }

    public Instruction(Command command) {
        this.command = command;
        this.argument = 0;
    }

    public Command getCommand() {
        return command;
    }

    public int getArgument() {
        return this.argument;
    }
}
