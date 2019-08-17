package com.pgrela.games.engine.dummy;

import com.pgrela.games.engine.api.Board;
import com.pgrela.games.engine.api.Player;

public class DummyState implements Board {
    public static int c=0;
    private int number;

    public DummyState(int number) {
        this.number = number;
        ++c;
    }

    @Override
    public Player getNextPlayer() {
        return DummyPlayer.STEVEN;
    }

    public int getNumber() {
        return number;
    }
}
