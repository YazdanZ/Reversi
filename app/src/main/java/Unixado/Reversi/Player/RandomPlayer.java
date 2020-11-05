package Unixado.Reversi.Player;

import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Game.DiskLocation;
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
        boolean[][] validMoves = this.game.findAllValidMoves ( this );
        ArrayList<DiskLocation> validLocations = new ArrayList<> ();
        for (int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++) {
                if (validMoves[row][col])
                    validLocations.add ( new DiskLocation ( row, col ) );
            }

        if (validLocations.size () == 0)
            return;

        DiskLocation randomLocation = validLocations.get ( new Random ().nextInt ( validLocations.size () ) );
        this.game.addMove ( new Move ( this, randomLocation ) );
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
