package com.udacity.baking;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.udacity.baking.adapter.IngredientAdapter;
import com.udacity.baking.adapter.OnItemClickListener;
import com.udacity.baking.adapter.StepAdapter;
import com.udacity.baking.databinding.RvContainerBinding;
import com.udacity.baking.model.Ingredient;
import com.udacity.baking.model.Step;

import java.util.List;

import static com.udacity.baking.R.layout.rv_container;

/**
 * Created by fabiano.alvarenga on 27/02/18.
 */

public class RecipeDetailsActivity extends AppCompatActivity {

    private RvContainerBinding rvContainerBinding;

    private RecyclerView rvIngredients;
    private RecyclerView rvSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(rv_container);

        rvContainerBinding = DataBindingUtil.setContentView(this, rv_container);

        Intent intent = getIntent();
        if(null != intent.getExtras()) {
            String title = intent.getStringExtra("TITLE");

            List<Ingredient> ingredients = intent.getParcelableArrayListExtra(Ingredient.class.getSimpleName());
            makeIngredientsRecycleView(ingredients);

            List<Step> steps = intent.getParcelableArrayListExtra(Step.class.getSimpleName());
            makeStepsRecycleView(steps);

            this.setTitle(title);
        }

    }

    private void makeIngredientsRecycleView(List<Ingredient> ingredients) {
        rvIngredients = rvContainerBinding.rvIngredients;
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvIngredients.setLayoutManager(layout);
        rvIngredients.setAdapter(new IngredientAdapter(this, ingredients));
    }

    private void makeStepsRecycleView(final List<Step> steps) {
        rvSteps = rvContainerBinding.rvSteps;
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvSteps.setLayoutManager(layout);
        rvSteps.setAdapter(new StepAdapter(this, steps, new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                launchIntentStepDetailActivity(steps.get(position));
            }
        }));
    }

    private void launchIntentStepDetailActivity(Step step) {
        Intent stepIntent = new Intent(this, StepDetailActivity.class);
        stepIntent.putExtra(Step.class.getSimpleName(), step);
        startActivity(stepIntent);
    }

}
