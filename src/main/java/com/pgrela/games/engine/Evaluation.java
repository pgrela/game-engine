package com.pgrela.games.engine;

public interface Evaluation {
    double getForPlayer(Player player);
    boolean isDecisive();
    Player getWinner();
    boolean isWinner(Player player);
}
