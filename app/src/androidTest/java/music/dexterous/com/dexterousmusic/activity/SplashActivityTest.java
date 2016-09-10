package music.dexterous.com.dexterousmusic.activity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SplashActivityTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void splashActivityTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(music.dexterous.com.dexterousmusic.R.id.give_permission), withText("Give Permission"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(music.dexterous.com.dexterousmusic.R.id.give_permission), withText("Give Permission"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(music.dexterous.com.dexterousmusic.R.id.shuffel_all_songs_fab), isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(music.dexterous.com.dexterousmusic.R.id.toggle_upper),
                        withParent(withId(music.dexterous.com.dexterousmusic.R.id.bottom_bar)),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(music.dexterous.com.dexterousmusic.R.id.widget_repeate),
                        withParent(allOf(withId(music.dexterous.com.dexterousmusic.R.id.control_music_widget_lower_id),
                                withParent(withId(music.dexterous.com.dexterousmusic.R.id.now_plaiing_innerview)))),
                        isDisplayed()));
        appCompatImageView2.perform(click());

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(music.dexterous.com.dexterousmusic.R.id.widget_repeate),
                        withParent(allOf(withId(music.dexterous.com.dexterousmusic.R.id.control_music_widget_lower_id),
                                withParent(withId(music.dexterous.com.dexterousmusic.R.id.now_plaiing_innerview)))),
                        isDisplayed()));
        appCompatImageView3.perform(click());

        ViewInteraction appCompatImageView4 = onView(
                allOf(withId(music.dexterous.com.dexterousmusic.R.id.widget_repeate),
                        withParent(allOf(withId(music.dexterous.com.dexterousmusic.R.id.control_music_widget_lower_id),
                                withParent(withId(music.dexterous.com.dexterousmusic.R.id.now_plaiing_innerview)))),
                        isDisplayed()));
        appCompatImageView4.perform(click());

        ViewInteraction appCompatImageView5 = onView(
                allOf(withId(music.dexterous.com.dexterousmusic.R.id.widget_repeate),
                        withParent(allOf(withId(music.dexterous.com.dexterousmusic.R.id.control_music_widget_lower_id),
                                withParent(withId(music.dexterous.com.dexterousmusic.R.id.now_plaiing_innerview)))),
                        isDisplayed()));
        appCompatImageView5.perform(click());

        ViewInteraction appCompatImageView6 = onView(
                allOf(withId(music.dexterous.com.dexterousmusic.R.id.widget_repeate),
                        withParent(allOf(withId(music.dexterous.com.dexterousmusic.R.id.control_music_widget_lower_id),
                                withParent(withId(music.dexterous.com.dexterousmusic.R.id.now_plaiing_innerview)))),
                        isDisplayed()));
        appCompatImageView6.perform(click());

        ViewInteraction appCompatImageView7 = onView(
                allOf(withId(music.dexterous.com.dexterousmusic.R.id.widget_shuffel),
                        withParent(allOf(withId(music.dexterous.com.dexterousmusic.R.id.control_music_widget_lower_id),
                                withParent(withId(music.dexterous.com.dexterousmusic.R.id.now_plaiing_innerview)))),
                        isDisplayed()));
        appCompatImageView7.perform(click());

        ViewInteraction appCompatImageView8 = onView(
                allOf(withId(music.dexterous.com.dexterousmusic.R.id.widget_toggle),
                        withParent(allOf(withId(music.dexterous.com.dexterousmusic.R.id.control_music_widget_id),
                                withParent(withId(music.dexterous.com.dexterousmusic.R.id.now_plaiing_innerview)))),
                        isDisplayed()));
        appCompatImageView8.perform(click());

        ViewInteraction appCompatImageView9 = onView(
                allOf(withId(music.dexterous.com.dexterousmusic.R.id.widget_toggle),
                        withParent(allOf(withId(music.dexterous.com.dexterousmusic.R.id.control_music_widget_id),
                                withParent(withId(music.dexterous.com.dexterousmusic.R.id.now_plaiing_innerview)))),
                        isDisplayed()));
        appCompatImageView9.perform(click());

        ViewInteraction appCompatImageView10 = onView(
                allOf(withId(music.dexterous.com.dexterousmusic.R.id.widget_toggle),
                        withParent(allOf(withId(music.dexterous.com.dexterousmusic.R.id.control_music_widget_id),
                                withParent(withId(music.dexterous.com.dexterousmusic.R.id.now_plaiing_innerview)))),
                        isDisplayed()));
        appCompatImageView10.perform(click());

        ViewInteraction appCompatImageView11 = onView(
                allOf(withId(music.dexterous.com.dexterousmusic.R.id.widget_toggle),
                        withParent(allOf(withId(music.dexterous.com.dexterousmusic.R.id.control_music_widget_id),
                                withParent(withId(music.dexterous.com.dexterousmusic.R.id.now_plaiing_innerview)))),
                        isDisplayed()));
        appCompatImageView11.perform(click());

        ViewInteraction appCompatImageView12 = onView(
                allOf(withId(music.dexterous.com.dexterousmusic.R.id.toggle_upper),
                        withParent(withId(music.dexterous.com.dexterousmusic.R.id.bottom_bar)),
                        isDisplayed()));
        appCompatImageView12.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(music.dexterous.com.dexterousmusic.R.id.my_recycler_view),
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                        0)),
                        2),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
