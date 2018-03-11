package com.udacity.baking;


import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.udacity.baking.model.Ingredient;
import com.udacity.baking.model.Step;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Rule
    public IntentsTestRule<RecipeDetailsActivity> mRecipeDetailsActivityTestRule =
            new IntentsTestRule<RecipeDetailsActivity>(RecipeDetailsActivity.class);

    @Test
    public void clickOnRecipeListItem_opensRecipeDetailsActivity() throws Exception {

        onView(withId(R.id.rv_main))
                .perform(actionOnItemAtPosition(0, click()));

        intended(
                allOf(hasComponent(RecipeDetailsActivity.class.getCanonicalName()),
                        hasExtraWithKey(Ingredient.class.getSimpleName())
                )
        );

        intended(
                allOf(hasComponent(RecipeDetailsActivity.class.getCanonicalName()),
                        hasExtraWithKey(Step.class.getSimpleName())
                )
        );

    }
}
