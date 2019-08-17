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

}
