package Unixado.Reversi.Player;

public abstract class Player {

    private int score;
    private boolean winner;
    private boolean turn;
    private Color color;

    public Player (Color color){

        // game starts with 2 scores for each player
        this.score = 2;

        this.color = color;
        // black player always starts first
        if(this.color == Color.BLACK)
            this.turn = true;
    }
}
