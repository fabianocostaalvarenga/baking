package com.udacity.baking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.baking.R;
import com.udacity.baking.model.Ingredient;

import java.util.List;

/**
 * Created by fabiano.alvarenga on 24/02/18.
 */

public class IngredientAdapter extends RecyclerView.Adapter {

    private Context ctx;
    private List<Ingredient> list;

    public IngredientAdapter(Context ctx, List<Ingredient> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(this.ctx).inflate(R.layout.rv_ingredients_item, parent, false);

        IngredientViewHolder ingredientViewHolder = new IngredientViewHolder(view);

        return ingredientViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        IngredientViewHolder ingredientViewHolder = (IngredientViewHolder) holder;

        if(null != list) {
            Ingredient ingredient = list.get(position);

            ingredientViewHolder.ingredient.setText(ingredient.getIngredient());
            ingredientViewHolder.quantityAndMeasure.setText(String.format("%1$,.2f " + ingredient.getMeasure(), ingredient.getQuantity()));

        }
    }

    @Override
    public int getItemCount() {

        if(list == null || list.isEmpty()) { return 0; }

        return list.size();
    }

}
