package Unixado.Reversi.Player;

import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Game.Game;
import Unixado.Reversi.Game.Move;
import android.os.CountDownTimer;

import java.util.Random;

public abstract class ComputerPlayer extends Player {

    protected final Game game;

    public ComputerPlayer ( Color color, Game game ) {
        super ( color );
        this.game = game;
    }

    @Override
    public void toggleTurn () {
        super.toggleTurn ();
        if (super.isTurn ())
            takeActionWithDelay ();
    }

    protected void takeAction ( Move move ) {
        if (move != null)
            this.game.addMove ( move );
    }

    /**
     * the takeAction() method will be different for any Computer Player
     * this method should always be called from within takeActionWithDelay()
     * for a more natural and enjoyable game for the user
     */
    protected abstract void takeAction ();

    /**
     * this method should be implemented to call the takeAction()
     * method with a delay for a more enjoyable user experience
     */
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
