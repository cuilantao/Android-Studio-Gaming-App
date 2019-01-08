package fall2018.csc2017.Scoreboard;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.UserAndScore.User;
import fall2018.csc2017.UserAndScore.UserManager;
import fall2018.csc2017.slidingtiles.GameChoose;

/**
 * The controller of the Scoreboard which control the run of the global scoreboard
 */
public class ScoreboardController {

    private UserManager current_manager;
    private FragmentPage current_page;

    public ScoreboardController(UserManager current_manager, FragmentPage current_page) {
        this.current_manager = current_manager;
        this.current_page = current_page;

    }

    /**
     * Determine which game is currently playing
     *
     * @return Game name
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
     * Get the current playing user
     *
     * @return The player name
     */
    private List<User> getUsers() {
        return current_manager.getUser();
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
     * @param users      The users' names
     * @param pageNumber The page number which put the score
     * @param rank       The rank of the score should be put
     * @return Return the text display on the page.
     */
    private String getEmailText(List<User> users, int pageNumber, String rank) {
        if (users.size() < pageNumber || users.get(pageNumber - 1).returnBestScore() == null) {
            return "HOI! No one has score recorded here!";
        } else {
            return rank + "   Username: " + " \n" + users.get(pageNumber - 1).getUserEmail();
        }
    }

    /**
     * Display the format of the score
     *
     * @param users      The current user name
     * @param pageNumber The page which the score will display
     * @return Return the text of the score on the page
     */

    private String getScoreText(List<User> users, int pageNumber) {
        if (users.size() < pageNumber || users.get(pageNumber - 1).returnBestScore() == null) {
            return "";
        } else {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Timestamp ts = users.get(pageNumber - 1).returnBestScore().getTimeStamp();
            String gameComplexity;
            if (users.get(pageNumber - 1).returnBestScore().getComplexity() == 3) {
                gameComplexity = "Easy Mode";
            } else if (users.get(pageNumber - 1).returnBestScore().getComplexity() == 4) {
                gameComplexity = "Medium Mode";
            } else if (users.get(pageNumber - 1).returnBestScore().getComplexity() == 0) {
                gameComplexity = "";
            } else {
                gameComplexity = "Hard Mode";
            }
            return "Score: " + Integer.toString(users.get(pageNumber - 1).
                    returnBestScore().getFinalScore()) + "\n" + gameComplexity + "\n" +
                    "(Played at " + "\n" + sdf.format(ts) + ")";
        }
    }

    /**
     * Update the view on the scoreboard
     *
     * @param pageNumber The number of the page which we need to show it on
     */
    public void updateView(int pageNumber) {
        current_page.displayScoreboardText(getGameName());
        current_page.displayEmailText(getEmailText(getUsers(), pageNumber, getPosition(pageNumber)));
        current_page.displayScoreText(getScoreText(getUsers(), pageNumber));
    }
}
