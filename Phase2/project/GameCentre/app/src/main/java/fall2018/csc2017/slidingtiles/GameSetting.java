package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fall2018.csc2017.R;

public class GameSetting extends AppCompatActivity {

    /**
     * Initialize the default UndoStep.
     */
    public static int numUndo = 3;
    /**
     * The board manager.
     */
    private BoardManager boardManager;
    /**
     * Mode selected/
     */
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = new BoardManager();

        setContentView(R.layout.activity_setting);
        addEasyButtonListener();
        addMediumButtonListener();
        addHardButtonListener();
        addExitButtonListener();
        addSetPButtonListener();
        addSetMButtonListener();
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
                GameActivity.setUndoStep(numUndo);
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
                GameActivity.setUndoStep(numUndo);
                makeToastUndoText();
            }
        });
    }

    /**
     * Exit the setting menu.
     */
    private void addExitButtonListener() {
        Button exitButton = findViewById(R.id.EXIT);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame();
            }
        });
    }

    /**
     * Set the game's complexity to easy.
     */
    private void addEasyButtonListener() {
        final Button easyButton = findViewById(R.id.EASY);
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Board.setNumRows(3);
                Board.setNumCols(3);
                boardManager = new BoardManager();
                mode = "EASY";
                makeToastModeText();

            }
        });
    }

    /**
     * Set the game's complexity to medium.
     */
    private void addMediumButtonListener() {
        final Button mediumButton = findViewById(R.id.MEDIUM);
        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Board.setNumRows(4);
                Board.setNumCols(4);
                boardManager = new BoardManager();
                mode = "MEDIUM";
                makeToastModeText();
            }
        });
    }

    /**
     * Set the game's complexity to hard.
     */
    private void addHardButtonListener() {
        final Button hardButton = findViewById(R.id.HARD);
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Board.setNumRows(5);
                Board.setNumCols(5);
                boardManager = new BoardManager();
                mode = "HARD";
                makeToastModeText();
            }
        });
    }

    /**
     * Toast information of mode selected.
     */
    private void makeToastModeText() {
        Toast.makeText(this, "MODE SELECTED:" + mode, Toast.LENGTH_SHORT).show();
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
     * Switch back to Starting activity.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, StartingActivity.class);
        startActivity(tmp);
    }

    /**
     * Override Android back button.
     */
    @Override
    public void onBackPressed() {
        switchToGame();
    }
}
