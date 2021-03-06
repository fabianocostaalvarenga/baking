package com.udacity.baking.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.udacity.baking.R;

/**
 * Created by fabiano.alvarenga on 24/02/18.
 */

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    final TextView title;
    final TextView subTitle;

    public RecipeViewHolder(View itemView, @NonNull final OnItemClickListener clickListener) {
        super(itemView);
        this.title = (TextView) itemView.findViewById(R.id.tv_recipe);
        this.subTitle = (TextView) itemView.findViewById(R.id.sub_title);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v, getPosition());
            }
        });
    }
}
