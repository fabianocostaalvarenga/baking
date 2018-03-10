package com.udacity.baking.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.baking.R;
import com.udacity.baking.adapter.IngredientAdapter;
import com.udacity.baking.adapter.OnItemClickListener;
import com.udacity.baking.adapter.StepAdapter;
import com.udacity.baking.model.Ingredient;
import com.udacity.baking.model.Step;

import java.util.List;

/**
 * Created by fabiano.alvarenga on 04/03/18.
 */

@SuppressLint("ValidFragment")
public class RecipeDetailFragment extends Fragment {

    private RecyclerView rvIngredients;
    private RecyclerView rvSteps;
    private OnItemClickListener onItemClickListener;

    public RecipeDetailFragment() {}

    public RecipeDetailFragment(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView =
                inflater.inflate(R.layout.fragment_recipe_details, container, false);

        rvIngredients = (RecyclerView) rootView.findViewById(R.id.rv_ingredients);
        rvSteps = (RecyclerView) rootView.findViewById(R.id.rv_steps);

        final Bundle arguments = getArguments();
        List<Ingredient> ingredients = null;
        List<Step> steps = null;
        
        if(null != arguments) {
            ingredients = (List<Ingredient>) arguments.get(Ingredient.class.getSimpleName());
            steps = (List<Step>) arguments.get(Step.class.getSimpleName());
        }
        
        if(null != ingredients) {
            makeIngredientsRecycleView(ingredients);
        }
        
        if(null != steps) {
            makeStepsRecycleView(steps);
        }

        return rootView;

    }

    private void makeIngredientsRecycleView(List<Ingredient> ingredients) {
        RecyclerView.LayoutManager layout =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvIngredients.setLayoutManager(layout);
        rvIngredients.setAdapter(new IngredientAdapter(getContext(), ingredients));
    }

    private void makeStepsRecycleView(final List<Step> steps) {
        RecyclerView.LayoutManager layout =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvSteps.setLayoutManager(layout);
        rvSteps.setAdapter(new StepAdapter(getContext(), steps, onItemClickListener));
    }

}
