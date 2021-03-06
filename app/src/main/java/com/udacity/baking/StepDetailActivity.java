package com.udacity.baking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.udacity.baking.fragment.StepDetailFragment;
import com.udacity.baking.model.Step;

/**
 * Created by fabiano.alvarenga on 02/03/18.
 */

public class StepDetailActivity extends AppCompatActivity {

    private Step step;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        final Intent intent = getIntent();
        if (null != intent && intent.hasExtra(Step.class.getSimpleName())) {
            step = intent.getParcelableExtra(Step.class.getSimpleName());

            if (null != step) {

                this.setTitle(step.getId() + " - " + step.getShortDescription());

                final Bundle arguments = new Bundle();
                arguments.putParcelable(Step.class.getSimpleName(), step);

                final StepDetailFragment fragment = new StepDetailFragment();
                fragment.setArguments(arguments);

                final FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.container_step_detail, fragment)
                        .commit();

            }
        }
    }
}