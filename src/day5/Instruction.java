package day5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Instruction {
    private int amount;
    private int from;
    private int to;

    public Instruction(int amount, int from, int to) {
        this.amount = amount;
        this.from = from;
        this.to = to;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getFrom() {
        return this.from;
    }

    public int getTo() {
        return this.to;
    }

    @Override
    public String toString() {
        return String.format("move %d from %d to %d", amount, from, to);
    }

    public static Instruction parseFromString(String ins) {
        Pattern pattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");
        Matcher matcher = pattern.matcher(ins);

        if (matcher.find()) {
            final int amount = Integer.parseInt(matcher.group(1));
            final int from = Integer.parseInt(matcher.group(2));
            final int to = Integer.parseInt(matcher.group(3));

            return new Instruction(amount, from, to);
        } else {
            throw new IllegalArgumentException("Invalid instruction.");
        }
    }
}
