package day03;

import day03.errors.InvalidContentError;
import day03.errors.InvalidItemError;

public final class Rucksack {
    private String compartment_1;
    private String compartment_2;

    public Rucksack(String compartment_1, String compartment_2) {
        this.compartment_1 = compartment_1;
        this.compartment_2 = compartment_2;

    }

    public String getContent() {
        return this.compartment_1 + this.compartment_2;
    }

    @Override
    public String toString() {
        return this.compartment_1 + "|" + this.compartment_2;
    }

    public char findRepeating() throws InvalidContentError {
        for (char item : this.compartment_1.toCharArray()) {
            if (compartment_2.indexOf(item) != -1) {
                return item;
            }
        }

        throw new InvalidContentError("No characters repeat in both compartments.");
    }

    public static Rucksack parseFromString(String rucksackString) {
        final int length = rucksackString.length();
        final String compartment_1 = rucksackString.substring(0, length / 2);
        final String compartment_2 = rucksackString.substring(length / 2);

        return new Rucksack(compartment_1, compartment_2);
    }

    public static int getValueOfItem(char item) throws InvalidItemError {
        if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(item) == -1) {
            throw new InvalidItemError("Invalid item: " + item);
        }

        int codePoint = (int) item;

        return codePoint >= 97 ? codePoint - 96 : codePoint - 64 + 26;
    }

    public static char findCommon(Rucksack rucksack_1, Rucksack rucksack_2, Rucksack rucksack_3)
            throws InvalidContentError {
        for (char item : rucksack_1.getContent().toCharArray()) {
            if (rucksack_2.getContent().indexOf(item) != -1 && rucksack_3.getContent().indexOf(item) != -1) {
                return item;
            }
        }

        throw new InvalidContentError(String.format("No common element in rucksacks: %s %s %s", rucksack_1, rucksack_2,
                rucksack_3));
    }
}
