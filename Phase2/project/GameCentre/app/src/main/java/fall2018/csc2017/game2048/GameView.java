package fall2018.csc2017.game2048;

/**
 * The source code is originated from
 * https://github.com/JimZhou-001/2048-Android.git
 * It is used to construct basic game structure and modified by our group member.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fall2018.csc2017.UserAndScore.Score;
import fall2018.csc2017.UserAndScore.UserManager;

/**
 * A class to implement the layout.
 */
public class GameView extends GridLayout {

    /**
     * A 2D array(4 * 4) to store cards.
     */
    public static Card[][] cards = new Card[4][4];
    /**
     * An ArrayList to store score.
     */
    private static ArrayList<Integer> scoreList = new ArrayList<>();
    /**
     * An ArrayList to store state.
     */
    private static ArrayList<int[][]> stateList = new ArrayList<>();
    /**
     * List of positions of blank card.
     */
    private static List<Point> emptyPoints = new ArrayList<>();
    /**
     * Whether the player has touched the gameView.
     */
    public boolean hasTouched = false;
    /**
     * A 2D array(4 * 4) used to store current cards state.
     */
    private int num[][] = new int[4][4];

    /**
     * Constructor of class GameView.
     */
    public GameView(Context context) {
        super(context);
        initGameView();
    }

    /**
     * Constructor of class GameView.
     */
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    /**
     * Constructor of class GameView.
     */
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    /**
     * Reset scoreList for new game.
     */
    public static void resetScoreList() {
        scoreList.clear();
    }

    /**
     * Reset stateList for new game.
     */
    public static void resetStateList() {
        stateList.clear();
    }

