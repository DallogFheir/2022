package day6;

import java.util.HashSet;

import day6.errors.NoMarkerFoundError;

public class Signal {
    private final String signal;

    public Signal(String signal) {
        this.signal = signal;
    }

    public int findPacketMarker() throws NoMarkerFoundError {
        return this.findMarker(4);
    }

    public int findMessageMarker() throws NoMarkerFoundError {
        return this.findMarker(14);
    }

    private int findMarker(int howMany) throws NoMarkerFoundError {
        String lastChars = this.signal.substring(0, howMany);

        for (int i = howMany; i < this.signal.length(); i++) {
            final HashSet<Character> lastCharsSet = new HashSet<Character>();
            for (char c : lastChars.toCharArray()) {
                lastCharsSet.add(c);
            }

            if (lastCharsSet.size() == howMany) {
                return i;
            }

            final char charToAdd = this.signal.charAt(i);
            lastChars = lastChars.substring(1) + charToAdd;
        }

        throw new NoMarkerFoundError("No marker found in signal " + this.signal);
    }
}
