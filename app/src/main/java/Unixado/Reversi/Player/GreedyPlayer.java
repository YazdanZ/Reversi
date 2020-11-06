package Unixado.Reversi.Player;

import Unixado.Reversi.Game.Board;
import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Game.Game;
import Unixado.Reversi.Game.Move;

import java.util.ArrayList;

public class GreedyPlayer extends AIPlayer {

    public GreedyPlayer ( Color color, Game game ) {
        super ( color, game );
    }

    @Override
    public Move evaluate () {
        ArrayList<Move> validMoves = this.game.findAllValidMoves ( this );

        Move greedyMove = null;
        int greediness = 0;
        for (Move move : validMoves) {

            int moveGreediness = Board.getNumberOfPlayerDisks
                    ( this, Board.getBoardAfterMove ( move, this.game.getBoard () ) );

            if (moveGreediness > greediness) {
                greediness = moveGreediness;
                greedyMove = move;
            }
        }
        return greedyMove;
    }

    @Override
    public void takeAction () {
        super.takeAction ( evaluate () );
    }

}
