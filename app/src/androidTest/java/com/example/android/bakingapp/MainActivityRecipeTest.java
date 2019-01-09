package com.example.android.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.base.IdlingResourceRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.example.android.bakingapp.Data.Recipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityRecipeTest {


    @Rule public IntentsTestRule<MainActivity> mIntentTestRule = new IntentsTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before public void stubAllExternalIntents(){
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK,null));
    }
    @Before public void registerIdlingResource(){
        mIdlingResource = mIntentTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);


    }

    @Test
    public void clickRecipe_GoToRecipeDetails(){
        onView(withId(R.id.recipe_recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        intended(hasComponent(RecipeDetailsActivity.class.getName()));
    }

    @Test
    public void clickRecipe_sendRecipeObject(){
        onView(withId(R.id.recipe_recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        intended(hasExtraWithKey("Recipe"));
    }

    @After
    public void unregisterIdlingResource(){
        if (mIdlingResource != null){
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}
