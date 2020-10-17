package Unixado.Reversi.Game;

import Unixado.Reversi.Player.Player;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;
    public Player turn;
    //private Board board;

    public Game(Player player1, Player player2, Board board){
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        this.turn = player1;
    }

    public Player getPlayerTurn(){
        if(player1.isTurn ())
            return player1;
        else return player2;
    }

    public void alternateTurn(){
//        player1.alternateTurn ();
//        player2.alternateTurn ();
        if(turn == player1)
            turn = player2;
        if(turn == player2)
            turn = player1;
    }

}
