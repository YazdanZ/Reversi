package Unixado.Reversi.Game;

import Unixado.Reversi.Player.Player;
import java.util.Stack;

public class Move {
    private Player player;
    private PieceLocation location;

    /**
     * A move consists of a player who moves and
     * a location where the player puts a piece
     * @param player
     * @param location
     */
    public Move( Player player, PieceLocation location){
        this.player = player;
        this.location = location;
    }


    public PieceLocation getLocation () {
        return location;
    }

    public Player getPlayer () {
        return player;
    }
}
