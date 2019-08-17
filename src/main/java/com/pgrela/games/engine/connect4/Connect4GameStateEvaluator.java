package com.pgrela.games.engine.connect4;

import com.pgrela.games.engine.api.Evaluation;
import com.pgrela.games.engine.api.EvaluatorImpl;
import com.pgrela.games.engine.dummy.TwoPlayersBinaryEvaluation;

import java.util.ArrayList;
import java.util.List;

public class Connect4GameStateEvaluator implements EvaluatorImpl<Connect4Board> {
    static private List<Integer[][]> LINES = new ArrayList<>();

    static {

        // row lines
        for (int row = 0; row < 7; row++) {
            for (int column = 0; column < 4; column++) {
                Integer[][] line = new Integer[4][2];
                for (int j = 0; j < 4; j++) {
                    line[j][0] = column + j;
                    line[j][1] = row;
                }
                LINES.add(line);
            }
        }
        // column lines                                
        for (int column = 0; column < 7; column++) {
            for (int row = 0; row < 4; row++) {
                Integer[][] line = new Integer[4][2];
                for (int j = 0; j < 4; j++) {
                    line[j][0] = column;
                    line[j][1] = row + j;
                }
                LINES.add(line);
            }
        }
        // diagonals up-right lines
        for (int column = 0; column < 4; column++) {
            for (int row = 0; row < 4; row++) {
                Integer[][] line = new Integer[4][2];
                for (int j = 0; j < 4; j++) {
                    line[j][0] = row + j;
                    line[j][1] = column + j;
                }
                LINES.add(line);
            }
        }
        // diagonals up-left lines
        for (int column = 3; column < 7; column++) {
            for (int row = 0; row < 4; row++) {
                Integer[][] line = new Integer[4][2];
                for (int j = 0; j < 4; j++) {
                    line[j][0] = row + j;
                    line[j][1] = column - j;
                }
                LINES.add(line);
            }
        }
    }

    @Override
    public Evaluation evaluateBoard(Connect4Board gameState) {

        outer:
        for (Integer[][] line : LINES) {
            Tile tile = gameState.board[line[0][0]][line[0][1]];
            if (tile != Tile.BLANK) {
                for (int i = 1; i < 4; i++) {
                    if (tile != gameState.board[line[i][0]][line[i][1]]) {
                        continue outer;
                    }
                }
                return new TwoPlayersBinaryEvaluation(Connect4Player.ownerOf(tile));
            }
        }
        for (int i = 0; i < 7; i++) {
            if (gameState.board[i][6] == Tile.BLANK)
                return TwoPlayersBinaryEvaluation.DRAWING;
        }
        return TwoPlayersBinaryEvaluation.DRAWN;
    }
}
