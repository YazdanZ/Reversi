package Unixado.Reversi.Player;

import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Game.DiskLocation;
import Unixado.Reversi.Game.Game;
import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.Random;

public class GreedyPlayer extends ComputerPlayer {

    public GreedyPlayer ( Color color, Game game ) {
        super ( color, game );
    }

    @Override
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

        // Evaluate the moves to find the one that results in the maximum number of opponent disks to be flipped


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
