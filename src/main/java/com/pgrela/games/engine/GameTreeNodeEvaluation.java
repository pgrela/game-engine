package com.pgrela.games.engine;

public class GameTreeNodeEvaluation {

    private GameStateEvaluation evaluation;

    public GameTreeNodeEvaluation(GameStateEvaluation evaluation) {

        this.evaluation = evaluation;
    }

    public void update(GameTreeNodeEvaluation newEvaluation) {
        this.evaluation = newEvaluation.evaluation;
    }

    double getForPlayer(Player player){
        return evaluation.getForPlayer(player);
    }

    @Override
    public String toString() {
        return "GameTreeNodeEvaluation{" +
                "evaluation=" + evaluation +
                '}';
    }
}
