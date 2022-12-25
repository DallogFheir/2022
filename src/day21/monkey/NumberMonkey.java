package day21.monkey;

public final class NumberMonkey extends Monkey {
    public final long number;

    public NumberMonkey(final String name, final long number) {
        super(name);
        this.number = number;
    }
}
