package com.pgrela.games.engine.egines;

import com.google.common.collect.HashBiMap;
import com.pgrela.games.engine.api.Board;
import com.pgrela.games.engine.api.Evaluation;
import com.pgrela.games.engine.api.Move;
import com.pgrela.games.engine.api.Player;

import java.util.*;
import java.util.function.Consumer;

class Node {
    private HashBiMap<Move, Node> children;
    private PriorityQueue<Node> bestMoves;
    private Collection<Node> parents;
    private int depth = -1;

    private Board board;
    private final Player currentlyMoving;
    private Evaluation boardEvaluation;
    private Evaluation nodeEvaluation;

    private Move bestContinuation;

    private boolean expanded = false;
    private boolean removed = false;

    Node(Board board, Evaluation boardEvaluation, int depth) {
        this(board, boardEvaluation);
        this.depth = depth;
    }
    Node(Board board, Evaluation boardEvaluation) {
        this.board = board;
        this.boardEvaluation = boardEvaluation;
        nodeEvaluation = boardEvaluation;
        children = HashBiMap.create();
        currentlyMoving = board.getNextPlayer();
        parents = new ArrayList<>();
        bestMoves = new PriorityQueue<>(Comparator.comparingDouble((Node node) -> node.nodeEvaluation.getForPlayer(currentlyMoving)).reversed());
    }

    Board getBoard() {
        return board;
    }

    private void addParent(Node newParent) {
        removed = false;
        parents.add(newParent);
        if (depth == -1) {
            depth = newParent.getDepth() + 1;
        } else {
            if (depth > newParent.getDepth() + 1) {
                depth = newParent.getDepth() + 1;
            }

        }
    }

    boolean wasExpanded() {
        return expanded;
    }

    void markAsExpanded() {
        this.expanded = true;
    }

    private void recalculateEvaluation() {
        if (getChildren().isEmpty()) {
            setBestContinuation(null);
            nodeEvaluation = boardEvaluation;
            notifyParents();
        } else {
            setBestContinuation(children.inverse().get(bestMoves.peek()));
            Node node = children.get(getBestContinuation());
            if (node == null) {
                throw new IllegalStateException();
            }
            Evaluation newEvaluation = node.getNodeEvaluation();
            if (!this.nodeEvaluation.equals(newEvaluation)) {
                this.nodeEvaluation = newEvaluation;
                notifyParents();
            }
        }
    }

    private void childUpdated(Node updatedChild) {
        bestMoves.remove(updatedChild);
        bestMoves.add(updatedChild);
        recalculateEvaluation();
    }

    Evaluation getNodeEvaluation() {
        return nodeEvaluation;
    }

    Move getBestContinuation() {
        return bestContinuation;
    }

    private void setBestContinuation(Move bestContinuation) {
        this.bestContinuation = bestContinuation;
    }

    void embraceAsChild(Move move, Node child) {
        if (children.containsValue(child)) {
            return;
        }
        removed = false;
        child.addParent(this);
        children.put(move, child);
        if (bestMoves.contains(child)) {
            throw new IllegalStateException();
        }
        bestMoves.add(child);
        if (children.size() == 1) {
            nodeEvaluation = child.getNodeEvaluation();
            setBestContinuation(move);
            notifyParents();
        } else {
            childUpdated(child);
        }
    }

    private void notifyParents() {
        getParents().forEach(parent -> parent.childUpdated(this));
    }

    Map<Move, Node> getChildren() {
        return Collections.unmodifiableMap(children);
    }

    Node follow(Move continuation) {
        return getChildren().get(continuation);
    }

    Evaluation getBoardEvaluation() {
        return boardEvaluation;
    }

    Collection<Node> getParents() {
        return Collections.unmodifiableCollection(parents);
    }

    int getDepth() {
        return depth;
    }

    private void removeParent(Node parent) {
        if (!parents.contains(parent)) {
            throw new IllegalStateException();
        }
    }

    boolean isRemoved() {
        return removed;
    }

    void getLostUnless(Map<Node, Boolean> hasRootAsAncestorCache, Consumer<Node> onRemove) {
        if (removed) {
            return;
        }
        if (!hasAncestor(hasRootAsAncestorCache)) {
            ArrayList<Node> cachedChildren = new ArrayList<>(getChildren().values());
            children.forEach((ignore, child1) -> child1.removeParent(this));
            parents.clear();
            bestMoves.clear();
            bestContinuation=null;
            removed = true;
            onRemove.accept(this);
            cachedChildren.forEach(child -> child.getLostUnless(hasRootAsAncestorCache, onRemove));
        }
    }

    private boolean hasAncestor(Map<Node, Boolean> visiting) {
        Boolean cachedResult = visiting.get(this);
        if (cachedResult != null) {
            return cachedResult;
        }
        for (Node parent : getParents()) {
            if (parent.hasAncestor(visiting)) {
                visiting.put(this, true);
                return true;
            }
        }
        visiting.put(this, false);
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node that = (Node) o;

        return board.equals(that.board);
    }

    @Override
    public int hashCode() {
        return board.hashCode();
    }
}
