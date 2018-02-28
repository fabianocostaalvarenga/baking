package com.udacity.baking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.baking.R;
import com.udacity.baking.model.Recipe;

import java.util.List;

/**
 * Created by fabiano.alvarenga on 24/02/18.
 */

public class RecipeAdapter extends RecyclerView.Adapter {

    private Context ctx;
    private List<Recipe> list;
    private OnItemClickListener listener;

    public RecipeAdapter(Context ctx, List<Recipe> list, OnItemClickListener listener) {
        this.ctx = ctx;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(this.ctx).inflate(R.layout.activity_recipe, parent, false);

        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view, listener);

        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecipeViewHolder recipeViewHolder = (RecipeViewHolder) holder;

        if(null != list) {
            Recipe recipe = list.get(position);

            recipeViewHolder.title.setText(recipe.getName());
            recipeViewHolder.subTitle.setText(String.format("%1$,.2f " + ctx.getString(R.string.label_servings), recipe.getServings()));
        }
    }

    @Override
    public int getItemCount() {

        if(list == null || list.isEmpty()) { return 0; }

        return list.size();
    }

}
