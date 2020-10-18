package Unixado.Reversi.Game;

import Unixado.Reversi.Player.Player;

public final class Move {

    private final Player player;
    private final PieceLocation location;

    /**
     * A move consists of a player who moves and
     * a location where the player puts a piece
     * @param player the player who does the move
     * @param location the location where the player piece is placed
     */
    public Move ( Player player, PieceLocation location ){
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
