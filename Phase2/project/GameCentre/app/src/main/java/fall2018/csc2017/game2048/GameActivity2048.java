package fall2018.csc2017.game2048;

/**
 * The source code is originated from
 * https://github.com/JimZhou-001/2048-Android.git
 * It is used to construct basic game structure and modified by our group member.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall2018.csc2017.R;
import fall2018.csc2017.UserAndScore.UserManager;
import fall2018.csc2017.slidingtiles.StartingActivity;

/**
 * A class to implement the game 2048.
 */
public class GameActivity2048 extends Activity {

    /**
     * A mainActivity.
     */
    private static GameActivity2048 mainActivity = null;
    /**
     * Current score.
     */
    private static int score = 0;
    /**
     * The maximum step the player can use undo.(default 3)
     */
    private static int undoStep = 3;
    /**
     * The button for restarting game.
     */
    Button load;
    /**
     * The button for saving game.
     */
    Button save;
    /**
     * TextView of score.
     */
    private TextView Score;
    /**
     * TextView of the max score.
     */
    private TextView maxScore;
    /**
     * The GameView gameView.
     */
    private GameView gameView;
    private GameManager gm = GameManager.getInstance();
    /**
     * Manual Save to File.
     */
    private String saveFileName = "2048" + UserManager.getLoginUser().getUserEmail() + ".ser";
    /**
     * Save to File for autoSave.
     */
    private String autoSaveFileName = "2048auto" + UserManager.getLoginUser().getUserEmail() + ".ser";

    /**
     * Constructor of class GameActivity2048.
     */
    public GameActivity2048() {
        mainActivity = this;
    }

    /**
     * Get the maximum step of undo.
     *
     * @return the maximum step of undo.
     */
    public static int getUndoStep() {
        return undoStep;
    }

    /**
     * Set the maximum step to i
     *
     * @param i the maximum step
     */
    public static void setUndoStep(int i) {
        undoStep = i;
    }

    /**
     * Get current score.
     *
     * @return int current score.
     */
    public static int getScore() {
        return score;
    }

    /**
     * Get the main activity.
     *
     * @return GameActivityMinesweeper.
     */
    public static GameActivity2048 getMainActivity() {
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Score = findViewById(R.id.Score);
        maxScore = findViewById(R.id.maxScore);
        String s = getSharedPreferences("pMaxScore", MODE_PRIVATE).getInt("maxScore", 0) + "";
        maxScore.setText(s);
        createGameOptionDialog();
        gameView = findViewById(R.id.gameView);
        load = findViewById(R.id.load);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFromFile(saveFileName);
                saveToFile(saveFileName);
                GameView.resetScoreList();
                GameView.resetStateList();
            }
        });
        addUndoButtonListener();
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToFile(saveFileName);
                makeToastSaveText();
            }
        });

    }

    /**
     * Clear the score.
     */
    public void clearScore() {
        score = 0;
        showScore();
    }

    /**
     * Add score.
     *
     * @param i Score to add.
     */
    public void addScore(int i) {

        score += i;
        showScore();
        SharedPreferences pref = getSharedPreferences("pMaxScore", MODE_PRIVATE);

        //If the current score is higher than the max score, then update the max score.
        if (score > pref.getInt("maxScore", 0)) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("maxScore", score);
            editor.apply();
            String s = pref.getInt("maxScore", 0) + "";
            maxScore.setText(s);
        }

    }

    /**
     * Add the Undo bottom.
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.back);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameView.getStateList().size() == 0) {
                    makeToastUndo();
                }
                if (gameView.hasTouched && gameView.getStateList().size() >= 1) {
                    score = gameView.getScoreList().get(gameView.getScoreList().size() - 1);
                    gameView.getScoreList().remove(gameView.getScoreList().size() - 1);
                    showScore();
                    int[][] newState =
                            gameView.getStateList().get(gameView.getStateList().size() - 1);
                    gameView.getStateList().remove(gameView.getStateList().size() - 1);
                    for (int y = 0; y < 4; ++y) {
                        for (int x = 0; x < 4; ++x) {
                            gameView.cards[y][x].setNum(newState[y][x]);
                        }
                    }
                }
                GameActivity2048.getMainActivity().saveToFile("2048auto" + UserManager.getLoginUser().getUserEmail() + ".ser");
            }
        });
    }

    /**
     * Display that the player can't undo anymore.
     */
    private void makeToastUndo() {
        Toast.makeText(this, "Can't undo any more!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Show the current score.
     */
    public void showScore() {
        Score.setText(score + "");
    }

    /**
     * Override Android back button.
     */
    @Override
    public void onBackPressed() {
        createExitTipDialog();
    }

    /**
     * Ask user whether want to exit the game or not.
     */
    private void createExitTipDialog() {
        new AlertDialog.Builder(GameActivity2048.this)
                .setMessage("Exit？")
                .setTitle("Reminder")
                .setIcon(R.drawable.tip)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(saveFileName);
    }

    /**
     * Let user select whether to resume from auto save or load from saved game or start a new game.
     */
    private void createGameOptionDialog() {
        new AlertDialog.Builder(GameActivity2048.this)
                .setMessage("hOI, what do you want to do?")
                .setTitle("Option")
                .setIcon(R.drawable.tip)
                .setPositiveButton("New Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        GameView.startGame();
                    }
                })
                .setNegativeButton("Load", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        loadFromFile(saveFileName);
                    }
                })
                .setNeutralButton("Resume", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        loadFromFile(autoSaveFileName);
                    }
                })
                .show();
    }

    /**
     * Save the game.
     *
     * @param fileName filename for saved game.
     */
    public void saveToFile(String fileName) {
        serializeUserManager();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            gm.getData(GameView.cards, score);
            outputStream.writeObject(gm);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Load from saved games.
     *
     * @param fileName filename of saved game.
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                gm = (GameManager) input.readObject();
                gm.setData(GameView.cards);
                score = gm.score;
                showScore();
                inputStream.close();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("No class is found.");
        } catch (FileNotFoundException e) {
            gameView.startGame();
            makeToastNoFilesText();
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }

    /**
     * Display the start a new game when no saved files found.
     */
    private void makeToastNoFilesText() {
        Toast.makeText(this, "No Files Found, Start a New Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display files has been saved.
     */
    private void makeToastSaveText() {
        Toast.makeText(this, "File has saved", Toast.LENGTH_SHORT).show();
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
