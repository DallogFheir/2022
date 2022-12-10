package day10;

public final class AdderTimer {
    private final int addend;
    private int timer;

    public AdderTimer(int addend, int timer) {
        this.addend = addend;
        this.timer = timer;
    }

    public int getAddend() {
        return this.addend;
    }

    public void decrementTimer() {
        this.timer--;
    }

    public boolean isTimedOut() {
        return this.timer == 0;
    }
}
