package day21;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import day21.math.BinaryOperation;
import day21.math.Monomial;
import day21.math.Parser;
import day21.monkey.Monkey;
import day21.monkey.NumberMonkey;
import day21.monkey.OperationMonkey;

public class Main {
    public static void main(final String[] args) {
        final File file = new File("./src/day21/input.txt");

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

    private static long part_1(final ArrayList<String> lines) {
        final HashMap<String, Monkey> monkeys = Main.parseMonkeys(lines);
        final HashMap<String, Long> monkeyResults = new HashMap<String, Long>();

        final ArrayList<String> queue = new ArrayList<String>();
        queue.add("root");

        while (!queue.isEmpty()) {
            final String name = queue.get(queue.size() - 1);
            final Monkey monkey = monkeys.get(name);

            if (monkey instanceof NumberMonkey) {
                final NumberMonkey numberMonkey = (NumberMonkey) monkey;
                monkeyResults.put(name, numberMonkey.number);
                queue.remove(queue.size() - 1);
            } else {
                final OperationMonkey operationMonkey = (OperationMonkey) monkey;

                if (monkeyResults.containsKey(operationMonkey.operand1)
                        && monkeyResults.containsKey(operationMonkey.operand2)) {
                    final long operand1 = monkeyResults.get(operationMonkey.operand1);
                    final long operand2 = monkeyResults.get(operationMonkey.operand2);
                    monkeyResults.put(name, operationMonkey.performOperation(operand1, operand2));
                    queue.remove(queue.size() - 1);
                } else {
                    if (!monkeyResults.containsKey(operationMonkey.operand1)) {
                        queue.add(operationMonkey.operand1);
                    }
                    if (!monkeyResults.containsKey(operationMonkey.operand2)) {
                        queue.add(operationMonkey.operand2);
                    }
                }
            }
        }

        return monkeyResults.get("root");
    }

    private static long part_2(final ArrayList<String> lines) {
        final HashMap<String, Monkey> monkeys = Main.parseMonkeys(lines);

        final OperationMonkey root = (OperationMonkey) monkeys.get("root");
        String equation = String.format("%s %s %s", root.operand1, "=", root.operand2);
        boolean containsOnlyHuman = false;
        while (!containsOnlyHuman) {
            for (final Entry<String, Monkey> monkeyEntry : monkeys.entrySet()) {
                final Monkey monkey = monkeyEntry.getValue();

                if (monkey instanceof NumberMonkey) {
                    final NumberMonkey numberMonkey = (NumberMonkey) monkey;
                    equation = equation.replace(monkeyEntry.getKey(),
                            String.format("%s", numberMonkey.name.equals("humn") ? "humn" : numberMonkey.number));
                } else {
                    final OperationMonkey operationMonkey = (OperationMonkey) monkey;
                    equation = equation.replace(monkeyEntry.getKey(),
                            String.format("(%s %s %s)", operationMonkey.operand1,
                                    operationMonkey.operation, operationMonkey.operand2));
                }
            }

            containsOnlyHuman = true;
            for (final String name : monkeys.keySet()) {
                if (!name.equals("humn") && equation.contains(name)) {
                    containsOnlyHuman = false;
                }
            }
        }

        final BinaryOperation rootOperation = Parser.parse(equation);
        rootOperation.simplify();
        final Monomial leftOperand = (Monomial) rootOperation.getLeftOperand();
        final Monomial rightOperand = (Monomial) rootOperation.getRightOperand();
        return (long) ((rightOperand.freeTerm - leftOperand.freeTerm) / leftOperand.coefficient);
    }

    private static HashMap<String, Monkey> parseMonkeys(final ArrayList<String> lines) {
        final HashMap<String, Monkey> monkeys = new HashMap<String, Monkey>();

        for (final String line : lines) {
            final Monkey newMonkey = Monkey.parseFromLine(line);

            monkeys.put(newMonkey.name, newMonkey);
        }

        return monkeys;
    }
}