package Unixado.Reversi.Player;

import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Game.DiskLocation;
import Unixado.Reversi.Game.Game;
import Unixado.Reversi.Game.Move;
import android.os.CountDownTimer;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends AIPlayer{

    public RandomPlayer ( Color color, Game game ) {
        super ( color, game );
    }

    protected void takeAction(){
        boolean[][] validMoves = this.game.findAllValidMoves ( this );
        ArrayList<DiskLocation> validLocations = new ArrayList<> ();
        for(int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++){
                if(validMoves[row][col])
                    validLocations.add ( new DiskLocation ( row, col ) );
            }

        if(validLocations.size () == 0)
            return;

        DiskLocation randomLocation = validLocations.get ( new Random ().nextInt (validLocations.size ()));
        this.game.addMove ( new Move ( this, randomLocation ) );
    }

    public void takeActionWithDelay(){
        new CountDownTimer (2000, 2000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                takeAction ();
            }
        }.start();

    }
}
