package com.pgrela.games.engine.connect4;

enum Tile {
    BLUE("\u001B[36mO\u001B[0m", Connect4CompactBoardEvaluator.CIRCLE), RED("\u001B[31mX\u001B[0m", Connect4CompactBoardEvaluator.CROSS), BLANK(" ", Connect4CompactBoardEvaluator.BLANK);
    String symbol;
    private long code;

    Tile(String symbol, long code) {
        this.symbol = symbol;
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public long getCode() {
        return code;
    }
}
