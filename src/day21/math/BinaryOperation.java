package day21.math;

public final class BinaryOperation implements Expression {
    private Expression leftOperand;
    private Expression rightOperand;
    private String operation;
    public final BinaryOperation parent;
    public final Integer whichOperand;

    public BinaryOperation(final BinaryOperation parent, final Integer whichOperand) {
        this.parent = parent;
        this.whichOperand = whichOperand;
    }

    public Expression getLeftOperand() {
        return this.leftOperand;
    }

    public void setLeftOperand(final Expression leftOperand) {
        this.leftOperand = leftOperand;
    }

    public Expression getRightOperand() {
        return this.rightOperand;
    }

    public void setRightOperand(final Expression rightOperand) {
        this.rightOperand = rightOperand;
    }

    public String getOperation() {
        return this.operation;
    }

    public void setOperation(final String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return String.format("(%s %s %s)", this.leftOperand, this.operation, this.rightOperand);
    }

    public void setNext(final String operation) {
        if (this.operation != null) {
            throw new IllegalStateException("BinaryOperation already has operation.");
        }

        this.operation = operation;
    }

    public void setNext(final Expression expression) {
        if (this.leftOperand == null) {
            this.leftOperand = expression;
            return;
        }

        if (this.rightOperand == null) {
            this.rightOperand = expression;
            return;
        }

        throw new IllegalStateException("BinaryOperation already has two operands.");
    }

    public void simplify() {
        if (this.leftOperand instanceof BinaryOperation) {
            final BinaryOperation leftOperand = (BinaryOperation) this.leftOperand;
            leftOperand.simplify();
        }
        if (this.rightOperand instanceof BinaryOperation) {
            final BinaryOperation rightOperand = (BinaryOperation) this.rightOperand;
            rightOperand.simplify();
        }

        final Monomial leftOperand = (Monomial) this.leftOperand;
        final Monomial rightOperand = (Monomial) this.rightOperand;

        Monomial result;
        switch (this.operation) {
            case "=":
                return;
            case "+":
                result = leftOperand.add(rightOperand);
                break;
            case "-":
                result = leftOperand.subtract(rightOperand);
                break;
            case "*":
                result = leftOperand.multiply(rightOperand);
                break;
            case "/":
                result = leftOperand.divide(rightOperand);
                break;
            default:
                throw new IllegalStateException("Unknown operation: " + this.operation);
        }

        switch (this.whichOperand) {
            case 1:
                this.parent.setLeftOperand(result);
                break;
            case 2:
                this.parent.setRightOperand(result);
                break;
            default:
                throw new IllegalStateException("Unknown operand: " + this.whichOperand);
        }
    }
}
