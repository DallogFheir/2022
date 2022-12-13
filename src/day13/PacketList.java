package day13;

import java.util.ArrayList;

public class PacketList extends PacketElement {
    public final ArrayList<PacketElement> list;

    public PacketList(ArrayList<PacketElement> list) {
        this.list = list;
    }

    public int size() {
        return this.list.size();
    }

    public PacketElement get(int index) {
        return this.list.get(index);
    }

    @Override
    public String toString() {
        String result = "[";

        for (PacketElement el : this.list) {
            result += el.toString() + ", ";
        }

        if (result.length() > 1) {
            result = result.substring(0, result.length() - 2);
        }
        result += "]";

        return result;
    }
}
