package fall2018.csc2017.Scoreboard;
/*
Taken from https://www.youtube.com/watch?v=cKweRL0rHBc. The video demonstrate how to create a
swipe view using fragment. Class involved are FragmentPage, FragmentPageLocal, SwipeAdapter,
SwipeAdapterLocal, scoreboard.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.R;
import fall2018.csc2017.UserAndScore.User;
import fall2018.csc2017.UserAndScore.UserManager;
import fall2018.csc2017.slidingtiles.GameChoose;


public class FragmentPage extends Fragment {


    TextView scoreBoard;
    TextView email;
    TextView score;

    String scoreboardString;
    String emailString;
    String scoreString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view;
        view = inflater.inflate(R.layout.page_fragment_layout, container, false);

        scoreBoard = (TextView) view.findViewById(R.id.BoardName);
        email = (TextView) view.findViewById(R.id.UserEmail);
        score = (TextView) view.findViewById(R.id.Score);
        scoreBoard.setText(scoreboardString);
        email.setText(emailString);
        score.setText(scoreString);

        return view;

    }


    /**
     * Display the score on the scoreboard
     *
     * @param text The text of the scoreboard
     */
    public void displayScoreboardText(String text) {
        scoreboardString = text;
    }

    /**
     * Display the user info on the scoreboard
     *
     * @param text the user info
     */
    public void displayEmailText(String text) {
        emailString = text;
    }

    /**
     * Display the score on the scoreboard
     *
     * @param text score
     */
    public void displayScoreText(String text) {
        scoreString = text;
    }
}
