package com.udacity.baking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.udacity.baking.adapter.OnItemClickListener;
import com.udacity.baking.fragment.RecipeDetailFragment;
import com.udacity.baking.fragment.StepDetailFragment;
import com.udacity.baking.model.Ingredient;
import com.udacity.baking.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabiano.alvarenga on 27/02/18.
 */

public class RecipeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        Intent intent = getIntent();
        if(null != intent.getExtras()) {
            String title = intent.getStringExtra("TITLE");
            this.setTitle(title);

            final List<Ingredient> ingredients = intent.getParcelableArrayListExtra(Ingredient.class.getSimpleName());
            final List<Step> steps = intent.getParcelableArrayListExtra(Step.class.getSimpleName());

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
}
