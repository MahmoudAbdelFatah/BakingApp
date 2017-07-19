package com.example.android.bakingapp;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Mahmoud on 7/19/2017.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(
            MainActivity.class);

    //check the displayed of the recycler view
    @Test
    public void RecipeRecyclerViewIsDisplayed(){
        onView(withId(R.id.recipe_recycler_view)).check(matches(isDisplayed()));
    }
}
