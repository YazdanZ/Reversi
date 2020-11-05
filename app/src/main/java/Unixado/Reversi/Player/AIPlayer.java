package Unixado.Reversi.Player;

import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Game.Game;

public abstract class AIPlayer extends ComputerPlayer {
    public AIPlayer ( Color color, Game game ) {
        super ( color, game );
    }
}
