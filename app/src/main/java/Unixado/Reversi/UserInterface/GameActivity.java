package Unixado.Reversi.UserInterface;

import Unixado.Reversi.Game.Game;
import Unixado.Reversi.Game.Move;
import Unixado.Reversi.Game.DiskLocation;
import Unixado.Reversi.Game.Color;
import Unixado.Reversi.R;
import Unixado.circularactivityreveal.CircularRevealAnimation;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;

public class GameActivity extends AppCompatActivity {

    // Root layout of our activity
    private ConstraintLayout rootLayout;
    private CircularRevealAnimation circularRevealAnimation;
    private ImageView[][] boardResources;
    private Game game;

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