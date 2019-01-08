package fall2018.csc2017.UserAndScore;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Score class that represents the score of player.
 */
public class Score implements Comparable<Score>, Serializable {

    /**
     * Final score of this game.
     */
    private int finalScore;
    /**
     * Complexity of the game.
     */
    private int complexity;
    /**
     * Real time when you get the score.
     */
    private Timestamp timeStamp;

    public Score(int original_score, int complexity, Timestamp timeStamp) {
        this.timeStamp = timeStamp;
        this.complexity = complexity;
        this.finalScore = original_score + 5000 * (complexity - 3);
        if (this.finalScore < 0) {
            this.finalScore = 0;
        }
    }

    public Score(int finalScore, Timestamp timeStamp) {
        this.finalScore = finalScore;
        this.timeStamp = timeStamp;
    }

    /**
     * Get the complexity.
     *
     * @return complexity of the game.
     */
    public int getComplexity() {
        return complexity;
    }

    /**
     * Get the final score.
     *
     * @return final score.
     */
    public int getFinalScore() {
        return finalScore;
    }

    /**
     * Get the timeStamp of the score.
     *
     * @return timeStamp of the score.
     */
    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    @Override
    public int compareTo(@NonNull Score score) {
        if (this.getFinalScore() < score.getFinalScore()) {
            return -1;
        } else if (this.getFinalScore() > score.getFinalScore()) {
            return 1;
        } else {
            return 0;
        }
    }
}
