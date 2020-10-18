package Unixado.Reversi.Game;

import Unixado.Reversi.Player.HumanPlayer;
import Unixado.Reversi.Player.Player;
import Unixado.Reversi.UserInterface.GameActivity;

import java.util.Stack;

public class Game {
    private Board board;
    private GameActivity gameActivity;
    private Player player1;
    private Player player2;
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
        // Verify if player's turn
        // If above conditions are met, add the move to the queue
        this.moves.push ( move );

        // find opponent pieces that will be flipped

        // add the result to the board
        this.board.setBoardPiece ( move.getLocation (), move.getPlayer ().getColor () );
        switchTurn ();
    }

    public Player getCurrentPlayer(){
        if(this.player1.isTurn ())
            return player1;
        return player2;
    }

    private void switchTurn(){
        player1.alternateTurn ();
        player2.alternateTurn ();
    }

}
