package Unixado.Reversi.UserInterface;

import Unixado.Reversi.R;
import Unixado.circularactivityreveal.CircularRevealAnimation;
import android.animation.Animator;
import android.content.res.Resources;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;

public class GameActivity extends AppCompatActivity {

    private ConstraintLayout rootLayout;
    private CircularRevealAnimation circularRevealAnimation;
    private ImageView[][] board;

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
    }


    @Override
    public void onBackPressed() {
        int x = rootLayout.getWidth() / 2;
        // 64.5 dps is the dps of the center of the play button from the bottom of the screen
        int y = rootLayout.getHeight() - circularRevealAnimation.getDips( (float) 64.5 );

        circularRevealAnimation.AnimateClosing ( 400, x, y );
    }

    private void getBoardResources(){
        this.board = new ImageView[8][8];

        for (int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++){
                // This line is necessary to "convert" a string (e.g. "i1", "i2" etc.) to a resource ID
                // For instance, the square at the [1, 1] position on the board has an id of "square_11"

                int rowNumber = row + 1;
                int colNumber = col + 1;

                String id = "square_"+rowNumber+""+colNumber;
                int res = getResources().getIdentifier(id, "id", getPackageName());
                this.board[row][col] = findViewById(res);
            }
    }

    private void setBoardResources(){

    }
}