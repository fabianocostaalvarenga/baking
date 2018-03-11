package com.udacity.baking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.udacity.baking.adapter.OnItemClickListener;
import com.udacity.baking.fragment.RecipeDetailFragment;
import com.udacity.baking.fragment.StepDetailFragment;
import com.udacity.baking.model.Ingredient;
import com.udacity.baking.model.Recipe;
import com.udacity.baking.model.Step;
import com.udacity.baking.widget.IngredientListWidgetProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabiano.alvarenga on 27/02/18.
 */

public class RecipeDetailsActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        frameLayout = findViewById(R.id.container_recipe_details);

        Intent intent = getIntent();
        if(null != intent.getExtras()) {
            String title = intent.getStringExtra("TITLE");
            this.setTitle(title);

            final List<Ingredient> ingredients = intent.getParcelableArrayListExtra(Ingredient.class.getSimpleName());
            final List<Step> steps = intent.getParcelableArrayListExtra(Step.class.getSimpleName());

            recipe = new Recipe();
            recipe.setName(title);
            recipe.setIngredients(ingredients);
            recipe.setSteps(steps);

            final Bundle arguments = new Bundle();
            arguments.putParcelableArrayList(Ingredient.class.getSimpleName(), (ArrayList<? extends Parcelable>) ingredients);
            arguments.putParcelableArrayList(Step.class.getSimpleName(), (ArrayList<? extends Parcelable>) steps);

            final RecipeDetailFragment fragment = new RecipeDetailFragment(new OnItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    if(isTablet()) {
                        if(null != steps && !steps.isEmpty()) {
                            setStepDetailItemFragment(steps.get(position));
                        }
                    } else {
                        launchIntentStepDetailActivity(steps.get(position));
                    }
                }
            });

            fragment.setArguments(arguments);

            final FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.container_recipe_details, fragment)
                    .commit();

            if(isTablet()) {
                if(null != steps && !steps.isEmpty()) {
                    setStepDetailItemFragment(steps.get(0));
                }
            }

        }

    }

    private void setStepDetailItemFragment(final Step step) {
        final StepDetailFragment fragment = new StepDetailFragment();
        final Bundle arguments = new Bundle();
        arguments.putParcelable(Step.class.getSimpleName(), step);
        fragment.setArguments(arguments);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container_step_details, fragment)
                .commit();
    }

    private boolean isTablet() {
        return getResources().getBoolean(R.bool.is_tablet);
    }

    private void launchIntentStepDetailActivity(Step step) {
        Intent stepIntent = new Intent(this, StepDetailActivity.class);
        stepIntent.putExtra(Step.class.getSimpleName(), step);
        startActivity(stepIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = RecipeDetailsActivity.this;
        final Intent intent = getIntent();
        final int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_send_to_widget:
                IngredientListWidgetProvider.widgetsUpdate(context, recipe);
                Snackbar.make(frameLayout, getString(R.string.successful_send_to_widget), Snackbar.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
