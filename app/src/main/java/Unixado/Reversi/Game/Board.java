package Unixado.Reversi.Game;

import Unixado.Reversi.Player.Player;
import Unixado.Reversi.UserInterface.GameActivity;

public class Board {

    private Color[][] board;
    private final GameActivity gameActivity;

    /**
     * Initialize the game board
     * @param gameActivity needed to update the UI board resources
     */
    public Board(GameActivity gameActivity){
        this.gameActivity = gameActivity;
        initializeBoard ();
    }

    /**
     * Update board and update the displayed piece color displayed on the board
     * @param location location on the board
     * @param color piece color to assign to the given location
     */
    public void setBoardPiece(PieceLocation location, Color color){
        this.board[location.getRow ()][location.getColumn ()] = color;
        this.gameActivity.setBoardResources ( location, color );
    }

    /**
     * Find all the valid moves for a player
     *
     * @param player for whom to find all valid moves
     * @return a 2D boolean array of valid moves on the board.
     *         Cells with a true value represent spots on the board
     *         where the player's move will be valid.
     */
    public boolean[][] findAllValidMoves(Player player){
        boolean[][] validMoves = new boolean[8][8];

        for(int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++){
                if(this.board[row][col] == Color.BLANK){

                    Move move = new Move ( player, new PieceLocation ( row, col ) );
                    // Check all directions to the selected square to determine if it's a valid moves
                    boolean n = isValidMove (1, 0, move ); // North
                    boolean nw = isValidMove ( 1, -1, move ); // NorthWest
                    boolean ne = isValidMove ( 1, 1, move ); // NorthEast
                    boolean s = isValidMove ( -1, 0, move ); // South
                    boolean sw = isValidMove ( -1, -1, move ); // SouthWest
                    boolean se = isValidMove ( -1, 1, move ); //SouthEast
                    boolean e = isValidMove ( 0, 1, move ); // East
                    boolean w = isValidMove ( 0, -1, move ); // West

                    // if the move is valid in any direction, the move is valid
                    if( n || nw || ne || s || sw || se || e || w)
                        validMoves[row][col] = true;
                }
            }
        return validMoves;
    }

    /**
     * Check if the position given by row + drow and col + dcol on the board
     * contains the opposite player's piece. If so, check if the line indicated by
     * adding drow to row and dcol to col eventually ends in the player's color; meaning
     * that one or more opponent pieces are surrounded by 2 of the player's pieces.
     *
     * @param drow delta row specifies the y-direction
     * @param dcol delta column specifies the x-direction
     * @param move move containing a the player who moves and the location the player puts the piece
     * @return if the move is valid in the direction specified by drow and dcol
     */
    private boolean isValidMove(int drow, int dcol, Move move ){
        Color oppositeColor;
        int row = move.getLocation ().getRow ();
        int col = move.getLocation ().getColumn ();

        if(move.getPlayer ().getColor () == Color.BLACK)
            oppositeColor = Color.WHITE;
        else oppositeColor = Color.BLACK;


        // if the first next position in drow and dcol is not on the board, then the move in drow and dcol is invalid
        if(isPositionNotOnBoard ( row + drow, col + dcol ))
            return false;

        if(this.board[row + drow][col + dcol] != oppositeColor)
            return false;

        // if the second next position in drow and dcol is not on the board, then the move in drow and dcol is invalid
        // as another of the player's pieces can't surround an opponent piece.
        if(isPositionNotOnBoard ( row + drow + drow, col + dcol + dcol ))
            return false;


        Move nextMove = new Move ( move.getPlayer (),
                new PieceLocation ( row + drow + drow, col + dcol + dcol ));

        // If the above conditions are met, check if there is a player's piece surrounding one or more opponent pieces
        return checkLineMatch (drow, dcol, nextMove );
    }

    /**
     * Check if a position is not on the board.
     * @param row row on the board
     * @param col column on the board
     * @return if the position specifies by row and col are on the board
     */
    private boolean isPositionNotOnBoard ( int row, int col){
        if(row < 0 || row > 7)
            return true;
        return col < 0 || col > 7;
    }

    /**
     * Check if there is a piece with the player's color on the line defined from
     * row and col to any line further by adding drow and dcol to row and col
     * @param drow delta row specifies the y-direction
     * @param dcol delta column specifies the x-direction
     * @param move move containing a the player who moves and the location
     *             on the board that defines the starting point of our line
     * @return whether there is a player's piece at the end of the line
     *         starting from row and col in the direction of drow and dcol
     */
    private boolean checkLineMatch(int drow, int dcol, Move move ){
        Color playerColor = move.getPlayer ().getColor ();
        int row = move.getLocation ().getRow ();
        int col = move.getLocation ().getColumn ();

        if(board[row][col] == playerColor)
            return true;

        if(this.board[row][col] == Color.BLANK)
            return false;

        // if the next position in drow and dcol direction is not on the board,
        // we have reached the end of ur line without finding another of the player's pieces
        if(isPositionNotOnBoard ( row + drow, col + dcol ))
            return false;

        Move nextMove = new Move ( move.getPlayer (),
                new PieceLocation ( row + drow, col + dcol ));

        // do a recursive check
        return checkLineMatch (drow, dcol, nextMove);
    }

    /**
     * Assign Color.BLANK to all squares on the board
     */
    private void createEmptyBoard(){
        this.board = new Color[8][8];

        for (int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++)
                this.board[row][col] = Color.BLANK;
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

    /**
     * Create an empty board and set the initial position of pieces
     */
    private void initializeBoard(){
        createEmptyBoard ();
        setInitialBoardState ();
    }

    /**
     * Check id a flip in any direction is possible,
     * is a flip is possible, flip the required pieces
     * @param move move that causes the opponent pieces to flip
     */
    public void flipOpponentPieces(Move move){
        // Do all possible flips in every direction

        flipLine( 1, 0, move); // North
        flipLine( 1, -1, move); // NorthWest
        flipLine( 1, 1, move); // NorthEast
        flipLine( -1, 0, move); // South
        flipLine( -1, -1, move); // SouthWest
        flipLine( -1, 1, move); // SouthEast
        flipLine( 0, 1, move); // East
        flipLine( 0, -1, move); // West
    }

    /**
     * Flip pieces that need to be flipped on the line starting from the
     * row and column defined by move and in the direction of drow and dcol.
     * @param drow delta row specifies the y-direction
     * @param dcol delta column specifies the x-direction
     * @param move move that causes the opponent pieces to flip
     * @return true if there could exist a piece that would need to be flipped
     */
    private boolean flipLine(int drow, int dcol, Move move) {
        int row = move.getLocation ().getRow ();
        int col = move.getLocation ().getColumn ();
        Color playerColor = move.getPlayer ().getColor ();

        if(isPositionNotOnBoard ( row + drow, col + dcol ))
            return false;

        if (this.board[row + drow][col + dcol] == Color.BLANK)
            return false;

        if (this.board[row + drow][col + dcol] == playerColor)
            return true;

        else {
            Move nextMove = new Move ( move.getPlayer () , new PieceLocation ( row + drow, col + dcol ) );

            // do a recursive check
            if (flipLine (drow, dcol, nextMove )){
                setBoardPiece ( new PieceLocation ( row + drow, col + dcol ), playerColor);
                return true;
            } else return false;
        }
    }
}
