package Unixado.Reversi.Player;

import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Game.Game;

public abstract class AIPlayer extends Player {

    protected final Game game;
    public AIPlayer ( Color color, Game game ) {
        super ( color );
        this.game = game;
    }

    @Override
    public void toggleTurn () {
        super.toggleTurn ();
        if(super.isTurn ())
            takeAction();
    }

    public abstract void takeAction();
}
