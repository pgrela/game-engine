package com.pgrela.games.engine;

import com.pgrela.games.engine.api.Engine;
import com.pgrela.games.engine.api.Move;
import com.pgrela.games.engine.connect4.Connect4;
import com.pgrela.games.engine.dummy.Dummy;
import com.pgrela.games.engine.egines.EngineFactory;
import com.pgrela.games.engine.tictac.TicTac;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class GameRunner {
    public static void main(String[] args) throws IOException {
        EngineFactory engineFactory = new EngineFactory().initialized();

        Engine ticTac = engineFactory.forRules(new TicTac()).depthRestrainedEngine(2);
        Engine connect4 = engineFactory.forRules(new Connect4()).nodeCountRestrainedEngine(100000);
        Engine dummy = engineFactory.forRules(new Dummy()).nodeCountRestrainedEngine(1000_000);

        play(dummy);
    }

    private static void play(Engine engine) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (!engine.getEvaluation().isDecisive()) {
            System.out.println(engine.getCurrentBoard());
//            String read = scanner.nextLine();
//            engine.move(Connect4Move.getMove(Integer.valueOf(read)));
            long nanoTime = System.nanoTime();
            engine.start();
//            if(engine.getEvaluation().isDecisive()){
//                break;
//            }
            System.out.println((System.nanoTime() - nanoTime) / 10000 / 100. + "ms");
            Move bestMove = engine.getBestMove();
            System.out.println(bestMove + " "+ engine.getEvaluation());
            engine.move(bestMove);
        }
        System.out.println(engine.getEvaluation().getWinner());
        List<Move> movesSequence = engine.getBestMovesSequence();
        movesSequence.forEach(move->{
            System.out.println(engine.getCurrentBoard());
            engine.move(move);
        });
        System.out.println(engine.getCurrentBoard());
    }
}
