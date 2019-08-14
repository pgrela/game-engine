package com.pgrela.games.engine.tictac;

import com.pgrela.games.engine.Player;

public enum TicTacPlayer implements Player {
    CIRCLER(Symbol.CIRCLE), CROSSER(Symbol.CROSS);


    private Symbol symbol;

    TicTacPlayer(Symbol symbol) {
        this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public TicTacPlayer next() {
        return this.equals(CIRCLER) ? CROSSER : CIRCLER;
    }
}
