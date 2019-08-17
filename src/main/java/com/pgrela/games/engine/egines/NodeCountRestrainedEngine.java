package com.pgrela.games.engine.egines;

import com.pgrela.games.engine.api.Evaluator;
import com.pgrela.games.engine.api.Generator;

public class NodeCountRestrainedEngine extends DepthRestrainedEngine {
    private int size;

    public NodeCountRestrainedEngine(Evaluator evaluator, Generator generator, int size) {
        super(evaluator, generator);

        this.size = size;
    }

    @Override
    protected boolean shouldContinue(Node node) {
        return nodes.size() > size;
    }
}
