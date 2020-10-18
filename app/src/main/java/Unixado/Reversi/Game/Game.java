package Unixado.Reversi.Game;

import Unixado.Reversi.Player.HumanPlayer;
import Unixado.Reversi.Player.Player;
import Unixado.Reversi.UserInterface.GameActivity;
import android.util.Log;

import java.util.Stack;

public class Game {
    private final GameActivity gameActivity;
    private final Player player1;
    private final Player player2;
    private Board board;
    private Stack<Move> moves;


    public Game( GameActivity gameActivity ){
        this.gameActivity = gameActivity;

        // For now, let's just hardcode 2 human players
        // We will change this once the "Settings" is Implemented
        // Give the user the option to choose their own colors,
        // possibly the possibility to choose which color goes first
        // And the option to choose HumanVSHuman to AIvsHuman or AI vs AI

        this.player1 = new HumanPlayer ( Color.WHITE );
        this.player2 = new HumanPlayer ( Color.BLACK );

        this.moves = new Stack<> ();
        startGame ();
    }

    private void startGame(){

        // Create and set board with initial states according to othello version of reversi
        this.board = new Board ( this.gameActivity );
    }

    public void addMove(Move move){
        // Verify the move

        // Verify if there is already a piece at the move's location
//        if(this.board.getBoard ()[move.getLocation ().getX ()][move.getLocation ().getY ()] != Color.NONE)
//            return;

        boolean[][] validMoves = this.board.findAllValidMoves ( move.getPlayer () );
        if(validMoves[move.getLocation ().getX ()][move.getLocation ().getY ()]){
            this.moves.push ( move );


            // find opponent pieces that will be flipped

            // add the result to the board
            this.board.setBoardPiece ( move.getLocation (), move.getPlayer ().getColor () );
            this.board.flipOpponentPieces ( move );
            toggleTurn ();
            isGameOver ();

        }

        // Verify if player's turn
        // If above conditions are met, add the move to the queue
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
        // Read rules is more detail

        // For now, I think that the game is over either when the board is filled or when
        // none of the 2 players have a remaining valid move

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++){
                if(this.board.getBoard ()[i][j] == Color.NONE)
                    return false;
            }
        Log.i ("Game", "Game is over");
        return true;
    }
}
