package Unixado.Reversi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );

        // make the activity fullscreen
        getWindow ().setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView ( R.layout.activity_main );

        //Animations
        Animation topAnimation = AnimationUtils.loadAnimation ( this, R.anim.top_animation );
        Animation bottomAnimation = AnimationUtils.loadAnimation ( this, R.anim.bottom_animation );
        Animation iconAnimation = AnimationUtils.loadAnimation ( this, R.anim.icon_animations );
        //Hooks
        ImageView image = findViewById ( R.id.logo );
        TextView appName = findViewById ( R.id.appName );
        Button playButton = findViewById ( R.id.playButton );
        Button settingsButton = findViewById ( R.id.settingsButton );

        image.setAnimation ( topAnimation );
        appName.setAnimation ( bottomAnimation );
        playButton.setAnimation ( iconAnimation );
        settingsButton.setAnimation ( iconAnimation );

        playButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                openGameActivity ();
            }
        } );

        settingsButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick ( View v ) {
                openSettingsActivity ();
            }
        } );
    }

    private void openGameActivity(){
        Intent intent = new Intent (MainActivity.this, GameActivity.class);
        startActivity ( intent );
        finish ();
    }

    private void openSettingsActivity(){
        Intent intent = new Intent (MainActivity.this, SettingsActivity.class);
        startActivity ( intent );
    }
}