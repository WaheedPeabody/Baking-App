package com.example.waheed.bakingapp.recipes;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.waheed.bakingapp.R;
import com.example.waheed.bakingapp.ui.recipes.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Tests for the recipes screen, the main screen which contains a list of recipes.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipesScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecipeItem_opensRecipeDetailsScreen() {
        // When click on an item in the recipes list
        onView(withId(R.id.recipesRecyclerView))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(0, ViewActions.click()));

        // Then recipe details screen is displayed
        onView(withId(R.id.recipeStepsRecyclerView)).check(matches(isDisplayed()));
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    public void unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(
                mainActivityActivityTestRule.getActivity().getCountingIdlingResource());
    }

    /**
     * Convenience method to register an IdlingResources with Espresso. IdlingResource resource is
     * a great way to tell Espresso when your app is in an idle state. This helps Espresso to
     * synchronize your test actions, which makes tests significantly more reliable.
     */
    @Before
    public void registerIdlingResource() {
        Espresso.registerIdlingResources(
                mainActivityActivityTestRule.getActivity().getCountingIdlingResource());
    }

}
