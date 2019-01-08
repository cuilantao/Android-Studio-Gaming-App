package fall2018.csc2017.UserAndScore;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A singleton class UserManager that is responsible for managing all user activities.
 */
public class UserManager implements Serializable, Iterable<User> {
    /**
     * The single instance of UserManager.
     */
    private static UserManager singleInstance = null;
    /**
     * Current Login user.
     */
    private static User loginUser = null;
    /**
     * List of all registered users.
     */
    private List<User> currentUser = new ArrayList<>();

    /**
     * Make sure only one UserManager exists.
     *
     * @return null if no UserManager exists, or the current UserManager instance if there is one.
     */
    public static UserManager get_instance() {
        if (singleInstance == null) {
            singleInstance = new UserManager();
        }
        return singleInstance;
    }

    /**
     * Set the instance of UserManager.
     *
     * @param um: the instance to set.
     */
    public static void set_instance(UserManager um) {
        UserManager.singleInstance = um;
    }

    /**
     * Get all the user.
     *
     * @return: all the user registered.
     */
    public static List<User> getUser() {
        return UserManager.get_instance().currentUser;
    }

    /**
     * Get login user
     *
     * @return: return the current login user
     */
    public static User getLoginUser() {
        return loginUser;
    }

    /**
     * Set login user
     *
     * @param user: the login user.
     */
    public static void setLoginUser(User user) {
        loginUser = user;
    }

    public Iterator<User> iterator() {
        return new UserIterator();
    }

    public void add(User new_user) {
        currentUser.add(new_user);
    }

    public void switch_game(String game) {
        for (User u : currentUser) {
            u.switch_game(game);
        }
    }

    private class UserIterator implements Iterator<User> {
        private int curIndex = 0;

        @Override
        public boolean hasNext() {
            return curIndex < currentUser.size();
        }

        @Override
        public User next() {

            User result = currentUser.get(curIndex);
            curIndex++;
            return result;
        }
    }

}
