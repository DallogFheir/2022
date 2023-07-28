package day02;

import java.util.HashMap;

public class Round {
    private Choice opponent;
    private Choice player;

    private enum Result {
        PLAYER,
        DRAW,
        OPPONENT
    };

    private enum Choice {
        ROCK,
        PAPER,
        SCISSORS
    };

    public Round(Choice opponent, Choice player) {
        this.opponent = opponent;
        this.player = player;
    }

    public Round(Choice opponent, Result result) {
        this.opponent = opponent;
        this.player = this.findPlayerMove(opponent, result);
    }

    public int calculateScore() {
        HashMap<Choice, Integer> choiceScores = new HashMap<Choice, Integer>();
        choiceScores.put(Choice.ROCK, 1);
        choiceScores.put(Choice.PAPER, 2);
        choiceScores.put(Choice.SCISSORS, 3);

        HashMap<Result, Integer> resultScores = new HashMap<Result, Integer>();
        resultScores.put(Result.OPPONENT, 0);
        resultScores.put(Result.DRAW, 3);
        resultScores.put(Result.PLAYER, 6);

        return choiceScores.get(this.player) + resultScores.get(this.getWinner());
    }

    private Result getWinner() {
        if (this.opponent == this.player) {
            return Result.DRAW;
        } else if ((this.opponent == Choice.ROCK && this.player == Choice.SCISSORS) ||
                (this.opponent == Choice.PAPER && this.player == Choice.ROCK) ||
                (this.opponent == Choice.SCISSORS && this.player == Choice.PAPER)) {
            return Result.OPPONENT;
        } else {
            return Result.PLAYER;
        }
    }

    private Choice findPlayerMove(Choice opponent, Result result) {
        switch (result) {
            case PLAYER:
                final HashMap<Choice, Choice> winChoices = new HashMap<Choice, Choice>();
                winChoices.put(Choice.ROCK, Choice.PAPER);
                winChoices.put(Choice.PAPER, Choice.SCISSORS);
                winChoices.put(Choice.SCISSORS, Choice.ROCK);

                return winChoices.get(opponent);
            case DRAW:
                return opponent;
            case OPPONENT:
                final HashMap<Choice, Choice> lossChoices = new HashMap<Choice, Choice>();
                lossChoices.put(Choice.ROCK, Choice.SCISSORS);
                lossChoices.put(Choice.PAPER, Choice.ROCK);
                lossChoices.put(Choice.SCISSORS, Choice.PAPER);

                return lossChoices.get(opponent);
            default:
                throw new IllegalArgumentException("Unknown choice " + opponent);
        }
    }

    public static Round parseLine(String line, Mode mode) {
        final char opponentCode = line.charAt(0);
        final char playerOrResultCode = line.charAt(2);

        final HashMap<Character, Choice> opponentTrans = new HashMap<Character, Choice>();
        opponentTrans.put('A', Choice.ROCK);
        opponentTrans.put('B', Choice.PAPER);
        opponentTrans.put('C', Choice.SCISSORS);

        final HashMap<Character, Choice> playerTrans = new HashMap<Character, Choice>();
        playerTrans.put('X', Choice.ROCK);
        playerTrans.put('Y', Choice.PAPER);
        playerTrans.put('Z', Choice.SCISSORS);

        final HashMap<Character, Result> resultTrans = new HashMap<Character, Result>();
        resultTrans.put('X', Result.OPPONENT);
        resultTrans.put('Y', Result.DRAW);
        resultTrans.put('Z', Result.PLAYER);

        final Choice opponent = opponentTrans.get(opponentCode);
        switch (mode) {
            case PLAYER_CHOICE:
                final Choice player = playerTrans.get(playerOrResultCode);
                return new Round(opponent, player);
            case RESULT:
                final Result result = resultTrans.get(playerOrResultCode);
                return new Round(opponent, result);
            default:
                throw new IllegalArgumentException("Unknown mode " + mode);
        }

    }
}
