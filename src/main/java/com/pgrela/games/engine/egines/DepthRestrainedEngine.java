package com.pgrela.games.engine.egines;

import com.pgrela.games.engine.api.*;
import com.pgrela.games.engine.tictac.TicTacGameState;

import java.util.*;

public class DepthRestrainedEngine extends BoardEngine implements Engine {
    private Node currentState;
    private int depth;
    private PriorityQueue<Node> leafs;
    Map<Board, Node> nodes = new WeakHashMap<>();
    private Evaluator evaluator;
    private Generator generator;
    private static int cc = 0;

    DepthRestrainedEngine(Evaluator evaluator, Generator generator, int depth) {
        this(evaluator, generator);
        this.depth = depth;
    }

    DepthRestrainedEngine(Evaluator evaluator, Generator generator) {
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
            if(leafs.peek().isRemoved()){
                leafs.poll();
                continue;
            }
            if (shouldNotContinue(leafs.peek())) {
                return;
            }
            follow(leafs.poll());
        }
        System.out.println("Played");
        System.out.println("Analyzed " + nodes.size() + " nodes.");
        System.out.println("Created " + TicTacGameState.c + " states.");
        System.out.println(cc);
    }

    protected boolean shouldNotContinue(Node node) {
        return node.getDepth() - currentState.getDepth() > depth;
    }

    private Node createRootNode(Board board) {
        Node newNode = new Node(board, evaluate(board), 0);
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
        if(anExpandable.isRemoved()){
            return;
        }
        expand(anExpandable);
    }


    private void expand(Node leaf) {
        Map<Move, Board> next = generator.next(leaf.getBoard());
        for (Map.Entry<Move, Board> nextState : next.entrySet()) {
            Move move = nextState.getKey();
            Board board = nextState.getValue();

            Node newNode;
            if (nodes.containsKey(board)) {
                newNode = nodes.get(board);
            } else {
                newNode = new Node(board, evaluate(board));
                nodes.put(board, newNode);
            }

            leaf.embraceAsChild(move, newNode);
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
        if(!currentState.wasExpanded()){
            expand(currentState);
        }
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
        } while (!current.getBoardEvaluation().isDecisive());
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
        System.out.println(nodes.size());
        passedState.getLostUnless(hasCurrentAsAncestor, nodes::remove);
        System.out.println(nodes.size());
        nodes.entrySet().removeIf(e->e.getValue().isRemoved());
        System.out.println(nodes.size());
    }

}
