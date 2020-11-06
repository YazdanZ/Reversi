package Unixado.Reversi.Game;

import Unixado.Reversi.Player.Player;

import java.util.Objects;

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

    @Override
    public boolean equals ( Object o ) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move move = (Move) o;
        return getPlayer ().equals ( move.getPlayer () ) &&
                getLocation ().equals ( move.getLocation () );
    }

    @Override
    public int hashCode () {
        return Objects.hash ( getPlayer (), getLocation () );
    }
}
