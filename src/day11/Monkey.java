package day11;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Monkey<T> {
    protected int number;
    protected ArrayList<T> items;
    protected UnaryOperation<T> operation;
    protected Predicate<T> test;
    protected int ifTrue;
    protected int ifFalse;
    protected long counter;

    public Monkey(int number, ArrayList<T> items, UnaryOperation<T> operation, Predicate<T> test,
            int ifTrue,
            int ifFalse) {
        this.number = number;
        this.items = items;
        this.operation = operation;
        this.test = test;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
        this.counter = 0;
    }

    public ArrayList<T> getItems() {
        return this.items;
    }

    public UnaryOperation<T> getOperation() {
        return this.operation;
    }

    public Predicate<T> getTest() {
        return this.test;
    }

    public int getIfTrue() {
        return this.ifTrue;
    }

    public int getIfFalse() {
        return this.ifFalse;
    }

    public long getCounter() {
        return this.counter;
    }

    public void incrementCounter() {
        this.counter++;
    }

    @Override
    public String toString() {
        return String.format("Monkey(%d)", this.counter);
    }
}
