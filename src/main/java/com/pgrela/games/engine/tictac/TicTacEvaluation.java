package com.pgrela.games.engine.tictac;

import com.pgrela.games.engine.Evaluation;
import com.pgrela.games.engine.Player;

public class TicTacEvaluation implements Evaluation {
    private double circle;
    private double cross;
    private int blanks;

    public TicTacEvaluation(double circle, double cross, int blanks) {

        this.circle = circle;
        this.cross = cross;
        this.blanks = blanks;
    }

    @Override
    public double getForPlayer(Player player) {
        if (blanks == 0) {
            if (circle >= 100 || cross >= 100) {
                if (player.equals(TicTacPlayer.CIRCLER)) {
                    return circle >= 100 ? 100 : -100;
                }
                return cross >= 100 ? 100 : -100;
            }
            return 0;
        }
        return (player.equals(TicTacPlayer.CIRCLER) ? circle - cross : cross - circle);
    }

    @Override
    public boolean isDecisive() {
        return circle >= 100 || cross >= 100 || blanks == 0;
    }

    @Override
    public Player getWinner() {
        return circle >= 100 ? TicTacPlayer.CIRCLER : TicTacPlayer.CROSSER;
    }

    @Override
    public boolean isWinner(Player player) {
        return player.equals(getWinner());
    }

    @Override
    public String toString() {
        return "TicTacEvaluation{" +
                "circle=" + getForPlayer(TicTacPlayer.CIRCLER) +
                ", cross=" + getForPlayer(TicTacPlayer.CROSSER) +
                '}';
    }
}
