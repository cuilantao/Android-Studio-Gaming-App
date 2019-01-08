package fall2018.csc2017.Scoreboard;
/*
Taken from https://www.youtube.com/watch?v=cKweRL0rHBc. The video demonstrate how to create a
swipe view using fragment. Class involved are FragmentPage, FragmentPageLocal, SwipeAdapter,
SwipeAdapterLocal, scoreboard.
 */

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import fall2018.csc2017.R;
import fall2018.csc2017.UserAndScore.UserManager;

/**
 * Class scoreboard which can display and show the local and global highest score
 */

public class scoreboard extends AppCompatActivity {

    Switch current_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(1);
        final SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        final SwipeAdapterLocal swipeAdapterLocal = new SwipeAdapterLocal(getSupportFragmentManager());
        current_switch = (Switch) findViewById(R.id.current_switch);
        current_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    ((TextView) current_switch).setText("Local");
                    viewPager.setAdapter(swipeAdapter);

                } else {
                    ((TextView) current_switch).setText("Global");
                    viewPager.setAdapter(swipeAdapterLocal);
                }
            }
        });
        viewPager.setAdapter(swipeAdapter);
        viewPager.setCurrentItem(0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Scoreboard");
        ((TextView) current_switch).setText("Local");
    }


}
