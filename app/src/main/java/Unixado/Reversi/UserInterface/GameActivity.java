package Unixado.Reversi.UserInterface;

import Unixado.Reversi.Game.Game;
import Unixado.Reversi.Game.Move;
import Unixado.Reversi.Game.DiskLocation;
import Unixado.Reversi.Game.Color;
import Unixado.Reversi.Player.Player;
import Unixado.Reversi.R;
import Unixado.circularactivityreveal.CircularRevealAnimation;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;

public class GameActivity extends AppCompatActivity {

    // Root layout of our activity
    private ConstraintLayout rootLayout;
    private CircularRevealAnimation circularRevealAnimation;
    private ImageView[][] boardResources;
    private Game game;
    private ImageView whiteScoreBand, blackScoreBand ;
    private TextView blackScoreText ,whiteScoreText;
    Animation whiteOpeningAnimation, blackOpeningAnimation, whiteClosingAnimation, blackClosingAnimation,
            whiteWinsAnimationIsTurn, whiteLosesAnimationIsTurn, blackWinsAnimationIsTurn, blackLosesAnimationIsTurn,
            whiteWinsAnimationIsNotTurn, whiteLosesAnimationIsNotTurn, blackWinsAnimationIsNotTurn,
            blackLosesAnimationIsNotTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow ().setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        setContentView( R.layout.activity_game);

        this.rootLayout = findViewById ( R.id.rootLayout );
        this.circularRevealAnimation = new CircularRevealAnimation ( this, this.rootLayout, getResources () );

        if (savedInstanceState == null) {
            rootLayout.setVisibility(View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int x = rootLayout.getWidth() / 2;
                        // 64.5 dps is the dps of the center of the play button from the bottom of the screen
                        int y = rootLayout.getHeight() - circularRevealAnimation.getDips( (float) 64.5 );

                        circularRevealAnimation.AnimateOpening ( 1000, x, y );

                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }
        }

        fetchBoardResources ();
        fetchScoreBoardResources ();
        createBoardListeners ();

