package cse110group4.devnet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MakePostInstrumentedTest {
    private String ProjectTitle;
    private String Deadline;
    private String Payment;
    private String ShortDesc;
    private String LongDesc;

    @Rule
    public ActivityTestRule<MakePost> mActivityRule =
            new ActivityTestRule<>(MakePost.class);

    // Add instrumentation test here

    @Before
    public void initValidStrings() {
        ProjectTitle = "Title of New Project";
        Deadline = "12/31/2016";
        Payment = "900";
        ShortDesc = "This is the short description.";
        LongDesc = "This is the long description. This is longer.";
    }


    @Test
    public void enterTextAndCheck() {
        // Type text and then press the button.
        onView(withId(R.id.project_title))
                .perform(typeText(ProjectTitle), closeSoftKeyboard());
        onView(withId(R.id.project_title))
                .perform(typeText(Deadline), closeSoftKeyboard());
        onView(withId(R.id.project_title))
                .perform(typeText(Payment), closeSoftKeyboard());
        onView(withId(R.id.project_title))
                .perform(typeText(ShortDesc), closeSoftKeyboard());
        onView(withId(R.id.project_title))
                .perform(typeText(LongDesc), closeSoftKeyboard());

        onView(withId(R.id.makePostFab)).perform(click());

        // Check that the post was posted (on recyclerView).
        onView(withId(R.id.homeSwipeLayout)).check(matches(withText(ProjectTitle)));
    }
}
