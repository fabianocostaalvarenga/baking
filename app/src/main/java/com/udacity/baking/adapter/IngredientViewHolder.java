package com.udacity.baking.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.udacity.baking.R;

/**
 * Created by fabiano.alvarenga on 24/02/18.
 */

public class IngredientViewHolder extends RecyclerView.ViewHolder {

    final TextView ingredient;
    final TextView quantityAndMeasure;

    public IngredientViewHolder(View itemView) {
        super(itemView);
        this.ingredient = (TextView) itemView.findViewById(R.id.tv_ingredient);
        this.quantityAndMeasure = (TextView) itemView.findViewById(R.id.tv_quantity_measure);
    }

}
