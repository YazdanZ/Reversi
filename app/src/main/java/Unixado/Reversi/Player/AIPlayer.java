package Unixado.Reversi.Player;

import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Game.Game;
import Unixado.Reversi.Game.Move;

public abstract class AIPlayer extends ComputerPlayer {
    private final Player opponent;

    public AIPlayer ( Color color, Game game, Player opponent ) {
        super ( color, game );
        this.opponent = opponent;
    }

    public abstract Move evaluate ();

    public Game getGame () {
        return this.game;
    }

    public Player getOpponent () {
        return this.opponent;
    }
}