    /**
     * Add card(2 or 4) randomly, the probability is different.
     */
    public static void addRandomNum() {
        emptyPoints.clear();
        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 4; ++x) {
                if (cards[x][y].getNum() == 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }
        //Set the number on a blank card as 2 or 4 (the probability ratio is 9:1)
        Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
        cards[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
    }

    /**
     * Start the game.
     */
    public static void startGame() {
        GameActivity2048.getMainActivity().clearScore();
        set_cards();
    }

    public static void set_cards() {
        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 4; ++x) {
                cards[x][y].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }

    /**
     * Get the scoreList.
     *
     * @return scoreList
     */
    public ArrayList<Integer> getScoreList() {
        return scoreList;
    }

    /**
     * Get the stateList.
     *
     * @return stateList
     */
    public ArrayList<int[][]> getStateList() {
        return stateList;
    }

    /**
     * Initialize the gameView.
     */
    private void initGameView() {
        setRowCount(4);
        setColumnCount(4);
        setOnTouchListener(new Listener());
    }

    /**
     * Adjust the card size.
     *
     * @param w    int width.
     * @param h    int height.
     * @param oldw original width.
     * @param oldh original height.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = (Math.min(w, h) - 10) / 4;
        int cardHeight = (Math.min(w, h) - 10) / 4;
        addCards(cardWidth, cardHeight);

    }

    /**
     * Initialize the cards on the gameView.
     *
     * @param cardWidth  the width of each card.
     * @param cardHeight the height of each card.
     */
    private void addCards(int cardWidth, int cardHeight) {
        this.removeAllViews();
        Card c;
        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 4; ++x) {
                c = new Card(getContext());
                c.setNum(0);
                addView(c, cardWidth, cardHeight);
                cards[x][y] = c;
            }
        }
    }

    /**
     * Swipe left.
     */
    public void swipeLeft() {
        boolean b = false;
        for (int y = 0; y < 4; ++y) {
            //Each column.(There is no need to compare the last column)
            for (int x = 0; x < 3; ++x) {
                //Compare the number on cards.
                for (int x1 = x + 1; x1 < 4; ++x1) {
                    //If cards[x1][y] is not blank, then compare it with cards[x][y].
                    if (cards[x1][y].getNum() > 0) {
                        //If [x][y] is blank, then move [x1][y] to left.
                        if (cards[x][y].getNum() == 0) {
                            cards[x][y].setNum(cards[x1][y].getNum());
                            cards[x1][y].setNum(0);
                            --x;
                            b = true;
                        } else if (cards[x][y].equals(cards[x1][y])) {
                            //Combine two cards.
                            cards[x][y].setNum(cards[x][y].getNum() * 2);
                            cards[x1][y].setNum(0);
                            GameActivity2048.getMainActivity().addScore(cards[x][y].getNum());
                            b = true;
                        }
                        //If the card is empty, there is no need to compare.
                        break;
                    }
                }
            }
        }
        //Add a random card once the gameView changes.
        if (b) {
            addRandomNum();
            if (checkGameOver()) {
                ifOver();
            }//Check if the game is over after adding new cards.
        }
    }

    /**
     * Swipe Right.
     */
    public void swipeRight() {
        boolean b = false;
        for (int y = 0; y < 4; ++y) {
            for (int x = 3; x > 0; --x) {
                for (int x1 = x - 1; x1 >= 0; --x1) {
                    if (cards[x1][y].getNum() > 0) {
                        if (cards[x][y].getNum() == 0) {
                            cards[x][y].setNum(cards[x1][y].getNum());
                            cards[x1][y].setNum(0);
                            ++x;
                            b = true;
                        } else if (cards[x][y].equals(cards[x1][y])) {
                            cards[x][y].setNum(cards[x][y].getNum() * 2);
                            cards[x1][y].setNum(0);
                            GameActivity2048.getMainActivity().addScore(cards[x][y].getNum());
                            b = true;
                        }
                        break;
                    }
                }
            }
        }
        if (b) {
            addRandomNum();
            if (checkGameOver()) {
                ifOver();
            }
        }
    }

    /**
     * Swipe Up.
     */
    public void swipeUp() {
        boolean b = false;
        for (int x = 0; x < 4; ++x) {
            for (int y = 0; y < 3; ++y) {
                for (int y1 = y + 1; y1 < 4; ++y1) {
                    if (cards[x][y1].getNum() > 0) {
                        if (cards[x][y].getNum() == 0) {
                            cards[x][y].setNum(cards[x][y1].getNum());
                            cards[x][y1].setNum(0);
                            --y;
                            b = true;
                        } else if (cards[x][y].equals(cards[x][y1])) {
                            cards[x][y].setNum(cards[x][y].getNum() * 2);
                            cards[x][y1].setNum(0);
                            GameActivity2048.getMainActivity().addScore(cards[x][y].getNum());
                            b = true;
                        }
                        break;
                    }
                }
            }
        }
        if (b) {
            addRandomNum();
            if (checkGameOver()) {
                ifOver();
            }
        }
    }

    /**
     * Swipe Down.
     */
    public void swipeDown() {
        boolean b = false;
        for (int x = 0; x < 4; ++x) {
            for (int y = 3; y > 0; --y) {
                for (int y1 = y - 1; y1 >= 0; --y1) {
                    if (cards[x][y1].getNum() > 0) {
                        if (cards[x][y].getNum() == 0) {
                            cards[x][y].setNum(cards[x][y1].getNum());
                            cards[x][y1].setNum(0);
                            ++y;
                            b = true;
                        } else if (cards[x][y].equals(cards[x][y1])) {
                            cards[x][y].setNum(cards[x][y].getNum() * 2);
                            cards[x][y1].setNum(0);
                            GameActivity2048.getMainActivity().addScore(cards[x][y].getNum());
                            b = true;
                        }
                        break;
                    }
                }
            }
        }
        if (b) {
            addRandomNum();
            if (checkGameOver()) {
                ifOver();
            }
        }
    }

    /**
     * Check if the game is over.
     */
    public boolean checkGameOver() {
        boolean isOver = true;
        ALL:
        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 4; ++x) {
                // The condition for continuing the game：
                // 1. At least one blank card.
                // 2.No blank card, but there exists two cards adjacent to each other and the number
                // on these two cards are equal.

                if (cards[x][y].getNum() == 0 ||
                        (x < 3 && cards[x][y].getNum() == cards[x + 1][y].getNum()) ||
                        (y < 3 && cards[x][y].getNum() == cards[x][y + 1].getNum())) {
                    isOver = false;
                    break ALL;
                }
            }
        }
        return isOver;
    }

    /**
     * To Do when game over.
     */
    public void ifOver() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        UserManager.getLoginUser().addScore(new Score(GameActivity2048.getScore(), timestamp));
        new AlertDialog.Builder(getContext()).setTitle("Sorry, game is over!").
                setMessage("Your score is " + GameActivity2048.getScore()).
                setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startGame();
                    }
                }).show();

    }

    /**
     * OnTouch Listener to record things happens when onTouch.
     */
    class Listener implements View.OnTouchListener {

        private float startX, startY, offsetX, offsetY;

        /**
         * OnTouch Listener.
         */
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (!hasTouched) {
                hasTouched = true;
            }

            for (int y = 0; y < 4; ++y) {
                for (int x = 0; x < 4; ++x) {
                    num[y][x] = cards[y][x].getNum();
                }
            }

            if (stateList.size() <= 1) {
                scoreList.add(GameActivity2048.getScore());
                stateList.add(new int[4][4]);

                for (int y = 0; y < 4; ++y) {
                    for (int x = 0; x < 4; ++x) {
                        stateList.get(stateList.size() - 1)[y][x] = cards[y][x].getNum();
                    }
                }
            } else {
                if (!(Arrays.deepEquals(num, stateList.get(stateList.size() - 1)))) {

                    scoreList.add(GameActivity2048.getScore());
                    stateList.add(new int[4][4]);

                    for (int y = 0; y < 4; ++y) {
                        for (int x = 0; x < 4; ++x) {
                            stateList.get(stateList.size() - 1)[y][x] = cards[y][x].getNum();
                        }
                    }
                }
            }
            if (stateList.size() > GameActivity2048.getUndoStep()) {
                stateList.remove(0);
                scoreList.remove(0);
            }


            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = motionEvent.getX();
                    startY = motionEvent.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    offsetX = motionEvent.getX() - startX;
                    offsetY = motionEvent.getY() - startY;

                    if (Math.abs(offsetX) > Math.abs(offsetY)) {
                        if (offsetX < -5) {
                            swipeLeft();
                        } else if (offsetX > 5) {
                            swipeRight();
                        }
                    } else {
                        if (offsetY < -5) {
                            swipeUp();
                        } else if (offsetY > 5) {
                            swipeDown();
                        }
                    }

            }
            GameActivity2048.getMainActivity().saveToFile("2048auto" + UserManager.getLoginUser().getUserEmail() + ".ser");

            return true;

        }

    }


}