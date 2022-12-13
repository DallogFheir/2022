package day13;

public class PacketInteger extends PacketElement {
    public final int integer;

    public PacketInteger(int integer) {
        this.integer = integer;
    }

    @Override
    public String toString() {
        return Integer.toString(this.integer);
    }
}
