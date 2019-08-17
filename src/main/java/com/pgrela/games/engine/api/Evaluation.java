package com.pgrela.games.engine.api;

public interface Evaluation {
    double getForPlayer(Player player);
    boolean isDecisive();
    Player getWinner();
    boolean isWinner(Player player);
}
