package com.pgrela.games.engine;

import com.pgrela.games.engine.api.Engine;
import com.pgrela.games.engine.api.Move;
import com.pgrela.games.engine.connect4.Connect4;
import com.pgrela.games.engine.connect4.Connect4Compact;
import com.pgrela.games.engine.connect4.Connect4Move;
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
        Engine connect4 = engineFactory.forRules(new Connect4()).depthRestrainedEngine(5);
        Engine connect4Compact = engineFactory.forRules(new Connect4Compact()).depthRestrainedEngine(8);
        Engine dummy = engineFactory.forRules(new Dummy()).nodeCountRestrainedEngine(1000_000);

        play(connect4Compact);
    }

    private static void play(Engine engine) throws IOException {
        //System.in.read();
        while (!engine.getEvaluation().isDecisive()) {
            System.out.println(engine.getCurrentBoard());
            long nanoTime = System.nanoTime();
            engine.start();
            System.out.println((System.nanoTime() - nanoTime) / 10000 / 100. + "ms");
            Move bestMove = engine.getBestMove();
            System.out.println(bestMove + " "+ engine.getEvaluation());
            engine.move(bestMove);
        }
        finishGame(engine);
    }
    private static void playInteractive(Engine engine) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (!engine.getEvaluation().isDecisive()) {
            System.out.println(engine.getCurrentBoard());
            String read = scanner.nextLine();
            engine.move(Connect4Move.getMove(Integer.valueOf(read)));
            long nanoTime = System.nanoTime();
            engine.start();
            System.out.println((System.nanoTime() - nanoTime) / 10000 / 100. + "ms");
            if(engine.getEvaluation().isDecisive()){
                break;
            }
            Move bestMove = engine.getBestMove();
            System.out.println(bestMove + " "+ engine.getEvaluation());
            engine.move(bestMove);
        }
        finishGame(engine);
    }

    private static void finishGame(Engine engine) {
        System.out.println(engine.getEvaluation().getWinner());
        List<Move> movesSequence = engine.getBestMovesSequence();
        movesSequence.forEach(move->{
            System.out.println(engine.getCurrentBoard());
            engine.move(move);
        });
        System.out.println(engine.getCurrentBoard());
    }
}
