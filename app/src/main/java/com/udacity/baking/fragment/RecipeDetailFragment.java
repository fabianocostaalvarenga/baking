package com.udacity.baking.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.baking.R;
import com.udacity.baking.model.Step;

/**
 * Created by fabiano.alvarenga on 04/03/18.
 */

public class RecipeDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView =
                inflater.inflate(R.layout.activity_main, container, false);

        final Bundle arguments = getArguments();
        final Step step = (Step) arguments.getParcelable(Step.class.getSimpleName());


        return rootView;

    }

}
