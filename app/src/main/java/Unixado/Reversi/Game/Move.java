package Unixado.Reversi.Game;

import Unixado.Reversi.Player.Player;

public final class Move {

    private final Player player;
    private final DiskLocation location;

    /**
     * A move consists of a player who moves and
     * a location where the player puts a disk
     *
     * @param player   the player who does the move
     * @param location the location where the player disk is placed
     */
    public Move ( Player player, DiskLocation location ) {
        this.player = player;
        this.location = location;
    }

    public DiskLocation getLocation () {
        return this.location;
    }

    public Player getPlayer () {
        return this.player;
    }

}
