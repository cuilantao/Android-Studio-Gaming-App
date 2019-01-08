package fall2018.csc2017.Scoreboard;
/*
Taken from https://www.youtube.com/watch?v=cKweRL0rHBc. The video demonstrate how to create a
swipe view using fragment. Class involved are FragmentPage, FragmentPageLocal, SwipeAdapter,
SwipeAdapterLocal, scoreboard.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import fall2018.csc2017.UserAndScore.UserManager;

public class SwipeAdapterLocal extends FragmentStatePagerAdapter {

    public SwipeAdapterLocal(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment pageFragment = new FragmentPageLocal();
        ScoreboardControllerLocal controllerLocal = new ScoreboardControllerLocal(UserManager.getLoginUser(), (FragmentPageLocal) pageFragment);
        Bundle bundle = new Bundle();
        bundle.putInt("pageNumber", position + 1);
        controllerLocal.updateView(bundle.getInt("pageNumber"));
        pageFragment.setArguments(bundle);

        return pageFragment;
    }

    @Override
    public int getCount() {
        return 10;
    }
}
