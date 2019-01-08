package fall2018.csc2017.game2048;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;

import fall2018.csc2017.R;
import fall2018.csc2017.slidingtiles.GameChoose;
import fall2018.csc2017.login.LoginActivity;
import fall2018.csc2017.Scoreboard.scoreboard;
import fall2018.csc2017.UserAndScore.UserManager;

/**
 * The initial activity for the game2048.
 * <p>
 * The source code is originated from
 * https://github.com/JimZhou-001/2048-Android.git
 * It is used to construct basic game structure and modified by our group member.
 * <p>
 * https://evgenii.com/blog/spring-button-animation-on-android/
 * https://stackoverflow.com/questions/36894384/android-move-background-continuously-with-animation
 * https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
 */
public class StartingActivity2048 extends AppCompatActivity {

    /**
     * The board manager.
     */

    /**
     * Initialize the default UndoStep.
     */
    private static int numUndo = 3;

    /**
     * The string of the current game.
     */
    String currentGame = "2048";

    /**
     * The current manager.
     */
    private UserManager current_manager = UserManager.get_instance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        serializeUserManager();
        current_manager.switch_game(currentGame);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048_starting);
        addStartButtonListener();
        addScoreboardButtonListener();
        addSignOutButtonListener();
        addSetPButtonListener();
        addSetMButtonListener();
        addBackButtonListener();
    }

    /**
     * Go back to the game choosing menu.
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
     * Add one undo step.
     */
    private void addSetPButtonListener() {
        Button setButton = findViewById(R.id.undoSetP);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txt = findViewById(R.id.undoStp);
                numUndo += 1;
                String str = "Undo " + Integer.toString(numUndo) + " Steps";
                txt.setText(str);
                GameActivity2048.setUndoStep(numUndo);
                makeToastUndoText();
            }
        });
    }

    /**
     * Minus one undo step.
     */
    private void addSetMButtonListener() {
        Button setButton = findViewById(R.id.undoSetM);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txt = findViewById(R.id.undoStp);
                if (numUndo >= 1) {
                    numUndo -= 1;
                }
                String str = "Undo " + Integer.toString(numUndo) + " Steps";
                if (numUndo == 1) {
                    str = "Undo " + Integer.toString(numUndo) + " Step";
                }
                txt.setText(str);
                GameActivity2048.setUndoStep(numUndo);
                makeToastUndoText();
            }
        });
    }

    /**
     * Toast information of number of undo step selected.
     */
    private void makeToastUndoText() {
        String str = "Number of Undo Steps:" + numUndo;
        if (numUndo == 0) {
            str = "Minimum Undo Step:" + numUndo;
        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * Start a new game.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
     * Attempt to sign out
     */
    private void addSignOutButtonListener() {
        Button loadButton = findViewById(R.id.Signout);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });
    }

    private void signout() {
        Intent tmp = new Intent(this, LoginActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Button button = findViewById(R.id.StartButton);
        startAnimation(button);
    }

    /**
     * Animation added for start button.
     *
     * @param button the start button.
     */
    private void startAnimation(Button button) {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.c2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);
        final StartingActivity2048 tmp1 = this;
        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent tmp = new Intent(tmp1, GameActivity2048.class);
                startActivity(tmp);
            }
        });
    }

    /**
     * Switch to Scoreboard interface.
     */
    private void switchToScoreboard() {
        Intent tmp = new Intent(this, scoreboard.class);
        startActivity(tmp);
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

