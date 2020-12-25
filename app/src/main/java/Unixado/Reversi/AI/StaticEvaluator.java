package Unixado.Reversi.AI;

import Unixado.Reversi.Game.Board;
import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Player.AIPlayer;

public class StaticEvaluator implements Evaluator {
    private static final int[][] BOARD_POSITION_EVALUATION = {
            {200, -100, 100, 50, 50, 100, -100, 200},
            {-100, -200, -50, -50, -50, -50, -200, -100},
            {100, -50, 100, 0, 0, 100, -50, 100},
            {50, -50, 0, 0, 0, 0, -50, 50},
            {50, -50, 0, 0, 0, 0, -50, 50},
            {100, -50, 100, 0, 0, 100, -50, 100},
            {-100, -200, -50, -50, -50, -50, -200, -100},
            {200, -100, 100, 50, 50, 100, -100, 200},};

    AIPlayer maximizingPlayer;


    public StaticEvaluator ( AIPlayer maximizingPlayer ) {
        this.maximizingPlayer = maximizingPlayer;
    }

    @Override
    public int evaluate ( Color[][] board ) {
        int evaluation = 0;
        evaluation += evaluateNumberOfDisks ( board );
        //evaluation += evaluatePositions ( board );
        evaluation += 2 * evaluateMobility ( board );
        evaluation += 100 * evaluateCorners ( board );
        evaluation += evaluateXTiles ( board );
//        evaluation += evaluateCTiles ( board );
        return evaluation;
    }

    /**
     * difference in disks between the max player and the min player.
     */
    private int evaluateNumberOfDisks ( Color[][] board ) {
        int playerNumDisks = Board.getNumberOfPlayerDisks ( this.maximizingPlayer, board );
        int opponentNumDisks = Board.getNumberOfPlayerDisks ( this.maximizingPlayer.getOpponent (), board );

        return 100 * (playerNumDisks - opponentNumDisks) / (playerNumDisks + opponentNumDisks);
    }

    private int evaluateMobility ( Color[][] board ) {
        Board boardObj = new Board ( board );
        int playerValidMoves = boardObj.findAllValidMoves ( this.maximizingPlayer ).size ();
        int opponentValidMoves = boardObj.findAllValidMoves ( this.maximizingPlayer.getOpponent () ).size ();
        int totalMoves = playerValidMoves + opponentValidMoves;
        return 100 * (playerValidMoves - opponentValidMoves) / (totalMoves + 1);
    }

    private int evaluateCorners ( Color[][] board ) {
        int playerCorners = 0;
        int opponentCorners = 0;

        Color[] corners = {board[0][0], board[0][7], board[7][0], board[7][7]};
        for (Color color : corners) {
            if (color == this.maximizingPlayer.getColor ())
                playerCorners++;
            if (color == this.maximizingPlayer.getOpponent ().getColor ())
                opponentCorners++;
        }

        int totalTakenCorners = playerCorners + opponentCorners;
        return 100 * (playerCorners - opponentCorners) / (totalTakenCorners + 1);
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

        return evaluation;
    }

    private int evaluateCTiles ( Color[][] board ) {
        int maximizingPlayerCTiles = 0;
        int opponentCTiles = 0;
        Color maximizingPlayerColor = this.maximizingPlayer.getColor ();
        Color opponentColor = this.maximizingPlayer.getOpponent ().getColor ();
        Color[] corners = {board[0][0], board[0][7], board[7][0], board[7][7]};
        Color[][] ctiles =
                {{board[0][1], board[1][0]},
                {board[0][6], board[1][7]},
                {board[6][0], board[7][1]},
                {board[6][7], board[7][6]}};

        for (int i = 0; i < corners.length; i++){
            if (corners[i] == Color.BLANK && ctiles[i][0] == maximizingPlayerColor)
                maximizingPlayerCTiles++;
            if (corners[i] == Color.BLANK && ctiles[i][1] == maximizingPlayerColor)
                maximizingPlayerCTiles++;
            if (corners[i] == Color.BLANK && ctiles[i][1] == opponentColor)
                opponentCTiles++;
            if (corners[i] == Color.BLANK && ctiles[i][1] == opponentColor)
                opponentCTiles++;
        }

        return 100 * (opponentCTiles - maximizingPlayerCTiles) / (opponentCTiles + maximizingPlayerCTiles + 1);
    }

    /**
     * X tiles are tiles diagonal to corner tiles, they are unfavourable tiles
     * because they can give the opponent easy corner captures
     *
     * @param board board
     * @return evaluation
     */
    private int evaluateXTiles ( Color[][] board ) {
        int maximizingPlayerXTiles = 0;
        int opponentXTiles = 0;
        Color[] corners = {board[0][0], board[0][7], board[7][0], board[7][7]};
        Color[] xTiles = {board[1][1], board[1][6], board[6][1], board[6][6]};
        Color maximizingPlayerColor = this.maximizingPlayer.getColor ();
        Color opponentColor = this.maximizingPlayer.getOpponent ().getColor ();

        for (int i = 0; i < corners.length; i++){
            if (corners[i] == Color.BLANK && xTiles[i] == maximizingPlayerColor) maximizingPlayerXTiles++;
            if (corners[i] == Color.BLANK && xTiles[i] == opponentColor) opponentXTiles++;
        }

        return 150 * (opponentXTiles - maximizingPlayerXTiles) / (opponentXTiles + maximizingPlayerXTiles + 1);
    }
}
