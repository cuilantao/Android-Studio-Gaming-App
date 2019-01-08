package fall2018.csc2017.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import fall2018.csc2017.R;
import fall2018.csc2017.login.LoginActivity;

/*
https://www.youtube.com/watch?v=h_hTuaEpc-8
 */
public class SplashActivity extends AppCompatActivity {
    private ImageView iv;
    private ImageView iv1;
    private ImageView iv2;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        iv.startAnimation(myanim);
        iv1.startAnimation(myanim);
        iv2.startAnimation(myanim);
        tv.startAnimation(myanim);
        final Intent tmp = new Intent(this, LoginActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(tmp);
                    finish();
                }

            }
        };
        timer.start();

    }
}
