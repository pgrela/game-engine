package com.pgrela.games.engine.connect4;

enum Tile {
    BLUE("\u001B[36mO\u001B[0m"), RED("\u001B[31mX\u001B[0m"), BLANK(" ");
    String symbol;

    Tile(String symbol) {
        this.symbol = symbol;
    }
}
