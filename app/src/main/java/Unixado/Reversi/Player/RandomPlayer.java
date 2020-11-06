package Unixado.Reversi.Player;

import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Game.Game;
import Unixado.Reversi.Game.Move;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends ComputerPlayer {

    public RandomPlayer ( Color color, Game game ) {
        super ( color, game );
    }

    /**
     * the random player will simply choose a random spot from
     * the available valid moves to play. This random player has been
     * implemented to create the infrastructure for adding other
     * computer players later and can be used for the EASY option in 1 V Computer games
     */
    protected void takeAction () {
        ArrayList<Move> validMoves = this.game.findAllValidMoves ( this );

        if (validMoves.size () == 0)
            return;

        Move randomMove = validMoves.get ( new Random ().nextInt ( validMoves.size () ) );
        super.takeAction ( randomMove );
    }
}
