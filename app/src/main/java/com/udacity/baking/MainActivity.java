package com.udacity.baking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.udacity.baking.adapter.OnItemClickListener;
import com.udacity.baking.adapter.RecipeAdapter;
import com.udacity.baking.databinding.ActivityMainBinding;
import com.udacity.baking.model.Ingredient;
import com.udacity.baking.model.Recipe;
import com.udacity.baking.model.Step;
import com.udacity.baking.tasks.AsyncTaskDelegate;
import com.udacity.baking.tasks.BakingAsyncTasks;
import com.udacity.baking.utils.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AsyncTaskDelegate<List<Recipe>> {

    private ActivityMainBinding activityMainBinding;

    private List<Recipe> recipes;
    private RecyclerView rvMain;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        rvMain = activityMainBinding.rvMain;

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvMain.setLayoutManager(layout);

        executeRequest();

    }

    @Override
    public void onPostProcess(List<Recipe> entity) {
        this.recipes = entity;

        rvMain.setAdapter(new RecipeAdapter(this, entity, new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                launchIngredientIntentActivity(position);
            }
        }));

        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onPreProcess() {
        if(progressDialog != null) {
            progressDialog.cancel();
        }

        progressDialog = ProgressDialog.show(this, "", getString(R.string.please_wait), true, true);
    }

    private void executeRequest() {

        if(NetWorkUtils.isOnline(this)) {
            new BakingAsyncTasks(this).execute();
        } else {
            View view = findViewById(R.id.activity_main);
            Snackbar snackbar =
                    Snackbar.make(view, getString(R.string.no_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.setAction(getString(R.string.label_retry), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    executeRequest();
                }
            });
            snackbar.show();
        }
    }

    private void launchIngredientIntentActivity(int adapterPosition) {
        Intent ingredientIntent = new Intent(this, RecipeDetailsActivity.class);
        Recipe recipe = recipes.get(adapterPosition);
        List<Ingredient> ingredients = this.recipes.get(adapterPosition).getIngredients();
        List<Step> steps = this.recipes.get(adapterPosition).getSteps();
        ingredientIntent.putParcelableArrayListExtra(Ingredient.class.getSimpleName(), (ArrayList<? extends Parcelable>) ingredients);
        ingredientIntent.putParcelableArrayListExtra(Step.class.getSimpleName(), (ArrayList<? extends Parcelable>) steps);
        ingredientIntent.putExtra("TITLE", recipe.getName() +" - "+ recipe.getServings() +" "+this.getString(R.string.label_servings));
        startActivity(ingredientIntent);
    }
}
