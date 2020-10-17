package Unixado.Reversi.Game;

import Unixado.Reversi.UserInterface.GameActivity;

public class Board {

    private Color[][] board;
    private final GameActivity gameActivity;

    public Board(GameActivity gameActivity){
        this.gameActivity = gameActivity;
    }

    public void createEmptyBoard(){
        this.board = new Color[8][8];

        for (int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++)
                this.board[row][col] = Color.NONE;
    }

    /**
     *
     * @return board 2D array
     */
    public Color[][] getBoard(){
        return this.board.clone ();
    }

    /**
     * Update board and update the displayed piece color
     * @param location location on the board
     * @param color piece color to assign to the given location
     */
    public void setBoardPiece(PieceLocation location, Color color){
        this.board[location.getX ()][location.getY ()] = color;
        this.gameActivity.setBoardResources ( location, color );
    }

}
