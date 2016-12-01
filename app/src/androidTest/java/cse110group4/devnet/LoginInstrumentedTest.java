package cse110group4.devnet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.app.Activity;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginInstrumentedTest {

    private String email;
    private String password;
    @Rule
    public ActivityTestRule<LoginScreen> mActivityRule =
            new ActivityTestRule<>(LoginScreen.class);

    @Before
    public void initValidStrings() {
        email = "vincent@dev.net";
        password = "1234qwer";
    }

    @Test
    public void enterCredentialsAndCheck() {
        // Hit the login button to go to log in page
        onView(withId(R.id.button2)).perform(click());

        // Type in Credentials
        onView(withId(R.id.email))
                .perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(password), closeSoftKeyboard());

        // Hit the sign in button
        onView(withId(R.id.email_sign_in_button)).perform(click());

        // Check that the user is logged in (on the home page).
            intended(hasComponent(RVAdapter.class.getName()));
        }
}
