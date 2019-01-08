package fall2018.csc2017.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;

import fall2018.csc2017.R;
import fall2018.csc2017.Scoreboard.scoreboard;
import fall2018.csc2017.UserAndScore.UserManager;
import fall2018.csc2017.login.LoginActivity;
import fall2018.csc2017.slidingtiles.GameChoose;
import fall2018.csc2017.slidingtiles.MyBounceInterpolator;

/**
 * The initial activity for the minesweeper game.
 * The source code is originated from
 * https://github.com/marcellelek/Minesweeper.git
 * It is used to construct basic game structure and modified by our group member.
 */
public class StartingActivityMinesweeper extends AppCompatActivity {

    private static int showDefault = 1;

    /**
     * GridManagerMinesweeper of the game.
     */
    private GridManagerMinesweeper gameengine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        serializeUserManager();
        UserManager.get_instance().switch_game("Mine Sweeper");
        super.onCreate(savedInstanceState);
        gameengine = GridManagerMinesweeper.getInstance();
        if (showDefault == 1) {
            makeToastModeText();
            showDefault++;
        }
        setContentView(R.layout.activity_starting_mine);
        addStartButtonListener();
        addSettingButtonListener();
        addScoreboardButtonListener();
        addSignoutButtonListener();
        addBackButtonListener();
    }

    /**
     * The back button.
     */
    private void addBackButtonListener() {
        Button backButton = findViewById(R.id.backTwo);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSelecting();
            }
        });
    }

    /**
     * Switch to game choosing interface.
     * Used by ExitButtonListener.
     */
    private void switchToSelecting() {
        Intent tmp = new Intent(this, GameChoose.class);
        startActivity(tmp);
    }

    /**
     * Start a new game.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameengine = GridManagerMinesweeper.getInstance();
                switchToGame();
            }
        });
    }

    /**
     * Button for entering scoreboard interface.
     */
    private void addScoreboardButtonListener() {
        Button startButton = findViewById(R.id.scoreboardButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serializeUserManager();
                switchToScoreboard();

            }
        });
    }

    /**
     * Button listener for game setting.
     * Enter activity_setting interface.
     */
    private void addSettingButtonListener() {
        Button startButton = findViewById(R.id.SETTING);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSetting();
            }
        });
    }

    /**
     * Attempt to sign out
     */
    private void addSignoutButtonListener() {
        Button loadButton = findViewById(R.id.Signout);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });
    }

    /**
     * Sign out user.
     */
    private void signout() {
        Intent tmp = new Intent(this, LoginActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Button button = (Button) findViewById(R.id.StartButton);
        startAnimation(button);
    }

    /**
     * Animation added for start button.
     *
     * @param button
     */
    private void startAnimation(Button button) {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.c2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);
        final StartingActivityMinesweeper tmp1 = this;
        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent tmp = new Intent(tmp1, GameActivityMinesweeper.class);
                startActivity(tmp);
            }
        });
    }

    /**
     * Switch to activity_setting interface.
     */
    private void switchToSetting() {
        final StartingActivityMinesweeper tmp1 = this;
        Intent tmp = new Intent(tmp1, GameSettingMinesweeper.class);
        startActivity(tmp);
    }

    /**
     * Switch to Scoreboard interface.
     */
    private void switchToScoreboard() {
        Intent tmp = new Intent(this, scoreboard.class);
        startActivity(tmp);
    }

    /**
     * Display the default mode -- medium.
     */
    private void makeToastModeText() {
        Toast.makeText(this, "DEFAULT MODE: MEDIUM", Toast.LENGTH_SHORT).show();
    }

    private void serializeUserManager() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput("UserManager.ser", MODE_PRIVATE));
            outputStream.writeObject(UserManager.get_instance());
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
