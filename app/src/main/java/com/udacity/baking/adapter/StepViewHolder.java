package com.udacity.baking.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.udacity.baking.R;

/**
 * Created by fabiano.alvarenga on 24/02/18.
 */

public class StepViewHolder extends RecyclerView.ViewHolder {

    final TextView shortDescription;

    public StepViewHolder(View itemView,  @NonNull final OnItemClickListener clickListener) {
        super(itemView);
        this.shortDescription = (TextView) itemView.findViewById(R.id.tv_short_description);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v, getPosition());
            }
        });
    }

}
