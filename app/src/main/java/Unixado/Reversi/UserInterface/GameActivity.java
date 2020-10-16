package Unixado.Reversi.UserInterface;

import Unixado.Reversi.Game.PieceLocation;
import Unixado.Reversi.Game.Color;
import Unixado.Reversi.R;
import Unixado.circularactivityreveal.CircularRevealAnimation;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;

public class GameActivity extends AppCompatActivity {

    private ConstraintLayout rootLayout;
    private CircularRevealAnimation circularRevealAnimation;
    private ImageView[][] boardResources;
    private Color[][] board;

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

        getBoardResources ();
        createEmptyBoard ();
        createBoardListeners ();
    }


    @Override
    public void onBackPressed() {
        int x = rootLayout.getWidth() / 2;
        // 64.5 dps is the dps of the center of the play button from the bottom of the screen
        int y = rootLayout.getHeight() - circularRevealAnimation.getDips( (float) 64.5 );

        circularRevealAnimation.AnimateClosing ( 400, x, y );
    }

    /**
     * Get the board ImageViews from the layout and put elements in an 2D array of ImageViews
     */
    private void getBoardResources(){
        this.boardResources = new ImageView[8][8];

        for (int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++){
                // This line is necessary to "convert" a string (e.g. "i1", "i2" etc.) to a resource ID
                // For instance, the square at the [1, 1] position on the board has an id of "square_11"

                int rowNumber = row + 1;
                int colNumber = col + 1;

                String id = "square_"+rowNumber+""+colNumber;
                int res = getResources().getIdentifier(id, "id", getPackageName());
                this.boardResources[row][col] = findViewById(res);
            }
    }

    /**
     *
     * @param location location on the board
     * @param color piece color to assign to the given location
     */
    private void setBoardResources( PieceLocation location, Color color ){

        // Specified square on the board
        ImageView square = this.boardResources[location.getX ()][location.getY ()];

        switch (color){
            case BLACK:
                square.setImageResource ( R.drawable.ic_circleblack );
                break;
            case WHITE:
                square.setImageResource ( R.drawable.ic_circlewhite );
                break;
            case NONE:
                // Remove the white/black piece from the square
                square.setImageResource ( 0 );
                break;
        }
    }

    private void createEmptyBoard(){
        this.board = new Color[8][8];

        for (int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++)
                this.board[row][col] = Color.NONE;
    }

    private void createBoardListeners(){
        for (int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++){
                final int finalRow = row;
                final int finalCol = col;
                this.boardResources[finalRow][finalCol].setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick ( View v ) {
                        setBoardPiece ( new PieceLocation ( finalRow, finalCol ), Color.BLACK );
                    }
                } );
            }
    }

    /**
     *
     * @return board 2D array
     */
    public Color[][] getBoard(){
        return this.board.clone ();
    }

    /**
     * Update board and update the displayed piece color
     * @param location location on the board
     * @param color piece color to assign to the given location
     */
    public void setBoardPiece(PieceLocation location, Color color){
        this.board[location.getX ()][location.getY ()] = color;
        setBoardResources ( location, color );
    }

}