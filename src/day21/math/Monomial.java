package day21.math;

public final class Monomial implements Expression {
    public final double coefficient;
    public final String variable;
    public final int exponent;
    public final double freeTerm;

    public Monomial(final double coefficient, final String variable, final int exponent, final double freeTerm) {
        this.coefficient = coefficient;
        this.variable = variable;
        this.exponent = exponent;
        this.freeTerm = freeTerm;
    }

    @Override
    public String toString() {
        return String.format("%s%s%s%s", this.coefficient == 1 || this.coefficient == 0 ? "" : this.coefficient,
                this.variable == null ? "" : this.variable,
                this.exponent == 1 || this.exponent == 0 ? "" : "^" + this.exponent,
                this.freeTerm == 0 ? ""
                        : this.freeTerm < 0 ? this.freeTerm
                                : (this.coefficient == 1 || this.coefficient == 0) ? this.freeTerm
                                        : "+" + this.freeTerm);
    }

    public Monomial add(final Monomial other) {
        final double coefficient = this.variable == null ? other.coefficient : this.coefficient;
        final String variable = this.variable == null ? other.variable : this.variable;
        final int exponent = this.variable == null ? other.exponent : this.exponent;

        return new Monomial(coefficient, variable, exponent, this.freeTerm + other.freeTerm);
    }

    public Monomial subtract(final Monomial other) {
        final double coefficient = this.variable == null ? -other.coefficient : this.coefficient;
        final String variable = this.variable == null ? other.variable : this.variable;
        final int exponent = this.variable == null ? other.exponent : this.exponent;

        return new Monomial(coefficient, variable, exponent, this.freeTerm - other.freeTerm);
    }

    public Monomial multiply(final Monomial other) {
        final double multiplicand = this.variable == null ? this.freeTerm : other.freeTerm;
        final double coefficient = this.variable == null ? other.coefficient : this.coefficient;
        final String variable = this.variable == null ? other.variable : this.variable;
        final double freeTerm = this.variable == null ? other.freeTerm : this.freeTerm;
        final int exponent = this.variable == null ? other.exponent : this.exponent;

        return new Monomial(coefficient * multiplicand, variable, exponent, freeTerm * multiplicand);
    }

    public Monomial divide(final Monomial other) {
        final double coefficient = this.variable == null ? other.coefficient : this.coefficient;
        final String variable = this.variable == null ? other.variable : this.variable;
        final int exponent = this.variable == null ? -other.exponent : this.exponent;
        final double freeTerm = this.variable == null ? other.freeTerm : this.freeTerm;
        final double divisor = this.variable == null ? other.freeTerm * other.freeTerm / this.freeTerm : other.freeTerm;

        return new Monomial(coefficient / divisor, variable, exponent, freeTerm / divisor);
    }
}
