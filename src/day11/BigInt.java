package day11;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class BigInt {
    private final HashMap<Integer, Integer> factors;

    public BigInt(int number, HashSet<Integer> factors) {
        this.factors = new HashMap<Integer, Integer>();

        for (final int factor : factors) {
            this.factors.put(factor, number % factor);
        }
    }

    public int getFactor(int factor) {
        return this.factors.get(factor);
    }

    public BigInt multiply(int multiplicand) {
        for (final Entry<Integer, Integer> factorEntry : this.factors.entrySet()) {
            final int result = (factorEntry.getValue() * multiplicand) % factorEntry.getKey();
            factorEntry.setValue(result);
        }

        return this;
    }

    public BigInt multiply(BigInt multiplicand) {
        for (final Entry<Integer, Integer> factorEntry : this.factors.entrySet()) {
            final int result = (factorEntry.getValue() * multiplicand.getFactor(factorEntry.getKey()))
                    % factorEntry.getKey();
            factorEntry.setValue(result);
        }

        return this;
    }

    public BigInt add(int addend) {
        for (final Entry<Integer, Integer> factorEntry : this.factors.entrySet()) {
            final int newFactor = (factorEntry.getValue() + addend) % factorEntry.getKey();
            factorEntry.setValue(newFactor);
        }

        return this;
    }

    public boolean isDivisible(int divisor) {
        return this.factors.get(divisor) == 0;
    }
}
