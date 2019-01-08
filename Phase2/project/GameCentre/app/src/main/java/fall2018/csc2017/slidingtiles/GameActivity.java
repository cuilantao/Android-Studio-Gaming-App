package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.R;
import fall2018.csc2017.UserAndScore.UserManager;

/**
 * The algorithm of preventing unsolvable sliding tiles cite from https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
 */

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * The list of the position of blank tile in each state.
     */
    public static ArrayList<Integer> positionList = new ArrayList<>();
    /**
     * The maximum step the player can use undo.(default 3)
     */
    private static int undoStep = 3;
    private static int columnWidth, columnHeight;
    /**
     * The board manager.
     */
    private BoardManager boardManager;
    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;
    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;

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
     * Add the new state's position of blank tile to the positionList.
     *
     * @param i the new position of blank tile
     */
    public static void addPosition(int i) {
        positionList.add(i);
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(StartingActivity.TEMP_SAVE_FILENAME);
        while (!puzzleHasSolve()) {
            boardManager = new BoardManager();
            saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
            loadFromFile(StartingActivity.TEMP_SAVE_FILENAME);
        }
        createTileButtons(this);
        setContentView(R.layout.activity_main);

        addUndoButtonListener();
        positionList.clear();
        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(Board.NUM_COLS);
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        long displayHeight = Math.round(0.9 * gridView.getMeasuredHeight());
                        int dH = (int) displayHeight;

                        columnWidth = displayWidth / Board.NUM_COLS;
                        columnHeight = dH / Board.NUM_ROWS;

                        display();
                    }
                });
        CountDownTimer timer = new CountDownTimer(600000, 1000) {
            @Override
            public void onTick(long l) {
                boardManager.setTime(boardManager.getTime() + 1);
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }

    /**
     * Add the Undo bottom.
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.Undo);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int blankId = boardManager.getBoard().numTiles();
                int blankRow = 0;
                int blankCol = 0;
                for (int row = 0; row != Board.NUM_ROWS; row++) {
                    for (int col = 0; col != Board.NUM_COLS; col++) {
                        if (boardManager.getBoard().getTile(row, col).getId() == blankId) {
                            blankRow = row;
                            blankCol = col;
                        }
                    }
                }
                if (positionList.size() == 0) {
                    makeToastUndo();
                } else if (BoardManager.getCanUndo()) {
                    int swapPosition = positionList.remove(positionList.size() - 1);
                    int swapRow = swapPosition / Board.NUM_ROWS;
                    int swapCol = swapPosition % Board.NUM_COLS;
                    boardManager.getBoard().swapTiles(blankRow, blankCol, swapRow, swapCol);
                }
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
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / Board.NUM_ROWS;
            int col = nextPos % Board.NUM_COLS;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
        saveToFile(StartingActivity.AUTO_SAVE_FILENAME);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
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
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        serializeUserManager();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }

    /**
     * Save UserManager to UserManager.ser
     */
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

    /**
     * Prevent unsolved sliding tiles
     */
    private boolean puzzleHasSolve() {
        ArrayList<Tile> tempList = new ArrayList<>();
        int total = 0;
        int blankPos = 0;
        boolean findBlank = false;
        for (int i = 0; i < Board.NUM_COLS; i++) {
            for (int j = 0; j < Board.NUM_COLS; j++) {
                Tile temp = boardManager.getBoard().getTile(i, j);
                tempList.add(temp);
            }
        }
        while (tempList.size() != 0) {
            Tile firstTile = tempList.get(0);
            tempList.remove(0);
            if (firstTile.getId() != Board.NUM_COLS * Board.NUM_ROWS) {
                if (!findBlank) {
                    blankPos++;
                }
                for (Tile tile : tempList) {
                    if (tile.compareTo(firstTile) > 0) {
                        total++;
                    }
                }
            } else {
                findBlank = true;
            }
        }
        boolean condition = false;
        if ((Board.NUM_ROWS % 2 == 1) && (total % 2 == 0)) {
            condition = true;
        } else if ((Board.NUM_ROWS % 2 == 0) && ((0 <= blankPos && blankPos <= 3) || (8 <= blankPos && blankPos <= 11)) && (total % 2 == 1)) {
            condition = true;
        } else if ((Board.NUM_ROWS % 2 == 0) && ((4 <= blankPos && blankPos <= 7) || (12 <= blankPos && blankPos <= 15)) && (total % 2 == 0)) {
            condition = true;
        }
        return condition;

    }

}
