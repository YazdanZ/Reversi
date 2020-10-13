package Unixado.Reversi;

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

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        getWindow ().setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        setContentView ( R.layout.settings_activity );

        if (savedInstanceState == null) {
            final LinearLayout rootLayout = findViewById ( R.id.settingsLayout );
            rootLayout.setVisibility( View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
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
    public void onBackPressed () {
        super.onBackPressed ();

    }

    private void circularRevealActivity() {

        LinearLayout rootLayout = findViewById ( R.id.settingsLayout );
        int cx = getDips ( (float) 25.5 ); // the margin from top and left is 25.5 for the settings button
        int cy = getDips ( (float) 25.5 );

        float finalRadius = Math.max(rootLayout.getWidth(), rootLayout.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, 0, finalRadius);
        circularReveal.setDuration(1000);

        // make the view visible and start the animation
        rootLayout.setVisibility( View.VISIBLE);
        circularReveal.start();
    }

    private int getDips (float dps) {
        Resources resources = getResources ();
        return (int) TypedValue.applyDimension (
                TypedValue.COMPLEX_UNIT_DIP,
                dps,
                resources.getDisplayMetrics ()
        );
    }
}