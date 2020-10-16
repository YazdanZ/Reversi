package Unixado.Reversi.Player;

import Unixado.Reversi.Game.Color;

public abstract class Player {

    private int score;
    private Color color;

    public Player (Color color){

        // game starts with 2 scores for each player
        this.score = 2;

        this.color = color;
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

}
