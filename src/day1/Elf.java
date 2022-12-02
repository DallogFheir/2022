package day1;

import java.util.ArrayList;

public class Elf {
    private ArrayList<Integer> calories;

    public Elf(ArrayList<Integer> calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return Integer.toString(this.getAllCalories());
    }

    public int getAllCalories() {
        int sum = 0;

        for (int calorie : this.calories) {
            sum += calorie;
        }

        return sum;
    }
}
