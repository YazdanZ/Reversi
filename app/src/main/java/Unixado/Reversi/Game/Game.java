package Unixado.Reversi.Game;

import Unixado.Reversi.Player.AIPlayer;
import Unixado.Reversi.Player.HumanPlayer;
import Unixado.Reversi.Player.Player;
import Unixado.Reversi.Player.RandomPlayer;
import Unixado.Reversi.UserInterface.GameActivity;


public class Game {

    private final Player player1;
    private final Player player2;
    private final Board board;
    private final GameActivity gameActivity;

    public Game( GameActivity gameActivity ){

        this.gameActivity = gameActivity;

        // Create and set board with initial states according to othello version of reversi
        this.board = new Board ( this.gameActivity );


        /*         For now, let's just hardcode 2 human players
         We will change this once the "Settings" is Implemented
         Give the user the option to choose their own colors,
         possibly the possibility to choose which color goes first
         And the option to choose HumanVSHuman to AIvsHuman or AI vs AI*/

        this.player1 = new RandomPlayer ( Color.WHITE , this);
        this.player2 = new HumanPlayer ( Color.BLACK );

        this.gameActivity.updateScoreBoard ( player1, player2 );
/*

         If the player is an AI and has the first move in the game, make the player take action
         this has to be done only once as the AI player will automatically move when the isTurn
         field is set to true through Player.toggleTurn()
*/
        if(player1.isTurn () && player1 instanceof AIPlayer)
            ((AIPlayer) player1).takeActionWithDelay ();
        if(player2.isTurn () && player2 instanceof AIPlayer)
            ((AIPlayer) player2).takeActionWithDelay ();
    }

    public void addMove(Move move){

        // Verify the move
        if(move.getPlayer () != getCurrentPlayer ())
            return;

        boolean[][] validMoves = findAllValidMoves ( move.getPlayer () );
        if(validMoves[move.getLocation ().getRow ()][move.getLocation ().getColumn ()]){

            // add newly added disk to the board
            this.board.setBoardDisk ( move.getLocation (), move.getPlayer ().getColor () );

            // flip opponent disks that have been captured by this move
            this.board.flipOpponentDisks ( move );

            // change turns
            toggleTurn ();

            // Update player scores
            updateScores ();
        }

        // toggle turn if the current player has no valid moves
        // show that the player has no valid moves on the UI
        if(this.board.playerHasNoValidMoves ( getCurrentPlayer () )){
            toggleTurn ();
            updateScores ();
        }

        // Show that the second player has no valid moves on the UI
        // if none of the players have a valid move then the game is over
        if(this.board.playerHasNoValidMoves ( getCurrentPlayer () ))
            gameOver ();

    }

    public Player getCurrentPlayer(){
        if(this.player1.isTurn ())
            return player1;
        return player2;
    }

    /**
     * switch turns
     */
    private void toggleTurn (){
        player1.toggleTurn ();
        player2.toggleTurn ();
    }

    private void gameOver(){

    }

    /**
     * Update player scores (number of disks on the board)
     */
    private void updateScores(){
        // reset both player scores
        player1.setScore ( 0 );
        player2.setScore ( 0 );

        Color[][] board = this.board.getBoard ();
        // loop through the board and add a score each time a player's disk is found
        for (int row = 0; row < 8; row++)
            for (int col = 0 ; col < 8; col++){
                if(board[row][col] == player1.getColor ())
                    player1.setScore ( player1.getScore () + 1 );
                if(board[row][col] == player2.getColor ())
                    player2.setScore ( player2.getScore () + 1 );

            }
        // update the IU from here
        this.gameActivity.updateScoreBoard ( this.player1, this.player2 );
    }

    public boolean[][] findAllValidMoves(Player player){
        return this.board.findAllValidMoves ( player );
    }
}
