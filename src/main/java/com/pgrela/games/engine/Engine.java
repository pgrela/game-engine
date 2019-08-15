package com.pgrela.games.engine;

import com.pgrela.games.engine.tictac.TicTacGameState;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Engine<MOVE extends Move, GAME_STATE extends GameState> {
    private GameTreeNode<MOVE, GAME_STATE> currentState;
    private int depth = 6;
    private PriorityQueue<GameTreeNode<MOVE, GAME_STATE>> leafs;
    private Map<GAME_STATE, GameTreeNode<MOVE, GAME_STATE>> nodes = new HashMap<>();
    private GameStateEvaluator<GAME_STATE> evaluator;
    private GameStateGenerator<MOVE, GAME_STATE> generator;

    public Engine(GameStateEvaluator<GAME_STATE> evaluator, GameStateGenerator<MOVE, GAME_STATE> generator) {
        this.evaluator = evaluator;
        this.generator = generator;
    }

    public void start(GAME_STATE state) {
        leafs = new PriorityQueue<>(Comparator.comparingDouble(a -> a.getNodeEvaluation().getForPlayer(a.getState().getNextPlayer())));
        currentState = new GameTreeNode<>(state, this);
        leafs.add(currentState);
        while (!leafs.isEmpty()) {
            follow();
        }
        GameTreeNode<MOVE, GAME_STATE> current = currentState;
        System.out.println(current.getState());
        Move continuation;
        do {
            continuation = current.getBestContinuation();
            current = current.follow(continuation);
            System.out.println(current.getState() + " " + current.getNodeEvaluation() + " by " + continuation);
        } while (!current.getStateEvaluation().isDecisive());
        System.out.println("Played");
        System.out.println("Analyzed " + nodes.size() + " nodes.");
        System.out.println("Created " + TicTacGameState.c + " states.");
    }

    private void follow() {
        GameTreeNode<MOVE, GAME_STATE> leaf = leafs.poll();
        expand(leaf);
    }


    public void expand(GameTreeNode<MOVE, GAME_STATE> leaf) {
        Map<MOVE, GAME_STATE> next = generator.next(leaf.getState());
        for (Map.Entry<MOVE, GAME_STATE> nextState : next.entrySet()) {
            GAME_STATE gameState = nextState.getValue();
            if (nodes.containsKey(gameState)) {
                GameTreeNode<MOVE, GAME_STATE> node = nodes.get(gameState);
                node.addParent(leaf);
                leaf.addChild(nextState.getKey(), node);
            } else {
                GameTreeNode<MOVE, GAME_STATE> newNode = new GameTreeNode<>(gameState, leaf, this);
                nodes.put(gameState, newNode);
                leaf.addChild(nextState.getKey(), newNode);
            }
        }
        if (leaf.getChildren().isEmpty()) {
            return;
        }
        leaf.updateEvaluation();
        leafs.addAll(leaf.getChildren().values().stream()
                .filter(node -> !node.getNodeEvaluation().isDecisive())
                .filter(node -> !leafs.contains(node))
                .filter(node -> !node.wasExpanded())
                .collect(Collectors.toList()));
        leaf.markAsExpanded();
    }

    public Evaluation evaluate(GAME_STATE state) {
        return evaluator.evaluate(state);
    }
}
