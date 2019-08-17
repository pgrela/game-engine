package com.pgrela.games.engine.egines;

import com.pgrela.games.engine.api.Evaluation;
import com.pgrela.games.engine.api.Player;

public class GameTreeNodeEvaluation implements Evaluation {

    private Evaluation evaluation;

    public GameTreeNodeEvaluation() {
    }

    public void update(GameTreeNodeEvaluation newEvaluation) {
        this.evaluation = newEvaluation.evaluation;
    }

    public void update(Evaluation newEvaluation) {
        this.evaluation = newEvaluation;
    }

    @Override
    public double getForPlayer(Player player) {
        return evaluation.getForPlayer(player);
    }

    @Override
    public boolean isDecisive() {
        return evaluation.isDecisive();
    }

    @Override
    public Player getWinner() {
        return evaluation.getWinner();
    }

    @Override
    public boolean isWinner(Player player) {
        return evaluation.isWinner(player);
    }

    @Override
    public String toString() {
        return "GameTreeNodeEvaluation{" +
                "evaluation=" + evaluation +
                '}';
    }
}
