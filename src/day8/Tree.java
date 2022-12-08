package day8;

public final class Tree {
    private final int height;
    private int scenicScore;
    private boolean visible = false;

    public Tree(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    public int getScenicScore() {
        return this.scenicScore;
    }

    public void setScenicScore(int scenicScore) {
        this.scenicScore = scenicScore;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public void setVisible() {
        this.visible = true;
    }
}
