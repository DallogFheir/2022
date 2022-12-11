package day11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private enum Part {
        NUMBER,
        STARTING_ITEMS,
        OPERATION,
        TEST,
        IF_TRUE,
        IF_FALSE
    };

    public static void main(String[] args) {
        final File file = new File("./src/day11/input.txt");

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
        final ArrayList<Monkey<Integer>> monkeys = Main.parseMonkeys(lines);

        for (int i = 0; i < 20; i++) {
            for (final Monkey<Integer> monkey : monkeys) {
                for (final int item : monkey.getItems()) {
                    final int worryLevel = monkey.getOperation().apply(item) / 3;

                    if (monkey.getTest().test(worryLevel)) {
                        monkeys.get(monkey.getIfTrue()).getItems().add(worryLevel);
                    } else {
                        monkeys.get(monkey.getIfFalse()).getItems().add(worryLevel);
                    }

                    monkey.incrementCounter();
                }

                monkey.getItems().clear();
            }
        }

        Collections.sort(monkeys, (a, b) -> (int) (b.getCounter() - a.getCounter()));

        return monkeys.get(0).getCounter() * monkeys.get(1).getCounter();
    }

    private static long part_2(ArrayList<String> lines) {
        final ArrayList<Monkey<BigInt>> monkeys = Main.parseBigMonkeys(lines);

        for (int i = 0; i < 10000; i++) {
            for (final Monkey<BigInt> monkey : monkeys) {
                for (final BigInt item : monkey.getItems()) {
                    final BigInt worryLevel = monkey.getOperation().apply(item);

                    if (monkey.getTest().test(worryLevel)) {
                        monkeys.get(monkey.getIfTrue()).getItems().add(worryLevel);
                    } else {
                        monkeys.get(monkey.getIfFalse()).getItems().add(worryLevel);
                    }

                    monkey.incrementCounter();
                }

                monkey.getItems().clear();
            }
        }

        Collections.sort(monkeys, (a, b) -> (int) (b.getCounter() - a.getCounter()));

        return monkeys.get(0).getCounter() * monkeys.get(1).getCounter();
    }

    private static ArrayList<Monkey<Integer>> parseMonkeys(ArrayList<String> lines) {
        final ArrayList<Monkey<Integer>> monkeys = new ArrayList<Monkey<Integer>>();

        final HashMap<Part, String> patterns = new HashMap<Part, String>();
        patterns.put(Part.NUMBER, "^Monkey (\\d+):$");
        patterns.put(Part.STARTING_ITEMS, "^  Starting items: ((\\d+(, )?)+)$");
        patterns.put(Part.OPERATION, "^  Operation: new = old ([*+-/] \\w+)$");
        patterns.put(Part.TEST, "^  Test: divisible by (\\d+)$");
        patterns.put(Part.IF_TRUE, "^    If true: throw to monkey (\\d+)$");
        patterns.put(Part.IF_FALSE, "^    If false: throw to monkey (\\d+)$");

        int currentNum = -1;
        ArrayList<Integer> currentStartingItems = new ArrayList<Integer>();
        UnaryOperation<Integer> currentOperation = (old) -> old;
        int currentTest = -1;
        int currentIfTrue = -1;
        int currentIfFalse = -1;

        for (String line : lines) {
            boolean matched = false;

            for (final Entry<Part, String> patternEntry : patterns.entrySet()) {
                final Pattern pattern = Pattern.compile(patternEntry.getValue());
                final Matcher matcher = pattern.matcher(line);

                if (matcher.matches()) {
                    switch (patternEntry.getKey()) {
                        case NUMBER:
                            currentNum = Integer.parseInt(matcher.group(1));
                            break;
                        case STARTING_ITEMS:
                            currentStartingItems = new ArrayList<Integer>();
                            final String[] items = matcher.group(1).split(", ");
                            for (final String item : items) {
                                currentStartingItems.add(Integer.parseInt(item));
                            }
                            break;
                        case OPERATION:
                            final String group = matcher.group(1);
                            final char operation = group.charAt(0);
                            final String number = group.substring(2);

                            if (number.equals("old")) {
                                switch (operation) {
                                    case '+':
                                        currentOperation = (old) -> old + old;
                                        break;
                                    case '*':
                                        currentOperation = (old) -> old * old;
                                        break;
                                }
                            } else {
                                final int numberInt = Integer.parseInt(number);

                                switch (operation) {
                                    case '+':
                                        currentOperation = (old) -> old + numberInt;
                                        break;
                                    case '*':
                                        currentOperation = (old) -> old * numberInt;
                                        break;
                                }
                            }

                            break;
                        case TEST:
                            currentTest = Integer.parseInt(matcher.group(1));
                            break;
                        case IF_TRUE:
                            currentIfTrue = Integer.parseInt(matcher.group(1));
                            break;
                        case IF_FALSE:
                            currentIfFalse = Integer.parseInt(matcher.group(1));
                            break;
                    }

                    matched = true;
                    break;
                }
            }

            if (!matched) {
                final int currentTestFinal = currentTest;
                final Monkey<Integer> monkey = new Monkey<Integer>(currentNum, currentStartingItems, currentOperation,
                        (el) -> el % currentTestFinal == 0, currentIfTrue,
                        currentIfFalse);
                monkeys.add(monkey);
            }
        }

        final int currentTestFinal = currentTest;
        final Monkey<Integer> monkey = new Monkey<Integer>(currentNum, currentStartingItems, currentOperation,
                (el) -> el % currentTestFinal == 0, currentIfTrue,
                currentIfFalse);
        monkeys.add(monkey);

        return monkeys;
    }

    private static ArrayList<Monkey<BigInt>> parseBigMonkeys(ArrayList<String> lines) {
        final ArrayList<Monkey<BigInt>> monkeys = new ArrayList<Monkey<BigInt>>();

        final HashMap<Part, String> patterns = new HashMap<Part, String>();
        patterns.put(Part.NUMBER, "^Monkey (\\d+):$");
        patterns.put(Part.STARTING_ITEMS, "^  Starting items: ((\\d+(, )?)+)$");
        patterns.put(Part.OPERATION, "^  Operation: new = old ([*+-/] \\w+)$");
        patterns.put(Part.TEST, "^  Test: divisible by (\\d+)$");
        patterns.put(Part.IF_TRUE, "^    If true: throw to monkey (\\d+)$");
        patterns.put(Part.IF_FALSE, "^    If false: throw to monkey (\\d+)$");

        int currentNum = -1;
        ArrayList<BigInt> currentStartingItems = new ArrayList<BigInt>();
        UnaryOperation<BigInt> currentOperation = (old) -> old;
        int currentTest = -1;
        int currentIfTrue = -1;
        int currentIfFalse = -1;

        HashSet<Integer> factors = new HashSet<Integer>();
        for (String line : lines) {
            final Pattern pattern = Pattern.compile(patterns.get(Part.TEST));
            final Matcher matcher = pattern.matcher(line);

            if (matcher.matches()) {
                final int test = Integer.parseInt(matcher.group(1));
                factors.add(test);
            }
        }

        for (String line : lines) {
            boolean matched = false;

            for (final Entry<Part, String> patternEntry : patterns.entrySet()) {
                final Pattern pattern = Pattern.compile(patternEntry.getValue());
                final Matcher matcher = pattern.matcher(line);

                if (matcher.matches()) {
                    switch (patternEntry.getKey()) {
                        case NUMBER:
                            currentNum = Integer.parseInt(matcher.group(1));
                            break;
                        case STARTING_ITEMS:
                            currentStartingItems = new ArrayList<BigInt>();
                            final String[] items = matcher.group(1).split(", ");
                            final HashSet<Integer> factorsCopy = new HashSet<Integer>(factors);
                            for (final String item : items) {
                                currentStartingItems.add(new BigInt(Integer.parseInt(item), factorsCopy));
                            }
                            break;
                        case OPERATION:
                            final String group = matcher.group(1);
                            final char operation = group.charAt(0);
                            final String number = group.substring(2);

                            if (number.equals("old")) {
                                switch (operation) {
                                    case '*':
                                        currentOperation = (old) -> old.multiply(old);
                                        break;
                                }
                            } else {
                                final int numberInt = Integer.parseInt(number);

                                switch (operation) {
                                    case '+':
                                        currentOperation = (old) -> old.add(numberInt);
                                        break;
                                    case '*':
                                        currentOperation = (old) -> old.multiply(numberInt);
                                        break;
                                }
                            }

                            break;
                        case TEST:
                            currentTest = Integer.parseInt(matcher.group(1));
                            break;
                        case IF_TRUE:
                            currentIfTrue = Integer.parseInt(matcher.group(1));
                            break;
                        case IF_FALSE:
                            currentIfFalse = Integer.parseInt(matcher.group(1));
                            break;
                    }

                    matched = true;
                    break;
                }
            }

            if (!matched) {
                final int currentTestFinal = currentTest;
                final Monkey<BigInt> monkey = new Monkey<BigInt>(currentNum, currentStartingItems, currentOperation,
                        (el) -> el.isDivisible(currentTestFinal), currentIfTrue,
                        currentIfFalse);
                monkeys.add(monkey);
            }
        }

        final int currentTestFinal = currentTest;
        final Monkey<BigInt> monkey = new Monkey<BigInt>(currentNum, currentStartingItems, currentOperation,
                (el) -> el.isDivisible(currentTestFinal), currentIfTrue,
                currentIfFalse);
        monkeys.add(monkey);

        return monkeys;
    }
}