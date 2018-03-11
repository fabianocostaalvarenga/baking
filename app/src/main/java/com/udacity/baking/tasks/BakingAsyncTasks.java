package com.udacity.baking.tasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.udacity.baking.model.Recipe;
import com.udacity.baking.service.BakingService;

import java.util.List;

/**
 * Created by fabiano.alvarenga on 26/02/18.
 */

public class BakingAsyncTasks extends AsyncTask<Void, Long, List<Recipe>> {

    private AsyncTaskDelegate taskDelegate;
    private Context ctx;

    public BakingAsyncTasks(AsyncTaskDelegate taskDelegate) {
        this.taskDelegate = taskDelegate;
        ctx = ((Activity)taskDelegate).getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(null != taskDelegate) {
            taskDelegate.onPreProcess();
        }
    }

    @Override
    protected List<Recipe> doInBackground(Void... params) {
        List<Recipe> recipes = new BakingService(ctx).getRecipes();
        return recipes;
    }

    @Override
    protected void onPostExecute(List<Recipe> recipes) {
        super.onPostExecute(recipes);
        if(null != taskDelegate) {
            taskDelegate.onPostProcess(recipes);
        }
    }

}
