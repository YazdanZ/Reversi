package Unixado.Reversi.AI;

import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Player.Player;

public interface Evaluator {
    int evaluate ( Color[][] board, Player player );
}
