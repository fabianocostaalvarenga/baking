package com.udacity.baking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.baking.R;
import com.udacity.baking.model.Step;

import java.util.List;

/**
 * Created by fabiano.alvarenga on 24/02/18.
 */

public class StepAdapter extends RecyclerView.Adapter {

    private Context ctx;
    private List<Step> list;
    private OnItemClickListener listener;

    public StepAdapter(Context ctx, List<Step> list, OnItemClickListener listener) {
        this.ctx = ctx;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(this.ctx).inflate(R.layout.rv_step_item, parent, false);

        StepViewHolder stepViewHolder = new StepViewHolder(view, listener);

        return stepViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StepViewHolder stepViewHolder = (StepViewHolder) holder;

        if(null != list) {
            Step step = list.get(position);

            String stepText = step.getId() +" - "+ step.getShortDescription();
            stepViewHolder.shortDescription.setText(stepText);

        }
    }

    @Override
    public int getItemCount() {

        if(list == null || list.isEmpty()) { return 0; }

        return list.size();
    }

}
