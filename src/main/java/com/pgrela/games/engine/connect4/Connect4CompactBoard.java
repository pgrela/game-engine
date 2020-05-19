package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.Board;
import com.pgrela.games.engine.connect4.game.MagicBoard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.pgrela.games.engine.connect4.game.MagicBoard.BLANK;

public class Connect4CompactBoard implements Board {

    public static final int COLUMNS = 7;
    public static final int ROWS = 6;
    public static final int CONNECT = 4;
    final MagicBoard board;
    private final Connect4Player nextPlayer;
    private Integer hashCode = null;
    private final int blanks;

    static final Map<Tile, Long> PLAYER_SYMBOLS = new HashMap<>();

    static {
        PLAYER_SYMBOLS.put(Tile.BLUE, MagicBoard.CIRCLE);
        PLAYER_SYMBOLS.put(Tile.RED, MagicBoard.CROSS);
        PLAYER_SYMBOLS.put(Tile.BLANK, BLANK);
    }

    public Connect4CompactBoard(MagicBoard board, Connect4Player nextPlayer, int blanks) {
        this.board = board;
        this.nextPlayer = nextPlayer;
        this.blanks = blanks;
    }

    @Override
    public Connect4Player getNextPlayer() {
        return nextPlayer;
    }


    boolean isBlank(int row, int column) {
        return board.getCode(row, column) == 0L;
    }

    public boolean isColumnFull(int column) {
        return board.getCode(0, column) != BLANK;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Tile[][] board = asArray();
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                stringBuilder.append(board[column][row].getSymbol());
            }
            stringBuilder.append("\n");
        }
        for (int i = 0; i < COLUMNS; i++) {
            stringBuilder.append("-");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        Connect4CompactBoard that = (Connect4CompactBoard) o;

        if (blanks != that.blanks) {
            return false;
        }
        if ((board.left != that.board.left || board.right != that.board.right) && (board.left != that.board.right || board.right != that.board.left)) {
            return false;
        }
        return nextPlayer == that.nextPlayer;
    }

    @Override
    public int hashCode() {
        if (hashCode != null) {
            return hashCode;
        }
        int result = board.hashCode();
        result = 31 * result + (nextPlayer != null ? nextPlayer.hashCode() : 0);
        result = 31 * result + blanks;
        return hashCode = result;
    }

    public int getBlanks() {
        return blanks;
    }

    Tile[][] asArray() {
        Tile[][] tiles = new Tile[COLUMNS][ROWS];
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                long code = board.getCode(row, column);
                tiles[column][row] = Arrays.stream(Tile.values()).filter(tile -> Connect4CompactBoard.PLAYER_SYMBOLS.get(tile) == code)
                        .findFirst().get();
            }
        }
        return tiles;
    }
}
