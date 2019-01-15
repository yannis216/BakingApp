package com.example.android.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.Activities.RecipeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class RecipesActivityClickTest {

    @Rule
    public ActivityTestRule<RecipeActivity> mActivityRule =
            new ActivityTestRule<>(RecipeActivity.class);

    @Test
    public void ensureCardClickWorks() {
        // Type text and then press the button.
        onView(withId(R.id.rv_recipecards))
                .perform(click());

        // Check that the text was changed.
        onView(withId(R.id.tv_ingredients_header)).check(matches(isDisplayed()));
    }

}

