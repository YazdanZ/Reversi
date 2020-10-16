package Unixado.Reversi.Game;

import Unixado.Reversi.Player.Player;

public class Game {
    private Player player1;
    private Player player2;
    private Player playerTurn;

    private Board board;

    public Game(Player player1, Player player2, Board board){
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
    }

}
