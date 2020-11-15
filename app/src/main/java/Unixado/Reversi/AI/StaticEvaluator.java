package Unixado.Reversi.AI;

import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Player.Player;

public class StaticEvaluator implements Evaluator {
    @Override
    public int evaluate ( Color[][] board, Player player ) {
        int evaluation = 0;

        for (int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++) {
                if(board[row][col] == player.getColor ())
                    evaluation++;
            }
        return evaluation;
    }
}
