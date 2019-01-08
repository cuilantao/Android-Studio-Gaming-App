package fall2018.csc2017.Scoreboard;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import fall2018.csc2017.UserAndScore.Score;
import fall2018.csc2017.UserAndScore.User;
import fall2018.csc2017.UserAndScore.UserManager;
import fall2018.csc2017.slidingtiles.GameChoose;

/**
 * The controller of the Scoreboard which control the run of the local socreboar.
 */
public class ScoreboardControllerLocal {

    private User current_user;
    private FragmentPageLocal current_page;

    public ScoreboardControllerLocal(User current_user, FragmentPageLocal current_page) {
        this.current_user = current_user;
        current_user.sort_score();
        this.current_page = current_page;

    }

    /**
     * Get the current playing user
     *
     * @return The player name
     */
    private String getGameName() {
        if (GameChoose.getCurrentGame().equals("sliding tiles")) {
            return "Sliding Tiles" + "\n" + "Global Ranking List";
        } else if (GameChoose.getCurrentGame().equals("minesweeper")) {
            return "Minesweeper" + "\n" + "Global Ranking List";
        } else {
            return "2048" + "\n" + "Global Ranking List";
        }
    }

    /**
     * Determine where could this score put
     *
     * @param pageNumber The page which display the score
     * @return The rank of this score
     */
    private String getPosition(int pageNumber) {
        if (pageNumber == 1) {
            return "1st";
        } else if (pageNumber == 2) {
            return "2nd";
        } else if (pageNumber == 3) {
            return "3rd";
        } else {
            return Integer.toString(pageNumber) + "th";
        }
    }

    /**
     * Display the email(username) on the page
     *
     * @param pageNumber The page number which put the score
     * @param rank       The rank of the score should be put
     * @return Return the text of teh score on the page.
     */
    private String getEmailText(int pageNumber, String rank) {
        if (current_user.getTopScore().size() < pageNumber) {
            return "HOI! You need to play once to let me know your score!";
        } else {
            return rank + "   Username: " + "\n" + current_user.getUserEmail();
        }
    }

    /**
     * Display the format of the score
     *
     * @param pageNumber The page which the score will display
     * @return Return the text of the score on the page
     */

    private String getScoreText(int pageNumber) {
        if (current_user.getTopScore().size() < pageNumber) {
            return "";
        } else {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Timestamp ts = current_user.getTopScore().get(pageNumber - 1).getTimeStamp();
            String gameComplexity;
            if (current_user.getTopScore().get(pageNumber - 1).getComplexity() == 3) {
                gameComplexity = "Easy Mode";
            } else if (current_user.getTopScore().get(pageNumber - 1).getComplexity() == 4) {
                gameComplexity = "Medium Mode";
            } else if (current_user.getTopScore().get(pageNumber - 1).getComplexity() == 0) {
                gameComplexity = "";
            } else {
                gameComplexity = "Hard Mode";
            }
            return "Score: " + Integer.toString(current_user.getTopScore()
                    .get(pageNumber - 1).getFinalScore()) + "\n" + gameComplexity + "\n" + "(Played at " + "\n"
                    + sdf.format(ts) + ")";
        }
    }

    /**
     * Update the view on the scoreboard
     *
     * @param pageNumber The number of the page which we need to show it on
     */
    public void updateView(int pageNumber) {
        current_page.displayScoreboardText(getGameName());
        current_page.displayEmailText(getEmailText(pageNumber, getPosition(pageNumber)));
        current_page.displayScoreText(getScoreText(pageNumber));
    }

}
