package day13;

import java.util.ArrayList;

public class Packet {
    private ArrayList<PacketElement> packet;

    public Packet(ArrayList<PacketElement> packet) {
        this.packet = packet;
    }

    @Override
    public String toString() {
        String result = "[";

        for (PacketElement el : this.packet) {
            result += el.toString() + ", ";
        }

        if (result.length() > 1) {
            result = result.substring(0, result.length() - 2);
        }
        result += "]";

        return result;
    }

    public int compareTo(Packet other) {
        final int longerPacketSize = this.packet.size() > other.packet.size() ? this.packet.size()
                : other.packet.size();
        final Packet shorterPacket = this.packet.size() > other.packet.size() ? other : this;
        final int shorterPacketSize = shorterPacket.packet.size();

        for (int i = 0; i < longerPacketSize; i++) {
            if (i >= shorterPacketSize) {
                return shorterPacket == this ? -1 : 1;
            }

            final int comparison = this.packet.get(i).compareTo(other.packet.get(i));

            if (comparison == 0) {
                continue;
            }

            return comparison;
        }

        return 0;
    }

    public static Packet parseFromString(final String packetStringWithBrackets) {
        final ArrayList<PacketElement> packetEls = new ArrayList<PacketElement>();
        final String packetString = packetStringWithBrackets.substring(1, packetStringWithBrackets.length() - 1);

        ArrayList<ArrayList<PacketElement>> currentLists = new ArrayList<ArrayList<PacketElement>>();
        String currentInteger = "";
        boolean listOpen = false;
        boolean listJustClosed = false;
        for (char c : packetString.toCharArray()) {
            if (listOpen) {
                if (c == ',') {
                    if (!listJustClosed) {
                        final int integer = Integer.parseInt(currentInteger);
                        final PacketInteger packetInteger = new PacketInteger(integer);
                        currentLists.get(currentLists.size() - 1).add(packetInteger);
                        currentInteger = "";
                    }
                } else if (c == '[') {
                    currentLists.add(new ArrayList<PacketElement>());
                } else if (c == ']') {
                    if (!currentInteger.equals("")) {
                        final int integer = Integer.parseInt(currentInteger);
                        final PacketInteger packetInteger = new PacketInteger(integer);
                        currentLists.get(currentLists.size() - 1).add(packetInteger);
                        currentInteger = "";
                    }

                    final ArrayList<PacketElement> lastList = currentLists.get(currentLists.size() - 1);
                    final PacketList packetList = new PacketList(lastList);
                    if (currentLists.size() == 1) {
                        listOpen = false;
                        packetEls.add(packetList);
                    } else {
                        currentLists.get(currentLists.size() - 2).add(packetList);
                    }
                    currentLists.remove(currentLists.size() - 1);

                    listJustClosed = true;

                    continue;
                } else {
                    currentInteger += c;
                }

                listJustClosed = false;
            } else if (c == ',') {
                if (!listJustClosed) {
                    final int integer = Integer.parseInt(currentInteger);
                    final PacketInteger packetInteger = new PacketInteger(integer);
                    packetEls.add(packetInteger);
                    currentInteger = "";
                }
            } else if (c == '[') {
                listOpen = true;
                currentLists.add(new ArrayList<PacketElement>());
            } else {
                currentInteger += c;
            }

            listJustClosed = false;
        }

        if (!currentInteger.equals("")) {
            final int integer = Integer.parseInt(currentInteger);
            final PacketInteger packetInteger = new PacketInteger(integer);
            packetEls.add(packetInteger);
        }

        return new Packet(packetEls);
    }
}
