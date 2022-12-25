package day21.monkey;

public final class OperationMonkey extends Monkey {
    public final String operand1;
    public final String operand2;
    public final String operation;

    public OperationMonkey(final String name, final String operand1, final String operand2, final String operation) {
        super(name);
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operation = operation;
    }

    public long performOperation(final long operand1, final long operand2) {
        switch (this.operation) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            default:
                throw new IllegalStateException("Unknown operation: " + this.operation);
        }
    }
}
