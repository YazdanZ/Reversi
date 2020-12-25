package Unixado.Reversi.Player;

import Unixado.Reversi.AI.KillerMoveHeuristic;
import Unixado.Reversi.AI.Minimax;
import Unixado.Reversi.AI.StaticEvaluator;
import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Game.Game;
import Unixado.Reversi.Game.Move;

public class StaticMinimaxPlayer extends AIPlayer {
    public StaticMinimaxPlayer ( Color color, Game game, Player opponent ) {
        super ( color, game, opponent );
    }

    @Override
    public Move evaluate () {
        Move killerMove = KillerMoveHeuristic.getKillerMove ( this );
        if (killerMove != null)
            return killerMove;
        return Minimax.findBestMove ( this, 6, new StaticEvaluator ( this ) );
    }

    @Override
    protected void takeAction () {
        super.takeAction ( evaluate () );
    }
}
