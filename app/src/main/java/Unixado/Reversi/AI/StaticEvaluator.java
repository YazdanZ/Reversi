package Unixado.Reversi.AI;

import Unixado.Reversi.Game.Board;
import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Player.AIPlayer;

public class StaticEvaluator implements Evaluator {

    private static final int[][] BOARD_POSITION_EVALUATION = {
            {30, -25, 10, 5, 5, 10, -25, 30,},
            {-25, -30, 1, 1, 1, 1, -25, -25,},
            {10, 1, 5, 2, 2, 5, 1, 10,},
            {5, 1, 2, 1, 1, 2, 1, 5,},
            {5, 1, 2, 1, 1, 2, 1, 5,},
            {10, 1, 5, 2, 2, 5, 1, 10,},
            {-25, -25, 1, 1, 1, 1, -30, -25,},
            {30, -25, 10, 5, 5, 10, -25, 30,},};


    AIPlayer maximizingPlayer;


    public StaticEvaluator ( AIPlayer maximizingPlayer ) {
        this.maximizingPlayer = maximizingPlayer;
    }

    // Mobility (number of legal moves - number of opponent's legal moves)
    // always grab corners
    // always chose the move that gives the opponent zero legal moves
    // Corners
    // spots around corners have low evaluation
    // X tiles are very bad
    // C squares should be avoided
    // number of stable disks
    // number of meta stable disks
    //potential mobility. Potential mobility can be estimated by simply counting all the opponent's
    // pieces that are adjacent to an empty field.


    // Parity
    //This component of the utility function captures the difference in coins between the max player and the min player.

    // It attempts to capture the relative difference between the number of possible moves for the max and the min
    //players, with the intent of restricting the opponent’s mobility and increasing one’s own mobility.
    // This value is calculated as follows:
    // if ( Max Player Moves + Min Player Moves != 0)


//    if ( Max Player Moves + Min Player Moves != 0)
//    Mobility Heuristic Value =
//            100 * (Max Player Moves - Min Player Moves) / (Max Player Moves + Min Player Moves)
//            else
//    Mobility Heuristic Value = 0
//
    // corners captured:
    // if ( Max Player Corners + Min Player Corners != 0)
    //	Corner Heuristic Value =
    //		100 * (Max Player Corners - Min Player Corners) / (Max Player Corners + Min Player Corners)
    //else
    //	Corner Heuristic Value = 0


    // Stability
    // if ( Max Player Stability Value + Min Player Stability Value != 0)
    //	Stability  Heuristic Value =
    //		100 * (Max Player Stability Value - Min Player Stability Value) / (Max Player Stability Value + Min Player Stability Value)
    //else
    //	Stability Heuristic Value = 0

    @Override
    public int evaluate ( Color[][] board ) {
        int evaluation = 0;
        evaluation += evaluateParity ( board );
//        evaluation += evaluateMobility ( board );
        evaluation += evaluatePositions ( board );
        return evaluation;
    }

    /**
     * difference in coins between the max player and the min player.
     */
    private int evaluateParity ( Color[][] board ) {
        int playerNumDisks = Board.getNumberOfPlayerDisks ( this.maximizingPlayer, board );
        int opponentNumDisks = Board.getNumberOfPlayerDisks ( this.maximizingPlayer.getOpponent (), board );

        return 1000 * (playerNumDisks - opponentNumDisks) / (playerNumDisks + opponentNumDisks);
    }

    private int evaluateMobility ( Color[][] board ) {
        Board boardObj = new Board ( board );
        int playerValidMoves = boardObj.findAllValidMoves ( this.maximizingPlayer ).size ();
        int opponentValidMoves = boardObj.findAllValidMoves ( this.maximizingPlayer.getOpponent () ).size ();

        return 10 * (playerValidMoves - opponentValidMoves) / (playerValidMoves + opponentValidMoves);
    }

    private int evaluatePositions ( Color[][] board ) {
        int evaluation = 0;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == this.maximizingPlayer.getColor ())
                    evaluation += BOARD_POSITION_EVALUATION[i][j];
                else if (board[i][j] == this.maximizingPlayer.getOpponent ().getColor ())
                    evaluation -= BOARD_POSITION_EVALUATION[i][j];
            }

        return 100 * evaluation;
    }


    private enum gameStatus {
        STARTGAME,
        MIDGAME,
        ENDGAME
    }
}
