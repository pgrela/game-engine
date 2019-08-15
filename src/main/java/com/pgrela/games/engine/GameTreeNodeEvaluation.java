package com.pgrela.games.engine;

public class GameTreeNodeEvaluation implements Evaluation {

    private Evaluation evaluation;

    public GameTreeNodeEvaluation(Evaluation evaluation) {

        this.evaluation = evaluation;
    }

    public void update(GameTreeNodeEvaluation newEvaluation) {
        this.evaluation = newEvaluation.evaluation;
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
