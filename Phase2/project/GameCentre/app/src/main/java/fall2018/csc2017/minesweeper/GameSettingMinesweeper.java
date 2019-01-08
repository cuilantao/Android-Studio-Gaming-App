package fall2018.csc2017.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import fall2018.csc2017.R;

/**
 * Setting of game minesweeper.
 */
public class GameSettingMinesweeper extends AppCompatActivity {

    /**
     * Mode selected/
     */
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_mineweeper);
        addEasyButtonListener();
        addMediumButtonListener();
        addHardButtonListener();
        addExitButtonListener();
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
                GridManagerMinesweeper.setBombNumber(8);
                GridManagerMinesweeper.setWIDTH(8);
                GridManagerMinesweeper.setHEIGHT(10);
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
                GridManagerMinesweeper.setBombNumber(14);
                GridManagerMinesweeper.setWIDTH(10);
                GridManagerMinesweeper.setHEIGHT(14);
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
                GridManagerMinesweeper.setBombNumber(20);
                GridManagerMinesweeper.setWIDTH(12);
                GridManagerMinesweeper.setHEIGHT(16);
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
     * Switch back to Starting activity.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, StartingActivityMinesweeper.class);
        startActivity(tmp);
    }
}
