package Unixado.Reversi.Player;

import Unixado.Reversi.Game.*;
import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.Random;

public class GreedyPlayer extends ComputerPlayer {

    public GreedyPlayer ( Color color, Game game ) {
        super ( color, game );
    }

    @Override
    protected void takeAction () {
        boolean[][] validMoves = this.game.findAllValidMoves ( this );

        Color[][] board = this.game.getBoard ();
        ArrayList<Move> moveArray = new ArrayList<> ();

        for (int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++) {
                if (validMoves[row][col])
                    moveArray.add ( new Move ( this, new DiskLocation ( row, col ) ) );
            }

        Move greedyMove = null;
        int maxGreediness = 0;
        for ( Move move : moveArray ) {

            int greediness = Board.getNumberOfPlayerDisks
                    ( this, Board.getBoardAfterMove ( move, this.game.getBoard () ) );

            if ( greediness > maxGreediness) {
                maxGreediness = greediness;
                greedyMove = move;
            }
        }

        if( greedyMove != null )
            this.game.addMove ( greedyMove );
    }

    @Override
    public void takeActionWithDelay () {

        int millisInFuture = new Random ().nextInt ( 1000 ) + 1000;

        new CountDownTimer ( millisInFuture, millisInFuture ) {


            public void onTick ( long millisUntilFinished ) {
            }

            public void onFinish () {
                takeAction ();
            }
        }.start ();
    }
}
