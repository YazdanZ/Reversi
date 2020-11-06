package Unixado.Reversi.Player;

import Unixado.Reversi.Game.Board;
import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Game.Game;
import Unixado.Reversi.Game.Move;
import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.Random;

public class GreedyPlayer extends ComputerPlayer {

    public GreedyPlayer ( Color color, Game game ) {
        super ( color, game );
    }

    @Override
    protected void takeAction () {
        ArrayList<Move> validMoves = this.game.findAllValidMoves ( this );

        Move greedyMove = null;
        int greediness = 0;
        for (Move move : validMoves) {

            int moveGreediness = Board.getNumberOfPlayerDisks
                    ( this, Board.getBoardAfterMove ( move, this.game.getBoard () ) );

            if (moveGreediness > greediness) {
                greediness = moveGreediness;
                greedyMove = move;
            }
        }

        if (greedyMove != null) {
            this.game.addMove ( greedyMove );
        }
    }

    @Override
    public void takeActionWithDelay () {

        int millisInFuture = new Random ().nextInt ( 1000 ) + 1000;

        new CountDownTimer ( millisInFuture, millisInFuture ) {


            public void onTick ( long millisUntilFinished ) {
            }

            public void onFinish () {
                takeAction ();
            }
        }.start ();
    }
}
