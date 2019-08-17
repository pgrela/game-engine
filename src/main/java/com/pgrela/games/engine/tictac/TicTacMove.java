package com.pgrela.games.engine.tictac;

import com.pgrela.games.engine.api.Move;

public class TicTacMove implements Move {
    Symbol symbol;
    int x;
    int y;

    public TicTacMove(Symbol symbol, int x, int y) {
        this.symbol = symbol;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicTacMove that = (TicTacMove) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        return symbol == that.symbol;
    }

    @Override
    public int hashCode() {
        int result = symbol != null ? symbol.hashCode() : 0;
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "TicTacMove{" +
                "symbol=" + symbol +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
