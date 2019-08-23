package com.pgrela.games.engine.dummy;

import com.pgrela.games.engine.api.Evaluation;
import com.pgrela.games.engine.api.Player;

public class TwoPlayersEvaluation implements Evaluation {

    private Player player;
    private double evaluation;
    private boolean decisive = false;

    public static final double MAX = 1000;
    public static final Evaluation DRAWING = new TwoPlayersEvaluation(true);
    public static final Evaluation DRAWN = new TwoPlayersEvaluation(false);

    private TwoPlayersEvaluation(boolean decisive) {
        evaluation = 0;
        this.decisive = decisive;
    }

    public TwoPlayersEvaluation(Player winner) {
        this.player = winner;
        evaluation = MAX;
        decisive = true;
    }

    public TwoPlayersEvaluation(Player player, double evaluation) {
        this.player = player;
        this.evaluation = evaluation;
    }

    @Override
    public double getForPlayer(Player player) {
        return player.equals(this.player) ? evaluation : -evaluation;
    }

    @Override
    public boolean isDecisive() {
        return decisive;
    }

    @Override
    public Player getWinner() {
        return decisive ? player : null;
    }

    @Override
    public boolean isWinner(Player player) {
        return decisive && player.equals(getWinner());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TwoPlayersEvaluation that = (TwoPlayersEvaluation) o;

        if (Double.compare(that.evaluation, evaluation) != 0) return false;
        if (decisive != that.decisive) return false;
        return player != null ? player.equals(that.player) : that.player == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = player != null ? player.hashCode() : 0;
        temp = Double.doubleToLongBits(evaluation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (decisive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TwoPlayersEvaluation{" +
                "player=" + player +
                ", evaluation=" + evaluation +
                ", decisive=" + decisive +
                '}';
    }
}
