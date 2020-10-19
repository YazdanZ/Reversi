package Unixado.Reversi.Player;

import Unixado.Reversi.Game.Color;

public abstract class Player {

    private final Color color;
    private int score;
    private boolean turn;

    public Player (Color color){

        // game starts with 2 scores for each player
        this.score = 2;
        this.color = color;

        //  Black player starts first according to othello rules
        if(this.color == Color.BLACK)
            this.turn = true;
    }

    /**
     * get the number of disks the player has on the board
     * @return score
     */
    public final int getScore () {
        return this.score;
    }

    /**
     * Add/Subtract player's score
     * @param score number of player disks added to the board
     */
    public final void addScore ( int score ) {
        this.score += score;
    }

    public final void toggleTurn (){
        this.turn = !this.turn;
    }

    public final boolean isTurn(){
        return this.turn;
    }

    public final Color getColor(){
        return this.color;
    }
}
