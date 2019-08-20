package com.pgrela.games.engine.connect4;


import static com.pgrela.games.engine.connect4.Connect4CompactBoard.COLUMNS;
import static com.pgrela.games.engine.connect4.Connect4CompactBoardEvaluator.CROSS;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Connect4CompactBoardGeneratorTest {


  private Connect4CompactBoardGenerator generator = new Connect4CompactBoardGenerator();
  private Connect4CompactBoardFactory factory = new Connect4CompactBoardFactory();

  @Test
  public void test2() {
    Connect4CompactBoard board = factory.initialState();
    Connect4CompactBoard newBoard = generator.apply(board, Connect4Move.getMove(0));
    System.out.println(newBoard);
    assertThat(newBoard.getCode(6,0)).isEqualTo(board.getNextPlayer().getSymbol().getCode());
  }

}