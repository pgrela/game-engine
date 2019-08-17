package com.pgrela.games.engine.dummy;

import com.pgrela.games.engine.api.InitialStateFactory;

public class DummyGameStateFactory implements InitialStateFactory {
    @Override
    public DummyState initialState() {
        return new DummyState(0);
    }
}
