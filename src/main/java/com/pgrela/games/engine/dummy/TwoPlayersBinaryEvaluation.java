package com.pgrela.games.engine.dummy;

import com.pgrela.games.engine.api.Evaluation;
import com.pgrela.games.engine.api.Player;

public class TwoPlayersBinaryEvaluation implements Evaluation {

    private Player winner = null;
    boolean decisive;

    public static final double MAX = 100;
    public static final Evaluation DRAWN = new TwoPlayersBinaryEvaluation(true);
    public static final Evaluation DRAWING = new TwoPlayersBinaryEvaluation(false);

    private TwoPlayersBinaryEvaluation(boolean decisive) {
        this.decisive = decisive;
    }

    public TwoPlayersBinaryEvaluation(Player winner) {
        this.winner = winner;
        decisive = true;
    }

    @Override
    public double getForPlayer(Player player) {
        return isDecisive()?player.equals(winner) ? MAX : -MAX : 0;
    }

    @Override
    public boolean isDecisive() {
        return decisive;
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
        if(isDecisive()){
            if(getWinner()==null)
                return "DRAWN";
            return "TwoPlayersBinaryEvaluation{" +
                    "winner=" + winner +
                    '}';
        }
        return "DRAWING";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TwoPlayersBinaryEvaluation that = (TwoPlayersBinaryEvaluation) o;

        if (decisive != that.decisive) return false;
        return winner != null ? winner.equals(that.winner) : that.winner == null;
    }

    @Override
    public int hashCode() {
        int result = winner != null ? winner.hashCode() : 0;
        result = 31 * result + (decisive ? 1 : 0);
        return result;
    }
}
