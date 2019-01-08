package fall2018.csc2017.UserAndScore;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable, Comparable<User> {

    /**
     * The email of the user.
     */
    String userEmail;

    /**
     * The password of the user
     */
    String password;

    /**
     * List of top score the user have
     */
    private List<Score> topScore;
    private List<Score> score2048;
    private List<Score> scoreSlidingTiles;
    private List<Score> scoreMineSweeper;

    /**
     * User class constructor. Given email and password.
     *
     * @param user_email: the given email
     * @param password:   the given password
     */
    public User(String user_email, String password) {
        this.userEmail = user_email;
        this.password = password;
        this.score2048 = new ArrayList<>();
        this.scoreSlidingTiles = new ArrayList<>();
        this.scoreMineSweeper = new ArrayList<>();
    }

    /**
     * Get the top score the user have.
     *
     * @return the topScore.
     */
    public List<Score> getTopScore() {
        return topScore;
    }

    /**
     * Get the user's email.
     *
     * @return the userEmail.
     */
    public String getUserEmail() {
        return userEmail;
    }

    public String getPassword() {
        return password;
    }

    /**
     * add score to top score
     *
     * @param s: score to add
     */
    public void addScore(Score s) {
        topScore.add(s);
        sort_score();
    }

    /**
     * return the best score of the user.
     *
     * @return Score: best score of the user, null if no game is complete.
     */
    public Score returnBestScore() {
        if (topScore.isEmpty()) {
            return null;
        } else {
            Score best_score = topScore.get(0);
            for (Score s : topScore) {
                if (s.getFinalScore() > best_score.getFinalScore()) {
                    best_score = s;
                }
            }
            return best_score;
        }
    }

    @Override
    public int compareTo(@NonNull User user) {
        if (this.topScore.isEmpty() && user.topScore.isEmpty()) {
            return 0;
        } else if (user.topScore.isEmpty()) {
            return 1;
        } else if (this.topScore.isEmpty()) {
            return -1;
        }
        return this.returnBestScore().compareTo(user.returnBestScore());
    }

    public void sort_score() {
        int n = topScore.size();
        for (int i = 1; i < n; ++i) {
            Score key = topScore.get(i);
            int j = i - 1;
            while (j >= 0 && topScore.get(j).getFinalScore() < key.getFinalScore()) {
                topScore.set(j + 1, topScore.get(j));
                j = j - 1;
            }
            topScore.set(j + 1, key);
        }
    }

    public void switch_game(String game) {
        if (game.equals("2048")) {
            topScore = score2048;
        } else if (game.equals("Sliding Tiles")) {
            topScore = scoreSlidingTiles;
        } else if (game.equals("Mine Sweeper")) {
            topScore = scoreMineSweeper;
        }
    }
}
