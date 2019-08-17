package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.Evaluation;
import com.pgrela.games.engine.api.Player;

public class Connect4Evaluation implements Evaluation {
    private double evaluationRed = 0;
    private Player winner = null;
    private Boolean draw = null;

    public Connect4Evaluation(double evaluationRed) {
        this.evaluationRed = evaluationRed;
    }

    public Connect4Evaluation(Tile tile) {
        winner = Connect4Player.ownerOf(tile);
    }

    @Override
    public double getForPlayer(Player player) {
        return Connect4Player.RED.equals(player) ? evaluationRed : -evaluationRed;
    }

    @Override
    public boolean isDecisive() {
        return draw != null || winner != null;
    }

    @Override
    public Player getWinner() {
        return winner;
    }

    @Override
    public boolean isWinner(Player player) {
        return player.equals(getWinner());
    }

    @Override
    public String toString() {
        return "Connect4Evaluation{" +
                "evaluationRed=" + evaluationRed +
                ", winner=" + winner +
                ", draw=" + draw +
                '}';
    }
}
