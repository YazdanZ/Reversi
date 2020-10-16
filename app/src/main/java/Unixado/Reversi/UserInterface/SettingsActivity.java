package Unixado.Reversi.UserInterface;

import Unixado.Reversi.R;
import Unixado.circularactivityreveal.CircularRevealAnimation;
import android.animation.Animator;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout rootLayout;
    private CircularRevealAnimation circularRevealAnimation;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        getWindow ().setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        setContentView ( R.layout.settings_activity );
        this.rootLayout = findViewById ( R.id.settingsLayout );


        this.circularRevealAnimation = new CircularRevealAnimation ( this, this.rootLayout, getResources () );

        if (savedInstanceState == null) {
            this.rootLayout.setVisibility( View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = this.rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // the margin from top and left is 25.5 for the settings button
                        int x = circularRevealAnimation.getDips ( (float) 25.5 );
                        int y = circularRevealAnimation.getDips ( (float) 25.5 );

                        circularRevealAnimation.AnimateOpening ( 500, x, y);

                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {

        int x = circularRevealAnimation.getDips ( (float) 25.5 );
        int y = circularRevealAnimation.getDips ( (float) 25.5 );

        circularRevealAnimation.AnimateClosing ( 500, x, y );
    }
}