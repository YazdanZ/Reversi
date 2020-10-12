package Unixado.Reversi;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIMOUT = 3000;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );

        // make the activity fullscreen
        getWindow ().setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView ( R.layout.activity_main );

        //Animations
        Animation topAnimation = AnimationUtils.loadAnimation ( this, R.anim.top_animation );
        Animation bottomAnimation = AnimationUtils.loadAnimation ( this, R.anim.bottom_animation );

        //Hooks
        ImageView image = findViewById ( R.id.logo );
        TextView appName = findViewById ( R.id.appName );

        image.setAnimation ( topAnimation );
        appName.setAnimation ( bottomAnimation );

        new Handler ( Looper.getMainLooper ()).postDelayed ( new Runnable () {
            @Override
            public void run () {
                Intent intent = new Intent (MainActivity.this, GameActivity.class);
                startActivity ( intent );
                finish ();
            }
        }, SPLASH_SCREEN_TIMOUT );
    }
}