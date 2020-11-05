package Unixado.Reversi.Player;

import Unixado.Reversi.Game.Color;

public abstract class Player {

    private final Color color;
    private int score;
    private boolean turn;

    public Player ( Color color ) {

        // game starts with 2 scores for each player
        this.score = 2;
        this.color = color;

        //  Black player starts first according to othello rules
        if (this.color == Color.BLACK)
            this.turn = true;
    }

    /**
     * get the number of disks the player has on the board
     *
     * @return score
     */
    public final int getScore () {
        return this.score;
    }

    /**
     * set player's score
     *
     * @param score number of player disks on the board
     */
    public final void setScore ( int score ) {
        this.score = score;
    }

    /**
     * Toggle the turn. If it was the player's turn, after call to this
     * method. It won't be the player's turn anymore and vice versa.
     */
    public void toggleTurn () {
        this.turn = !this.turn;
    }

    /**
     * @return if it's the player's turn to play
     */
    public final boolean isTurn () {
        return this.turn;
    }

    /**
     * @return player's color (either white or black)
     */
    public final Color getColor () {
        return this.color;
    }
}
