package com.pgrela.games.engine.egines;

import com.google.common.collect.HashBiMap;
import com.pgrela.games.engine.api.Board;
import com.pgrela.games.engine.api.Evaluation;
import com.pgrela.games.engine.api.Move;
import com.pgrela.games.engine.api.Player;

import java.util.*;

class Node {
    private HashBiMap<Move, Node> children;
    private PriorityQueue<Node> bestMoves;
    private Collection<Node> parents;
    private int depth;

    private Board board;
    private final Player currentlyMoving;
    private Evaluation boardEvaluation;
    private Evaluation nodeEvaluation;

    private Move bestContinuation;

    private boolean expanded = false;
    private boolean removed = false;

    Node(Board board, Evaluation boardEvaluation) {
        this.board = board;
        this.boardEvaluation = boardEvaluation;
        nodeEvaluation = boardEvaluation;
        children = HashBiMap.create();
        currentlyMoving = board.getNextPlayer();
        depth = 0;
        parents = new ArrayList<>();
        bestMoves = new PriorityQueue<>(Comparator.comparingDouble((Node node) -> node.nodeEvaluation.getForPlayer(currentlyMoving)).reversed());
    }

    Board getBoard() {
        return board;
    }

    private void addParent(Node newParent) {
        removed = false;
        parents.add(newParent);
        if (parents.size() == 1) {
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

    private void disconnect() {
        disconnectChildren();
        disconnectParents();
        removed = true;
    }

    private void disconnectParents() {
        parents.forEach(this::removeChild);
        parents.clear();
    }

    private void disconnectChildren() {
        children.forEach((ignore, child) -> child.removeParent(this));
        children.clear();
        bestMoves.clear();
        bestContinuation=null;
    }

    private void removeParent(Node parent) {
        if (!parents.contains(parent)) {
            throw new IllegalStateException();
        }
        parents.remove(parent);
        if (parents.isEmpty()) {
            depth = 0;
        } else {
            depth = parents.stream().mapToInt(Node::getDepth).min().getAsInt() + 1;
        }
    }

    boolean isRemoved() {
        return removed;
    }

    private void removeChild(Node parent) {
        parent.bestMoves.remove(this);
        parent.children.inverse().remove(this);
        parent.recalculateEvaluation();
    }

    void getLostUnless(Map<Node, Boolean> hasRootAsAncestorCache) {
        if (removed) return;
        if (!hasAncestor(hasRootAsAncestorCache)) {
            ArrayList<Node> cachedChildren = new ArrayList<>(getChildren().values());
            disconnect();
            cachedChildren.forEach(child -> child.getLostUnless(hasRootAsAncestorCache));
        }
    }

    private boolean hasAncestor(Map<Node, Boolean> visited) {
        Boolean cachedResult = visited.get(this);
        if (cachedResult != null) {
            return cachedResult;
        }
        for (Node parent : getParents()) {
            if (parent.hasAncestor(visited)) {
                visited.put(this, true);
                return true;
            }
        }
        visited.put(this, false);
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
