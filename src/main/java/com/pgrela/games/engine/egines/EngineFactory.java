package com.pgrela.games.engine.egines;

import com.pgrela.games.engine.api.Engine;
import com.pgrela.games.engine.api.Rules;

public class EngineFactory {

    private Rules game;
    private boolean initialized = false;

    public EngineFactory forRules(Rules game) {
        this.game = game;
        return this;
    }

    public ExploringEngine exploringEngine(int depth) {
        ExploringEngine exploringEngine = new ExploringEngine(game.getEvaluator(), game.getGenerator(), depth);
        initialize(exploringEngine);
        return exploringEngine;
    }

    public DepthRestrainedEngine depthRestrainedEngine(int depth) {
        DepthRestrainedEngine engine = new DepthRestrainedEngine(game.getEvaluator(), game.getGenerator(), depth);
        initialize(engine);
        return engine;
    }
    public NodeCountRestrainedEngine nodeCountRestrainedEngine(int size) {
        NodeCountRestrainedEngine engine = new NodeCountRestrainedEngine(game.getEvaluator(), game.getGenerator(), size);
        initialize(engine);
        return engine;
    }

    private void initialize(Engine engine) {
        if(initialized){
            engine.initialize(game.initialBoard());
        }
    }

    public EngineFactory initialized() {
        this.initialized = true;
        return this;
    }
    public EngineFactory notInitialized() {
        this.initialized = false;
        return this;
    }
}
