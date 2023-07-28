package day05;

import java.util.ArrayList;

public final class Stack<T> {
    final private ArrayList<T> stack;

    public Stack() {
        this.stack = new ArrayList<T>();
    }

    @Override
    public String toString() {
        return this.stack.toString();
    }

    public void push(T el) {
        this.stack.add(el);
    }

    public T pop() {
        final int lastIndex = this.stack.size() - 1;
        final T el = this.stack.get(lastIndex);
        this.stack.remove(lastIndex);
        return el;
    }

    public T peek() {
        final int lastIndex = this.stack.size() - 1;
        final T el = this.stack.get(lastIndex);
        return el;
    }
}
