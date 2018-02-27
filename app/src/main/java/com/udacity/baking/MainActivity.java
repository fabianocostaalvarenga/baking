package com.udacity.baking;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.udacity.baking.adapter.RecipeAdapter;
import com.udacity.baking.databinding.ActivityMainBinding;
import com.udacity.baking.model.Recipe;
import com.udacity.baking.tasks.AsyncTaskDelegate;
import com.udacity.baking.tasks.BakingAsyncTasks;
import com.udacity.baking.utils.NetWorkUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AsyncTaskDelegate<List<Recipe>> {

    private ActivityMainBinding activityMainBinding;

    private List<Recipe> recipes;
    private RecyclerView rvMain;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        rvMain = activityMainBinding.rvMain;

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvMain.setLayoutManager(layout);

        executeRequest();

    }

    @Override
    public void onPostProcess(List<Recipe> entity) {
        this.recipes = entity;
        rvMain.setAdapter(new RecipeAdapter(this, entity));

        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onPreProcess() {
        if(progressDialog != null) {
            progressDialog.cancel();
        }

        progressDialog = ProgressDialog.show(this, "", getString(R.string.please_wait), true, true);
    }

    private void executeRequest() {

        if(NetWorkUtils.isOnline(this)) {
            new BakingAsyncTasks(this).execute();
        } else {
            View view = findViewById(R.id.activity_main);
            Snackbar snackbar =
                    Snackbar.make(view, getString(R.string.no_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.setAction(getString(R.string.label_retry), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    executeRequest();
                }
            });
            snackbar.show();
        }
    }
}
