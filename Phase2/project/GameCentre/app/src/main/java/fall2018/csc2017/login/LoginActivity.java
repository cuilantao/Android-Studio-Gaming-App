package fall2018.csc2017.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall2018.csc2017.R;
import fall2018.csc2017.UserAndScore.User;
import fall2018.csc2017.UserAndScore.UserManager;
import fall2018.csc2017.slidingtiles.GameChoose;

/**
 * A login screen that offers login via email/password.
 * https://www.youtube.com/watch?v=-8QrtXkfF9A
 */
public class LoginActivity extends AppCompatActivity {


    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private UserManager current_manager = UserManager.get_instance();
    private boolean newuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_gradbk);

        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attemptLogin()) {
                    game_menu();
                }
            }
        });

        Button mEmailSignUpButton = (Button) findViewById(R.id.email_sign_up_button);
        mEmailSignUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignUp();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    //Switch to Game menu
    private void game_menu() {
        if (newuser) {
            Intent tmp = new Intent(this, WelcomeActivity.class);
            startActivity(tmp);
        } else {
            Intent tmp = new Intent(this, GameChoose.class);
            startActivity(tmp);
        }
    }

    private void attemptSignUp() {
        deserialize();
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.w
        /**
         * Get the password and email.
         */
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            /**
             * Check if the Email is valid
             */
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        } else if (isEmailRegistered(email)) {
            /**
             * Check if the email is registered
             */
            mEmailView.setError(getString(R.string.error_register_email));
            focusView = mEmailView;
            cancel = true;
            System.out.println(cancel);
        } else if (password.length() <= 4) {
            /**
             * Check if the password is valid.
             */
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            /**
             * Store the user and user's password and email inside UserManager
             * and go back to log in activity class.
             */
            User new_user = new User(email, password);
            current_manager.add(new_user);
            current_manager.setLoginUser(new_user);
            newuser = true;
            serializeUserManager();
            showProgress(true);
            game_menu();
        }
    }

    //Store new user information
    private void serializeUserManager() {
        /**
         * Save the information inside the UserManager.
         */
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput("UserManager.ser", MODE_PRIVATE));
            outputStream.writeObject(current_manager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    //     Attempts to sign in or register the account specified by the login form.
    //     If there are form errors (invalid email, missing fields, etc.), the
    //     errors are presented and no actual login attempt is made.
    private boolean attemptLogin() {
        newuser = false;
        deserialize();
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailRegistered(email)) {
            mEmailView.setError("This email hasn't been registered yet, please click on the sign up button to sign up!");
            focusView = mEmailView;
            cancel = true;
        } else if (!correct_password(email, password)) {
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            for (User T : current_manager) {
                if (T.getUserEmail().equals(email)) {
                    UserManager.setLoginUser(T);
                }
            }
            showProgress(true);
        }
        return !cancel;
    }

    /**
     * Check if the password is correct
     *
     * @param email:    the email that needs to be checked
     * @param password: the password that needs to be checked
     * @return: return if the given email and password match the record in user manager.
     */
    private boolean correct_password(String email, String password) {
        for (User T : current_manager) {
            if (T.getUserEmail().equals(email)) {
                if (T.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * To check if the email is registered
     *
     * @param email: the given email
     * @return: true iff the email is already registered.
     */
    private boolean isEmailRegistered(String email) {
        for (User T : current_manager) {
            if (T.getUserEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    /**
     * load UserManager instance from UserManager.ser
     */
    private void deserialize() {

        try {
            InputStream inputStream = this.openFileInput("UserManager.ser");
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                UserManager.set_instance((UserManager) input.readObject());
                current_manager = UserManager.get_instance();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * To check if it's a valid email
     *
     * @param email: given email
     * @return: true if the email is valid.
     */
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

