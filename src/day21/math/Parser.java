package day21.math;

import java.util.ArrayList;

public final class Parser {
    public BinaryOperation parse(final ArrayList<String> tokens) {
        final BinaryOperation rootOperation = new BinaryOperation(null, null);
        BinaryOperation currentOperation = rootOperation;

        for (final String token : tokens) {
            switch (token) {
                case "(":
                    final BinaryOperation newOperation = new BinaryOperation(currentOperation,
                            currentOperation.getLeftOperand() == null ? 1 : 2);
                    currentOperation.setNext(newOperation);
                    currentOperation = newOperation;
                    break;
                case ")":
                    currentOperation = currentOperation.parent;
                    break;
                case "humn":
                    currentOperation.setNext(new Monomial(1, "humn", 1, 0));
                    break;
                default:
                    if ("+-*/=".contains(token)) {
                        currentOperation.setNext(token);
                    } else {
                        final double number = Double.parseDouble(token);
                        currentOperation.setNext(new Monomial(0, null, 0, number));
                    }
            }
        }

        return rootOperation;
    }

    public ArrayList<String> tokenize(final String equation) {
        final ArrayList<String> tokens = new ArrayList<String>();

        String currentToken = "";
        for (final char c : equation.toCharArray()) {
            switch (c) {
                case ' ':
                    if (!currentToken.equals("")) {
                        tokens.add(currentToken);
                        currentToken = "";
                    }
                    break;
                case '(':
                    tokens.add("(");
                    break;
                case ')':
                    if (!currentToken.equals("")) {
                        tokens.add(currentToken);
                        currentToken = "";
                    }
                    tokens.add(")");
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                    if (!currentToken.equals("")) {
                        tokens.add(currentToken);
                        currentToken = "";
                    }
                    tokens.add(Character.toString(c));
                    break;
                default:
                    currentToken += c;
            }
        }

        return tokens;
    }

    public static BinaryOperation parse(final String equation) {
        final Parser parser = new Parser();
        return parser.parse(parser.tokenize(equation));
    }
}
