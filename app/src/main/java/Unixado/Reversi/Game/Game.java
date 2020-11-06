package Unixado.Reversi.Game;

import Unixado.Reversi.Player.ComputerPlayer;
import Unixado.Reversi.Player.GreedyPlayer;
import Unixado.Reversi.Player.HumanPlayer;
import Unixado.Reversi.Player.Player;
import Unixado.Reversi.UserInterface.GameActivity;

public class Game {

    private final Player player1;
    private final Player player2;
    private final Board board;
    private final GameActivity gameActivity;

    public Game ( GameActivity gameActivity ) {

        this.gameActivity = gameActivity;

        // Create and set board with initial states according to othello version of reversi
        this.board = new Board ( this.gameActivity );


        /* For now, let's just hardcode 2 players
         We will change this once the "Settings" is Implemented
         Give the user the option to choose their own colors,
         possibly the possibility to choose which color goes first
         And the option to choose HumanVSHuman to AIvsHuman or AI vs AI*/

        this.player1 = new GreedyPlayer ( Color.BLACK, this );
        this.player2 = new HumanPlayer ( Color.WHITE );

        // show the scoreboard for the first time
        updateScoreBoard ();


/*
         If the player is an AI and has the first move in the game, make the player take action
         this has to be done only once as the AI player will automatically move when the isTurn
         field is set to true through Player.toggleTurn()
*/
        if (player1.isTurn () && player1 instanceof ComputerPlayer)
            ((ComputerPlayer) player1).takeActionWithDelay ();
        if (player2.isTurn () && player2 instanceof ComputerPlayer)
            ((ComputerPlayer) player2).takeActionWithDelay ();
    }

    public void addMove ( Move move ) {

        if (move.getPlayer () != getCurrentPlayer ())
            return;

        boolean[][] validMoves = findAllValidMoves ( move.getPlayer () );
        if (validMoves[move.getLocation ().getRow ()][move.getLocation ().getColumn ()]) {

            this.board.setBoardDisk ( move.getLocation (), move.getPlayer ().getColor () );
            this.board.flipOpponentDisks ( move );
            this.board.printBoard ();
            toggleTurn ();
            updateScores ();
        }

        // toggle turn if the current player has no valid moves
        // show that the player has no valid moves on the UI
        if (this.board.playerHasNoValidMoves ( getCurrentPlayer () )) {
            toggleTurn ();
            updateScores ();
        }

        // Show that the second player has no valid moves on the UI
        // if none of the players have a valid move then the game is over
        if (this.board.playerHasNoValidMoves ( getCurrentPlayer () ))
            gameOver ();

    }

    /**
     * @return the player who has the turn
     */
    public Player getCurrentPlayer () {
        if (this.player1.isTurn ())
            return player1;
        return player2;
    }

    /**
     * switch turns
     * if it was player1's turn, it will be player2's turn and vice versa
     */
    private void toggleTurn () {
        player1.toggleTurn ();
        player2.toggleTurn ();
    }

    /**
     * Defined what happens when the game is over
     * show the winner on the screen by updating the UI
     */
    private void gameOver () {
        if (this.player1.getScore () > this.player2.getScore ())
            this.gameActivity.showGameResults ( player1 );
        if (this.player2.getScore () > this.player1.getScore ())
            this.gameActivity.showGameResults ( player2 );
        else
            this.gameActivity.showGameResults ();
    }

    /**
     * Update player scores (number of disks on the board)
     */
    private void updateScores () {
        player1.setScore ( this.board.getNumberOfPlayerDisks ( player1 ) );
        player2.setScore ( this.board.getNumberOfPlayerDisks ( player2 ) );

        // update the IU from here
        updateScoreBoard ();
    }

    /**
     * Call the updateScoreBoard to update the ScoreBoardUI
     */
    private void updateScoreBoard () {
        int blackPlayerScore, whitePlayerScore;

        if (player1.getColor () == Color.BLACK) {
            blackPlayerScore = player1.getScore ();
            whitePlayerScore = player2.getScore ();
        } else {
            whitePlayerScore = player1.getScore ();
            blackPlayerScore = player2.getScore ();
        }

        this.gameActivity.updateScoreBoard ( whitePlayerScore, blackPlayerScore, getCurrentPlayer ().getColor () );
    }

    /**
     * Wrapper to find moves from the UI.
     * I didn't want to give Player access to the Board so
     * I created this wrapper method.
     *
     * @param player player to find valid moves
     * @return a boolean 2D array containing valid move tiles as true
     */
    public boolean[][] findAllValidMoves ( Player player ) {
        return this.board.findAllValidMoves ( player );
    }

    public Color[][] getBoard () {
        return this.board.getBoard ();
    }
}
