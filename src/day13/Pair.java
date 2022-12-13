package day13;

public class Pair<T> {
    private T first;
    private T second;

    public Pair() {

    }

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return this.first;
    }

    public T getSecond() {
        return this.second;
    }

    @Override
    public String toString() {
        return String.format("<%s, %s>", this.first, this.second);
    }

    public void setElement(T element) {
        if (this.first == null) {
            this.first = element;
        } else if (this.second == null) {
            this.second = element;
        } else {
            throw new IllegalStateException("Pair is already full.");
        }
    }
}
