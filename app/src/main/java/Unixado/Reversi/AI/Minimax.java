package Unixado.Reversi.AI;

import Unixado.Reversi.Game.Board;
import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Game.Game;
import Unixado.Reversi.Game.Move;
import Unixado.Reversi.Player.AIPlayer;

import java.util.ArrayList;

public class Minimax {

    private final static int NEGATIVE_INFINITY = Integer.MIN_VALUE;
    private final static int POSITIVE_INFINITY = Integer.MAX_VALUE;

    public static Move findBestMove ( AIPlayer maximizingPlayer, int depth, Evaluator evaluator ) {
        int maxEvaluation = NEGATIVE_INFINITY;
        Move bestMove = null;
        ArrayList<Move> validMoves = maximizingPlayer.getGame ().findAllValidMoves ( maximizingPlayer );
        Color[][] currentBoard = maximizingPlayer.getGame ().getBoard ();
        for (Move move : validMoves) {
            // get child node
            Color[][] newNode = Board.getBoardAfterMove ( move, currentBoard );
            //evaluate child node
            int score = runMinimax ( newNode, depth - 1,
                    NEGATIVE_INFINITY, POSITIVE_INFINITY, false, evaluator, maximizingPlayer );
            if (score > maxEvaluation) {
                maxEvaluation = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private static int runMinimax ( Color[][] node, int depth, int alpha, int beta,
                                    boolean maximizing, Evaluator evaluator, AIPlayer maximizingPlayer ) {
        Board boardAfterMove = new Board ( node );
        ArrayList<Move> validMoves = boardAfterMove.findAllValidMoves ( maximizingPlayer );

        // our base case, when we're at a terminal node
        if (depth == 0 || Game.isGameOver ( boardAfterMove, maximizingPlayer, maximizingPlayer.getOpponent () ))
            return evaluator.evaluate ( node, maximizingPlayer );

        // skip the move if maximizing player has no valid moves
        if (maximizing && validMoves.size () == 0) {
            runMinimax ( node, depth - 1, alpha, beta, false, evaluator, maximizingPlayer );
        }

        if (maximizing) {
            int maxEvaluation = NEGATIVE_INFINITY;
            for (Move move : validMoves) {
                Color[][] newNode = Board.getBoardAfterMove ( move, node );
                int evaluation = runMinimax ( newNode, depth - 1, alpha, beta,
                        false, evaluator, maximizingPlayer );
                maxEvaluation = Math.max ( maxEvaluation, evaluation );
                alpha = Math.max ( alpha, evaluation );
                if (beta <= alpha)
                    break;
            }
            return maxEvaluation;
        } else {
            ArrayList<Move> opponentValidMoves = boardAfterMove.findAllValidMoves ( maximizingPlayer.getOpponent () );
            int minEvaluation = POSITIVE_INFINITY;
            for (Move move : opponentValidMoves) {
                Color[][] newNode = Board.getBoardAfterMove ( move, node );
                int evaluation = runMinimax ( newNode, depth - 1, alpha, beta,
                        true, evaluator, maximizingPlayer );
                minEvaluation = Math.min ( minEvaluation, evaluation );
                beta = Math.min ( beta, evaluation );
                if (beta <= alpha)
                    break;
            }
            return minEvaluation;
        }
    }
}
