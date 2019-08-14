package com.pgrela.games.engine;

public interface GameStateEvaluation {
    double getForPlayer(Player player);
    boolean isDecisive();
    Player getWinner();
}
