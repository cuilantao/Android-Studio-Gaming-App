package fall2018.csc2017.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;


import fall2018.csc2017.R;
import fall2018.csc2017.UserAndScore.UserManager;

/**
 * Main activity interface of game minesweeper.
 */
public class GameActivityMinesweeper extends Activity {

    GameActivityMinesweeper main;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GridManagerMinesweeper.is_over = false;
        main = this;
        setContentView(R.layout.activity_main2);
        GridManagerMinesweeper.setIsLost(false);
        Log.e("GameActivity2048", "onCreate");
        GridManagerMinesweeper.getInstance().createGrid(this);
        TextView txt = findViewById(R.id.bombNum);
        String str = "#BOMBS: " + Integer.toString(GridManagerMinesweeper.getBombNumber());
        txt.setText(str);
        addReStartButtonListener();
        CountDownTimer timer = new CountDownTimer(600000, 1000) {
            @Override
            public void onTick(long l) {
                GridManagerMinesweeper.getInstance().time += 1;
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }

    /**
     * Create a new game.
     */
    private void addReStartButtonListener() {

        final Button newButton = findViewById(R.id.newGame);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tmp = new Intent(main, GameActivityMinesweeper.class);
                startActivity(tmp);

                finish();
            }
        });
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        serializeUserManager();
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
