package fall2018.csc2017.game2048;

import java.io.Serializable;

/**
 * The source code is originated from
 * https://github.com/JimZhou-001/2048-Android.git
 * It is used to construct basic game structure and modified by our group member.
 */

public class GameManager implements Serializable {

    /**
     * Static instance of game manager as a GameManager.
     */
    private static GameManager singleInstance = GameManager.getInstance();
    /**
     * Instance to store score of the game.
     */
    public int score;
    /**
     * Int Array to store cards.
     */
    private int[][] cards;

    /**
     * Constructor of GameManager.
     */
    public GameManager() {
        cards = new int[4][4];
        score = 0;
    }

    /**
     * Return the class instance as GameManager.
     *
     * @return GameManager.
     */
    public static GameManager getInstance() {
        if (singleInstance == null) {
            singleInstance = new GameManager();
        }
        return singleInstance;
    }


    /**
     * Get data in the cards.
     *
     * @param cards Card Array cards.
     * @param score int score.
     */
    public void getData(Card[][] cards, int score) {
        int i = 0;
        int j = 0;
        for (Card[] cl : cards) {
            for (Card c : cl) {
                this.cards[i][j] = c.getNum();
                j += 1;
            }
            i += 1;
            j = 0;
        }
        this.score = score;
    }

    /**
     * Set data in the cards.
     *
     * @param cards Card Array cards.
     */
    public void setData(Card[][] cards) {
        int i = 0;
        int j = 0;
        for (Card[] cl : cards) {
            for (Card c : cl) {
                cards[i][j].setNum(this.cards[i][j]);
                j += 1;
            }
            i += 1;
            j = 0;
        }
    }
}
