package day08;

import java.util.ArrayList;

public final class TreeGrid {
    private final Tree[][] grid;

    public TreeGrid(ArrayList<String> lines) {
        this.grid = new Tree[lines.size()][];

        for (int i = 0; i < lines.size(); i++) {
            this.grid[i] = new Tree[lines.get(i).length()];

            for (int j = 0; j < lines.get(i).length(); j++) {
                this.grid[i][j] = new Tree(Character.getNumericValue(lines.get(i).charAt(j)));
            }
        }

        this.setVisible();
        this.setScenicScores();
    }

    public int findVisible() {
        int count = 0;
        for (Tree[] row : this.grid) {
            for (Tree tree : row) {
                if (tree.getVisible()) {
                    count++;
                }
            }
        }

        return count;
    }

    public int findHighestScenicScore() {
        int max = 0;
        for (Tree[] row : this.grid) {
            for (Tree tree : row) {
                if (tree.getScenicScore() > max) {
                    max = tree.getScenicScore();
                }
            }
        }

        return max;
    }

    private void setVisible() {
        final int[] columnMaxFromTop = new int[this.grid[0].length];
        for (int i = 0; i < columnMaxFromTop.length; i++) {
            columnMaxFromTop[i] = -1;
        }

        for (int i = 0; i < this.grid.length; i++) {
            int rowMaxFromLeft = -1;
            for (int j = 0; j < this.grid[i].length; j++) {
                final Tree tree = this.grid[i][j];

                if (tree.getHeight() > rowMaxFromLeft) {
                    rowMaxFromLeft = tree.getHeight();
                    tree.setVisible();
                }

                if (tree.getHeight() > columnMaxFromTop[j]) {
                    columnMaxFromTop[j] = tree.getHeight();
                    tree.setVisible();
                }
            }
        }

        final int[] columnMaxFromBottom = new int[this.grid[0].length];
        for (int i = 0; i < columnMaxFromBottom.length; i++) {
            columnMaxFromBottom[i] = -1;
        }

        for (int i = this.grid.length - 1; i >= 0; i--) {
            int rowMaxFromRight = -1;
            for (int j = this.grid[i].length - 1; j >= 0; j--) {
                final Tree tree = this.grid[i][j];

                if (tree.getHeight() > rowMaxFromRight) {
                    rowMaxFromRight = tree.getHeight();
                    tree.setVisible();
                }

                if (tree.getHeight() > columnMaxFromBottom[j]) {
                    columnMaxFromBottom[j] = tree.getHeight();
                    tree.setVisible();
                }
            }
        }
    }

    private void setScenicScores() {
        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[i].length; j++) {
                final Tree tree = this.grid[i][j];

                // to the left
                int scoreLeft = 0;
                for (int k = j - 1; k >= 0; k--) {
                    final Tree otherTree = this.grid[i][k];

                    scoreLeft++;

                    if (otherTree.getHeight() >= tree.getHeight()) {
                        break;
                    }

                }

                // to the right
                int scoreRight = 0;
                for (int k = j + 1; k < this.grid[i].length; k++) {
                    final Tree otherTree = this.grid[i][k];

                    scoreRight++;

                    if (otherTree.getHeight() >= tree.getHeight()) {
                        break;
                    }

                }

                // up
                int scoreTop = 0;
                for (int k = i - 1; k >= 0; k--) {
                    final Tree otherTree = this.grid[k][j];

                    scoreTop++;

                    if (otherTree.getHeight() >= tree.getHeight()) {
                        break;
                    }

                }

                // down
                int scoreBottom = 0;
                for (int k = i + 1; k < this.grid.length; k++) {
                    final Tree otherTree = this.grid[k][j];

                    scoreBottom++;

                    if (otherTree.getHeight() >= tree.getHeight()) {
                        break;
                    }
                }

                tree.setScenicScore(scoreLeft * scoreRight * scoreTop * scoreBottom);
            }
        }
    }
}
