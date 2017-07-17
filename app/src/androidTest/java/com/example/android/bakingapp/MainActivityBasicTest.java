package com.example.android.bakingapp;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Mahmoud on 7/17/2017.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityBasicTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recipe_recycler_view),
                        withParent(allOf(withId(R.id.recipe_container),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction viewInteraction = onView(
                allOf(withId(R.id.next), withText("Next"), isDisplayed()));
        viewInteraction.perform(click());

        ViewInteraction viewInteraction1 = onView(
                allOf(withId(R.id.prev), withText("Prev"), isDisplayed()));
        viewInteraction1.perform(click());
        pressBack();
    }
}
