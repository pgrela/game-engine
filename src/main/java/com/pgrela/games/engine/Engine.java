package com.pgrela.games.engine;

import com.pgrela.games.engine.tictac.TicTacGameState;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Engine {
    private GameTreeNode currentState;
    private int depth = 6;
    private PriorityQueue<GameTreeNode> leafs;
    private Map<GameState, GameTreeNode> nodes = new HashMap<>();

    public void start(GameState state) {
        leafs = new PriorityQueue<>(Comparator.comparingDouble(a -> a.getEvaluation().getForPlayer(a.getState().getNextPlayer())));
        currentState = new GameTreeNode(state, leafs, nodes);
        leafs.add(currentState);
        while (!leafs.isEmpty()) {
            follow();
        }
        GameTreeNode current = currentState;
        System.out.println(current.getState());
        do {
            current = current.getBestContinuation();
            System.out.println(current.getState() + " " + current.getEvaluation());
        } while (!current.getState().getEvaluation().isDecisive());
        System.out.println("Played");
        System.out.println("Analyzed " + nodes.size() + " nodes.");
        System.out.println("Created " + TicTacGameState.c + " states.");
    }

    private void follow() {
        GameTreeNode leaf = leafs.poll();
        leaf.expand();
    }
}
