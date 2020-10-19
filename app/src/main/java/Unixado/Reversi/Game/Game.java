package Unixado.Reversi.Game;

import Unixado.Reversi.Player.HumanPlayer;
import Unixado.Reversi.Player.Player;
import Unixado.Reversi.UserInterface.GameActivity;
import android.util.Log;

import java.util.Stack;

public class Game {
    private final Player player1;
    private final Player player2;
    private final Board board;
    private Stack<Move> moves;


    public Game( GameActivity gameActivity ){

        // For now, let's just hardcode 2 human players
        // We will change this once the "Settings" is Implemented
        // Give the user the option to choose their own colors,
        // possibly the possibility to choose which color goes first
        // And the option to choose HumanVSHuman to AIvsHuman or AI vs AI

        this.player1 = new HumanPlayer ( Color.WHITE );
        this.player2 = new HumanPlayer ( Color.BLACK );

        this.moves = new Stack<> ();

        // Create and set board with initial states according to othello version of reversi
        this.board = new Board ( gameActivity );
    }

    public void addMove(Move move){
        // Verify the move

        boolean[][] validMoves = this.board.findAllValidMoves ( move.getPlayer () );
        if(validMoves[move.getLocation ().getRow ()][move.getLocation ().getColumn ()]){

            // move is valid push it to the move stack
            this.moves.push ( move );

            // add newly added disk to the board
            this.board.setBoardDisk ( move.getLocation (), move.getPlayer ().getColor () );

            // flip opponent disks that have been captured by this move
            this.board.flipOpponentDisks ( move );

            // Update player's score

            // change turns
            toggleTurn ();
        }

        // toggle turn if the current player has no valid moves
        // show that the player has no valid moves on the UI
        if(!this.board.playerHasValidMoves ( getCurrentPlayer () ))
            toggleTurn ();

        // Show that the second player has no valid moves on the UI
        // if none of the players have a valid move then the game is over
        if(!this.board.playerHasValidMoves ( getCurrentPlayer () ))
            gameIsOver ();

    }

    public Player getCurrentPlayer(){
        if(this.player1.isTurn ())
            return player1;
        return player2;
    }

    private void toggleTurn (){
        player1.toggleTurn ();
        player2.toggleTurn ();
    }

    private boolean isGameOver(){
        return true;
    }

    private void gameIsOver(){
        Log.i ("Game Over", "The game is over");

        // find the winner and show it on the UI
    }
}
