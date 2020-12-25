package Unixado.Reversi.AI;

import Unixado.Reversi.Game.DiskLocation;
import Unixado.Reversi.Game.Move;
import Unixado.Reversi.Player.AIPlayer;

import java.util.ArrayList;

public class KillerMoveHeuristic {

    public static Move getKillerMove ( AIPlayer player ) {
        Move killerMove = null;
        ArrayList<Move> validMoves = player.getGame ().findAllValidMoves ( player );

        if(validMoves.contains ( new Move ( player, new DiskLocation ( 0, 0 ) ) ))
            killerMove = new Move ( player, new DiskLocation ( 0, 0 ) );
        else if(validMoves.contains ( new Move ( player, new DiskLocation ( 0, 7 ) ) ))
            killerMove = new Move ( player, new DiskLocation ( 0, 7 ) );
        else if(validMoves.contains ( new Move ( player, new DiskLocation ( 7, 0 ) ) ))
            killerMove = new Move ( player, new DiskLocation ( 7, 0 ) );
        else if(validMoves.contains ( new Move ( player, new DiskLocation ( 7, 7 ) ) ))
            killerMove = new Move ( player, new DiskLocation ( 7, 7 ) );

        return killerMove;
    }
}
