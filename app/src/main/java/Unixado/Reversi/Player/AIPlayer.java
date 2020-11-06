package Unixado.Reversi.Player;

import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Game.Game;
import Unixado.Reversi.Game.Move;

public abstract class AIPlayer extends ComputerPlayer {
    public AIPlayer ( Color color, Game game ) {
        super ( color, game );
    }

    public abstract Move evaluate ();
}
