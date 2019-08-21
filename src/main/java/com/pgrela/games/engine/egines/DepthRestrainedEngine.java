package com.pgrela.games.engine.egines;

import com.pgrela.games.engine.api.*;
import com.pgrela.games.engine.tictac.TicTacGameState;

import java.util.*;

public class DepthRestrainedEngine extends BoardEngine implements Engine {
    private Node currentState;
    private int depth;
    private PriorityQueue<Node> leafs;
    protected Map<Board, Node> nodes = new HashMap<>();
    private Evaluator evaluator;
    private Generator generator;
    public static int cc = 0;

    public DepthRestrainedEngine(Evaluator evaluator, Generator generator, int depth) {
        this(evaluator, generator);
        this.depth = depth;
    }

    public DepthRestrainedEngine(Evaluator evaluator, Generator generator) {
        this.evaluator = evaluator;
        this.generator = generator;
        leafs = new PriorityQueue<>(Comparator.comparingInt(Node::getDepth));
    }

    @Override
    public void initialize(Board board) {
        super.initialize(board);
        currentState = createRootNode(this.board);
        leafs.add(currentState);
    }

    @Override
    public void start() {
        while (!leafs.isEmpty()) {
            if (shouldContinue(leafs.peek())) {
                return;
            }
            follow(leafs.poll());

        }
        System.out.println("Played");
        System.out.println("Analyzed " + nodes.size() + " nodes.");
        System.out.println("Created " + TicTacGameState.c + " states.");
        System.out.println(cc);
    }

    protected boolean shouldContinue(Node node) {
        return node.getDepth() - currentState.getDepth() > depth;
    }

    private Node createRootNode(Board board) {
        Node newNode = new Node(board, evaluate(board));
        nodes.put(board, newNode);

        return newNode;
    }

    private void follow(Node anExpandable) {
        Collection<Node> parents = anExpandable.getParents();
        if (anExpandable.getNodeEvaluation().isDecisive() || (!parents.isEmpty() && parents.stream().allMatch(node -> node.getNodeEvaluation().isDecisive()))) {
            return;
        }
        if (anExpandable.wasExpanded()) {
            return;
        }
        if (!anExpandable.hasAncestor(currentState)) {
            nodes.remove(anExpandable.getBoard());
            return;
        }
        expand(anExpandable);
    }


    public void expand(Node leaf) {
        Map<Move, Board> next = generator.next(leaf.getBoard());
        for (Map.Entry<Move, Board> nextState : next.entrySet()) {
            Move move = nextState.getKey();
            Board board = nextState.getValue();

            Node newNode;
            if (nodes.containsKey(board)) {
                newNode = nodes.get(board);
                newNode.addParent(leaf);
            } else {
                newNode = new Node(board, evaluate(board), leaf);
                nodes.put(board, newNode);
            }
            leaf.addChild(move, newNode);
        }
        for (Node child : leaf.getChildren().values()) {
            if (!child.getNodeEvaluation().isDecisive() && !child.wasExpanded()) {
                leafs.add(child);
            }
        }
        leaf.markAsExpanded();
    }

    private Evaluation evaluate(Board state) {
        return evaluator.evaluate(state);
    }

    @Override
    public Move getBestMove() {
        return currentState.getBestContinuation();
    }

    @Override
    public List<Move> getBestMovesSequence() {
        List<Move> moves = new ArrayList<>();
        Move continuation;
        Node current = currentState;
        do {
            continuation = current.getBestContinuation();
            current = current.follow(continuation);
            if (current == null) break;
            moves.add(continuation);
        } while (!current.getStateEvaluation().isDecisive());
        return moves;
    }

    @Override
    public Evaluation getEvaluation() {
        return currentState.getNodeEvaluation();
    }

    @Override
    public Board getCurrentBoard() {
        return currentState.getBoard();
    }

    @Override
    public void move(Move move) {
        if (move == null) {
            throw new IllegalArgumentException();
        }
        if (!currentState.wasExpanded()) {
            expand(currentState);
        }
        Node passedState = this.currentState;
        this.currentState = passedState.follow(move);
        Map<Node,Boolean> hasCurrentAsAncestor = new HashMap<>();
        hasCurrentAsAncestor.put(currentState, true);
        for (Node child : new ArrayList<>(passedState.getChildren().values())) {
            child.getLostUnless(hasCurrentAsAncestor, passedState);
        }
        /*Iterator<Map.Entry<Board, Node>> iterator = this.nodes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Board, Node> next = iterator.next();
            if (!next.getValue().hasAncestor(this.currentState)) {
                next.getValue().disconnect();
                iterator.remove();
            }
        } */
    }

}
