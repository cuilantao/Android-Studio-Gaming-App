package fall2018.csc2017.slidingtiles;
/**
 * https://evgenii.com/blog/spring-button-animation-on-android/
 * https://stackoverflow.com/questions/36894384/android-move-background-continuously-with-animation
 * https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
 */


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall2018.csc2017.R;
import fall2018.csc2017.Scoreboard.scoreboard;
import fall2018.csc2017.UserAndScore.UserManager;
import fall2018.csc2017.login.LoginActivity;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {

    /**
     * The main save file.
     */
    public static final String SAVE_FILENAME = "save_file_st" + UserManager.getLoginUser().getUserEmail() + ".ser";
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_st_tmp.ser";
    /**
     * A auto save file.
     */
    public static final String AUTO_SAVE_FILENAME = "save_file_st_auto" + UserManager.getLoginUser().getUserEmail() + ".ser";
    /**
     * Int created for only pop default mode msg once.
     */
    private static int showDefault = 1;
    /**
     * The board manager.
     */
    private BoardManager boardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        serializeUserManager();
        UserManager.get_instance().switch_game("Sliding Tiles");
        super.onCreate(savedInstanceState);
        boardManager = new BoardManager();
        if (showDefault == 1) {
            makeToastModeText();
            showDefault++;
        }
        saveToFile(TEMP_SAVE_FILENAME);
        setContentView(R.layout.activity_starting_);
        addStartButtonListener();
        addSaveButtonListener();
        addSettingButtonListener();
        addScoreboardButtonListener();
        addSignoutButtonListener();
        addExitButtonListener();
    }

    /**
     * Start a new game.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createResumeTipDialog();
            }
        });
    }

    /**
     * Enquire if the player want to continue the game
     */

    private void createResumeTipDialog() {
        new AlertDialog.Builder(StartingActivity.this)
                .setMessage("Choose the game mode you want")
                .setTitle("Option")
                .setIcon(R.drawable.tip)
                .setPositiveButton("Load", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        loadFromFile(SAVE_FILENAME);
                        switchToGame();
                    }
                })
                .setNegativeButton("Resume", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        loadFromFile(AUTO_SAVE_FILENAME);
                        switchToGame();
                        makeToastLoadedText("Resuming game");
                    }
                })
                .setNeutralButton("New Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        boardManager = new BoardManager();
                        switchToGame();
                    }
                })
                .show();
    }

    /**
     * Button for entering scoreboard interface.
     */
    private void addScoreboardButtonListener() {
        Button startButton = findViewById(R.id.scoreboardButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    private void signout() {
        Intent tmp = new Intent(this, LoginActivity.class);
        startActivity(tmp);
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        final Button saveButton = findViewById(R.id.SaveButton);
        final StartingActivity tmp1 = this;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(SAVE_FILENAME);
                saveToFile(TEMP_SAVE_FILENAME);
                makeToastSavedText();
                final Animation myAnim = AnimationUtils.loadAnimation(tmp1, R.anim.bounce);
                // Use bounce interpolator with amplitude 0.c2 and frequency 20
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(interpolator);
                saveButton.startAnimation(myAnim);
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

//    /**
//     * Read the temporary board from disk.
//     */
//    @Override
//    protected void onResume() {
//        super.onResume();
//        loadFromFile(TEMP_SAVE_FILENAME);
//    }

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
     * @param button the start button.
     */
    private void startAnimation(Button button) {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.c2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);
        final StartingActivity tmp1 = this;
        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent tmp = new Intent(tmp1, GameActivity.class);
                saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
                startActivity(tmp);
            }
        });
    }

    /**
     * Switch to activity_setting interface.
     */
    private void switchToSetting() {
        final StartingActivity tmp1 = this;
        Intent tmp = new Intent(tmp1, GameSetting.class);
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
        makeToastUndoText();
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
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager) input.readObject();
                Board.setNumCols(boardManager.getBoard().tiles.length);

                Board.setNumRows(boardManager.getBoard().tiles.length);
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            boardManager = new BoardManager();
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Display the default mode -- medium.
     */
    private void makeToastModeText() {
        Toast.makeText(this, "DEFAULT MODE: MEDIUM", Toast.LENGTH_SHORT).show();
    }


    /**
     * Display undo setting configuration: maximum undo steps selected by user.
     */
    private void makeToastUndoText() {
        String str = " Current Num Undo Steps:" + GameSetting.numUndo;
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * Button for go back to game choosing selection layout.
     */
    private void addExitButtonListener() {
        Button exitButton = findViewById(R.id.backTwo);
        exitButton.setOnClickListener(new View.OnClickListener() {
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
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
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
