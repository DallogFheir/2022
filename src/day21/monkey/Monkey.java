package day21.monkey;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Monkey {
    public final String name;

    protected Monkey(final String name) {
        this.name = name;
    }

    public static Monkey parseFromLine(final String line) {
        final Pattern numberPattern = Pattern.compile("^(\\w+): (\\d+)$");
        final Pattern operationPattern = Pattern.compile("^(\\w+): (\\w+) (.) (\\w+)$");

        final Matcher numberMatcher = numberPattern.matcher(line);
        if (numberMatcher.find()) {
            final String name = numberMatcher.group(1);
            final long number = Long.parseLong(numberMatcher.group(2));

            return new NumberMonkey(name, number);
        }

        final Matcher operationMatcher = operationPattern.matcher(line);
        operationMatcher.find();

        final String name = operationMatcher.group(1);
        final String operand1 = operationMatcher.group(2);
        final String operation = operationMatcher.group(3);
        final String operand2 = operationMatcher.group(4);

        return new OperationMonkey(name, operand1, operand2, operation);
    }
}
