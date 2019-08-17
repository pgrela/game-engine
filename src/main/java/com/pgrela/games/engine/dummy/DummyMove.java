package com.pgrela.games.engine.dummy;

import com.pgrela.games.engine.api.Move;

public class DummyMove implements Move {

    private int i;

    public DummyMove(int i) {

        this.i = i;
    }

    public int getI() {
        return i;
    }
}
