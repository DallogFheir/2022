package day13;

import java.util.ArrayList;

public abstract class PacketElement {
    public int compareTo(PacketElement other) {
        if (this instanceof PacketInteger && other instanceof PacketInteger) {
            final PacketInteger thisInteger = (PacketInteger) this;
            final PacketInteger otherInteger = (PacketInteger) other;

            return thisInteger.integer - otherInteger.integer;
        }

        if (this instanceof PacketList && other instanceof PacketList) {
            final PacketList thisList = (PacketList) this;
            final PacketList otherList = (PacketList) other;

            final int longerListSize = thisList.size() > otherList.size() ? thisList.size() : otherList.size();
            final PacketList shorterList = thisList.size() > otherList.size() ? otherList : thisList;
            final int shorterListSize = shorterList.size();

            for (int i = 0; i < longerListSize; i++) {
                if (i >= shorterListSize) {
                    return shorterList == thisList ? -1 : 1;
                }

                final int comparison = thisList.get(i).compareTo(otherList.get(i));
                if (comparison != 0) {
                    return comparison;
                }
            }

            return 0;
        }

        final ArrayList<PacketElement> newList = new ArrayList<PacketElement>();
        if (this instanceof PacketInteger) {
            newList.add(this);
            final PacketList newPacketList = new PacketList(newList);
            return newPacketList.compareTo(other);
        } else {
            newList.add(other);
            final PacketList newPacketList = new PacketList(newList);
            return this.compareTo(newPacketList);
        }
    }
}