        new Thread(new Runnable() {
            public void run() {
                createGame();
            }
        }).start();
    }


    @Override
    public void onBackPressed() {
        int x = rootLayout.getWidth() / 2;
        // 64.5 dps is the dps of the center of the play button from the bottom of the screen
        int y = rootLayout.getHeight() - circularRevealAnimation.getDips( (float) 64.5 );

        circularRevealAnimation.AnimateClosing ( 400, x, y );
    }

    public void updateScoreBoard( int whitePlayerScore, int blackPlayerScore, Color playerTurn ){

        this.whiteScoreText.setText ( Integer.toString ( whitePlayerScore ) );
        this.blackScoreText.setText ( Integer.toString ( blackPlayerScore ) );

        if(playerTurn == Color.WHITE){
            this.whiteScoreText.startAnimation ( this.whiteOpeningAnimation );
            this.whiteScoreBand.startAnimation ( this.whiteOpeningAnimation );
            this.blackScoreText.startAnimation ( this.blackClosingAnimation );
            this.blackScoreBand.startAnimation ( this.blackClosingAnimation );
        }
        else {
            this.whiteScoreText.startAnimation ( this.whiteClosingAnimation );
            this.whiteScoreBand.startAnimation ( this.whiteClosingAnimation );
            this.blackScoreText.startAnimation ( this.blackOpeningAnimation );
            this.blackScoreBand.setAnimation ( this.blackOpeningAnimation );
        }
    }

    public void showGameResults(Player winner){
        if(winner.getColor () == Color.WHITE){
            //white wins
            this.whiteScoreText.setText ( "White Wins " );

            if(winner.isTurn ()){
                this.whiteScoreText.startAnimation ( this.whiteWinsAnimationIsTurn );
                this.whiteScoreBand.startAnimation ( this.whiteWinsAnimationIsTurn );
                this.blackScoreText.startAnimation ( this.blackLosesAnimationIsNotTurn );
                this.blackScoreBand.startAnimation ( this.blackLosesAnimationIsNotTurn );
            }
            else {
                this.whiteScoreText.startAnimation ( this.whiteWinsAnimationIsNotTurn );
                this.whiteScoreBand.startAnimation ( this.whiteWinsAnimationIsNotTurn );
                this.blackScoreText.startAnimation ( this.blackLosesAnimationIsTurn );
                this.blackScoreBand.startAnimation ( this.blackLosesAnimationIsTurn );
            }
        }
        if(winner.getColor () == Color.BLACK){
            //black wins
            this.blackScoreText.setText ( "Black Wins" );
            if(winner.isTurn ()){
                this.blackScoreText.startAnimation ( this.blackWinsAnimationIsTurn );
                this.blackScoreBand.startAnimation ( this.blackWinsAnimationIsTurn );
                this.whiteScoreText.startAnimation ( this.whiteLosesAnimationIsNotTurn );
                this.whiteScoreBand.startAnimation ( this.whiteLosesAnimationIsNotTurn );
            }
            else {
                this.blackScoreText.startAnimation ( this.blackWinsAnimationIsNotTurn );
                this.blackScoreBand.startAnimation ( this.blackWinsAnimationIsNotTurn );
                this.whiteScoreText.startAnimation ( this.whiteLosesAnimationIsTurn );
                this.whiteScoreBand.startAnimation ( this.whiteLosesAnimationIsTurn );
            }

        }
    }

    public void showGameResults(){
        // it's a tie
    }

    /**
     *
     * @param location location on the board
     * @param color disk color to assign to the given location
     */
    public void setBoardResources( DiskLocation location, Color color ){

        // Specified square on the board
        ImageView square = this.boardResources[location.getRow ()][location.getColumn ()];

        switch (color){
            case BLACK:
                square.setImageResource ( R.drawable.ic_circleblack );
                break;
            case WHITE:
                square.setImageResource ( R.drawable.ic_circlewhite );
                break;
            case BLANK:
                // Remove the white/black disk from the square
                square.setImageResource ( R.drawable.ic_circleblank );
                break;
        }
    }

    /**
     * Get the board ImageViews from the layout and put elements in an 2D array of ImageViews
     */
    private void fetchBoardResources (){
        this.boardResources = new ImageView[8][8];

        for (int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++){
                int rowNumber = row + 1;
                int colNumber = col + 1;

                // These line are necessary to "convert" a string (e.g. "i1", "i2" etc.) to a resource ID
                // For instance, the square at the [1, 1] position on the board has an id of "square_11"
                String id = "square_"+rowNumber+""+colNumber;
                int res = getResources().getIdentifier(id, "id", getPackageName());
                this.boardResources[row][col] = findViewById(res);
            }
    }

    /**
     * fetch the animations, image views and text views of the score board
     */
    private void fetchScoreBoardResources (){

        this.whiteScoreBand = findViewById ( R.id.whiteScoreBand );
        this.blackScoreBand = findViewById ( R.id.blackScoreBand );
        this.blackScoreText = findViewById ( R.id.blackScore );
        this.whiteScoreText = findViewById ( R.id.whiteScore );

        this.whiteOpeningAnimation = AnimationUtils.loadAnimation ( this, R.anim.white_expand_scoreboard_animation );
        this.blackOpeningAnimation = AnimationUtils.loadAnimation ( this, R.anim.black_expand_scoreboard_animation );
        this.whiteClosingAnimation = AnimationUtils.loadAnimation ( this, R.anim.white_collapse_scoreboard_animation );
        this.blackClosingAnimation = AnimationUtils.loadAnimation ( this, R.anim.black_collapse_scoreboard_animation );
        this.whiteWinsAnimationIsTurn = AnimationUtils.loadAnimation ( this, R.anim.white_wins_scoreboard_animation_isturn );
        this.whiteLosesAnimationIsTurn = AnimationUtils.loadAnimation ( this, R.anim.white_loses_scoreboard_animation_isturn );
        this.blackWinsAnimationIsTurn = AnimationUtils.loadAnimation ( this, R.anim.black_wins_scoreboard_animation_isturn );
        this.blackLosesAnimationIsTurn = AnimationUtils.loadAnimation ( this, R.anim.black_loses_scoreboard_animation_isturn );
        this.whiteWinsAnimationIsNotTurn = AnimationUtils.loadAnimation ( this, R.anim.white_wins_scoreboard_animation_isnot_turn );
        this.whiteLosesAnimationIsNotTurn = AnimationUtils.loadAnimation ( this, R.anim.white_loses_scoreboard_animation_isnot_turn );
        this.blackWinsAnimationIsNotTurn = AnimationUtils.loadAnimation ( this, R.anim.black_wins_scoreboard_animation_isnot_turn );
        this.blackLosesAnimationIsNotTurn = AnimationUtils.loadAnimation ( this, R.anim.black_loses_scoreboard_animation_isnot_turn );
    }

    /**
     * Create listeners for image views making the game board
     */
    private void createBoardListeners (){
        for (int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++){
                final int finalRow = row;
                final int finalCol = col;
                this.boardResources[finalRow][finalCol].setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick ( View v ) {
                        Log.i ( "Game Activity", "cell [" + finalRow + "][" + finalCol + "] has been selected");

                        //TODO
                        // Create a move
                        Move move = new Move (game.getCurrentPlayer (), new DiskLocation ( finalRow, finalCol ) );
                        game.addMove ( move );
                    }
                } );
            }
    }

    private void createGame(){
        Log.i ("Game Activity", "Creating a new game ...");
        this.game = new Game ( this );
    }
}