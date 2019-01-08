package fall2018.csc2017.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import fall2018.csc2017.R;
import fall2018.csc2017.slidingtiles.GameChoose;
import fall2018.csc2017.slidingtiles.MyBounceInterpolator;

/*
https://www.youtube.com/watch?v=pznCs--BtJA
 */

public class  WelcomeActivity extends AppCompatActivity {
    LinearLayout l1, l2;
    Button btnsub;
    Animation uptodown, downtoup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btnsub = (Button) findViewById(R.id.buttonsub);
        l1 = (LinearLayout) findViewById(R.id.l1);
        l2 = (LinearLayout) findViewById(R.id.l2);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);
        addenterbuttonlistener();
    }

    private void addenterbuttonlistener() {
        Button startButton = findViewById(R.id.buttonsub);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchtogame();
            }
        });
    }

    private void switchtogame() {
        Button button = (Button) findViewById(R.id.buttonsub);
        startAnimation(button);
    }

    private void startAnimation(Button button) {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.c2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 5);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);
        final WelcomeActivity tmp1 = this;
        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent tmp = new Intent(tmp1, GameChoose.class);
                startActivity(tmp);
            }
        });
    }
}
