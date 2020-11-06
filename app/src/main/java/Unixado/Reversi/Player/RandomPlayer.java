package Unixado.Reversi.Player;

import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Game.Game;
import Unixado.Reversi.Game.Move;
import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends ComputerPlayer {

    public RandomPlayer ( Color color, Game game ) {
        super ( color, game );
    }

    /**
     * the random player will simply choose a random spot from
     * the available valid moves to play. This random player has been
     * implemented to create the infrastructure for adding other
     * computer players later and can be used for the EASY option in 1 V Computer games
     */
    protected void takeAction () {
        ArrayList<Move> validMoves = this.game.findAllValidMoves ( this );

        if (validMoves.size () == 0)
            return;

        Move randomMove = validMoves.get ( new Random ().nextInt ( validMoves.size () ) );
        this.game.addMove ( randomMove );
    }

    /**
     * Delay the computer move to let the player update his vision
     * of the board in his mind
     */
    public void takeActionWithDelay () {
        // randomize the timer taken for the computer to seem more natural
        // random time from 1 to 2 seconds so user thinks that the computer is thinking
        // and doesn't suspect that we are using a timer like in here
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
