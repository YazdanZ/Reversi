package Unixado.Reversi.Game;

import Unixado.Reversi.UserInterface.GameActivity;

public class Board {

    private Color[][] board;
    private final GameActivity gameActivity;

    public Board(GameActivity gameActivity){
        this.gameActivity = gameActivity;
        initializeBoard ();
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

    /**
     * Assign Color.NONE to all squares on the board
     */
    private void createEmptyBoard(){
        this.board = new Color[8][8];

        for (int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++)
                this.board[row][col] = Color.NONE;
    }

    /**
     * Set the starting position of black and white pieces
     *
     * Starting position of the game:
     * - - - - - - - -
     * - - - - - - - -
     * - - - W B - - -
     * - - - B W - - -
     * - - - - - - - -
     * - - - - - - - -
     * - - - - - - - -
     */
    private void setInitialBoardState(){
        setBoardPiece ( new PieceLocation ( 3, 3 ), Color.BLACK );
        setBoardPiece ( new PieceLocation ( 4, 4 ), Color.BLACK );
        setBoardPiece ( new PieceLocation ( 4, 3 ), Color.WHITE );
        setBoardPiece ( new PieceLocation ( 3, 4 ), Color.WHITE );
    }

    private void initializeBoard(){
        createEmptyBoard ();
        setInitialBoardState ();
    }

}
