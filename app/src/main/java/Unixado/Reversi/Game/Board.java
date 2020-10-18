package Unixado.Reversi.Game;

import Unixado.Reversi.Player.Player;
import Unixado.Reversi.UserInterface.GameActivity;

public class Board {

    private Color[][] board;
    private final GameActivity gameActivity;
    private boolean[][] validMoves;

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


    public boolean[][] findAllValidMoves( Player player){
        this.validMoves = new boolean[8][8];

        for(int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++){
                if(this.board[row][col] == Color.NONE){

                    // Check all directions to the selected square to determine if it's a valid moves
                    boolean N = isValidMove ( player, 1, 0, row, col );
                    boolean NW = isValidMove ( player, 1, -1, row, col );
                    boolean NE = isValidMove ( player, 1, 1, row, col );
                    boolean S = isValidMove ( player, -1, 0, row, col );
                    boolean SW = isValidMove ( player, -1, -1, row, col );
                    boolean SE = isValidMove ( player, -1, 1, row, col );
                    boolean E = isValidMove ( player, 0, 1, row, col );
                    boolean W = isValidMove ( player, 0, -1, row, col );

                    if( N || NW || NE || S || SW || SE || E || W)
                        this.validMoves[row][col] = true;

                }

            }


        return this.validMoves;
    }

    /**
     *  Check if the position given by the row and column on the board contains the opposite
     *  player's piece and if the line indicated by adding drow to row and dcol to col eventually
     *  ends in the player's color
     * @param player
     * @param drow
     * @param dcol
     * @param row
     * @param col
     * @return
     */
    private boolean isValidMove(Player player, int drow, int dcol, int row, int col ){
        Color oppositeColor;
        if(player.getColor () == Color.BLACK)
            oppositeColor = Color.WHITE;
        else oppositeColor = Color.BLACK;

        if(row + drow < 0 || row + drow > 7)
            return false;
        if(col + dcol < 0 || col + dcol > 7)
            return false;

        if(this.board[row + drow][col + dcol] != oppositeColor)
            return false;

        if(row + drow + drow < 0 || row + drow + drow > 7)
            return false;
        if(col + dcol + dcol < 0 || col + dcol + dcol > 7)
            return false;
        return checkLineMatch ( player, drow, dcol, row + drow + drow, col + dcol + dcol );
    }

    /**
     * Check if there is a piece with the player's color on the line defined from
     * row and col to any line further by adding drow and dcol to row and col
     * @param player
     * @return
     */
    private boolean checkLineMatch(Player player, int drow, int dcol, int row, int col ){
        if(board[row][col] == player.getColor ())
            return true;
        if(row + drow < 0 || row + drow > 7)
            return false;
        if(col + dcol < 0 || col + dcol > 7)
            return false;

        // do a recursive check
        return checkLineMatch ( player, drow, dcol, row + drow, col + dcol);
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
