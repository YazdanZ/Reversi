package Unixado.Reversi.Player;

import Unixado.Reversi.Game.Color;

public abstract class Player {

    private int score;
    private final Color color;
    private boolean turn;

    public Player (Color color){

        // game starts with 2 scores for each player
        this.score = 2;
        this.color = color;

        //  Black player starts first according to othello rules
        if(this.color == Color.BLACK)
            this.turn = true;
    }

    public int getScore () {
        return score;
    }

    /**
     * think about setScore and addScore
     * @param score
     */
    public void setScore ( int score ) {
        this.score = score;
    }

    public void alternateTurn(){
        this.turn = !this.turn;
    }

    public boolean isTurn(){
        return this.turn;
    }

    public Color getColor(){
        return this.color;
    }
}
