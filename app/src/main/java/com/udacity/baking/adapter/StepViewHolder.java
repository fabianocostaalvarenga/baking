package com.udacity.baking.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.udacity.baking.R;

/**
 * Created by fabiano.alvarenga on 24/02/18.
 */

public class StepViewHolder extends RecyclerView.ViewHolder {

    final TextView shortDescription;
    final TextView description;

    public StepViewHolder(View itemView) {
        super(itemView);
        this.shortDescription = (TextView) itemView.findViewById(R.id.tv_short_description);
        this.description = (TextView) itemView.findViewById(R.id.tv_description);
    }

}
