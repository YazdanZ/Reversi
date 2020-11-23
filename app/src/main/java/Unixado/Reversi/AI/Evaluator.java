package Unixado.Reversi.AI;

import Unixado.Reversi.Game.Color;

public interface Evaluator {
    int evaluate ( Color[][] board );
}
