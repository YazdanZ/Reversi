package Unixado.Reversi.Player;

import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Game.Game;

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
    public abstract void takeActionWithDelay ();
}
