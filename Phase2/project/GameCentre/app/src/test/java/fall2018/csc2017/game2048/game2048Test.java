package fall2018.csc2017.game2048;

import android.test.mock.MockContext;

import org.junit.Test;


import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class game2048Test {

    MockContext context = new android.test.mock.MockContext();

    Card card1 = new Card(context);
    Card card2 = new Card(context);

    @Test
    public void TestEquals() {
        card1.setNum(0);
        card2.setNum(0);
        assertTrue(card1.equals(card2));
        for (int i = 1; i <= 8192; i = i * 2) {
            card2.setNum(i);
            assertFalse(card1.equals(card2));
        }
    }

    @Test
    public void TestGetData() {
        Card[][] cards = new Card[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cards[i][j] = new Card(context);
                cards[i][j].setNum(1);
            }
        }
        Card[][] cards1 = new Card[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cards1[i][j] = new Card(context);
                cards1[i][j].setNum(2);
            }
        }
        GameManager.getInstance().getData(cards, 0);
        GameManager.getInstance().setData(cards1);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertTrue(cards[i][j].equals(cards1[i][j]));
            }
        }
    }

    @Test
    public void TestCheckGameOver() {
        GameView gView = new GameView(context);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Card c = new Card(context);
                c.setNum(i + 2* j + 1);
                gView.cards[i][j] = c;
            }
        }
        assertEquals(true, gView.checkGameOver());
    }

}
