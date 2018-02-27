package com.udacity.baking.tasks;

/**
 * Created by fabiano.alvarenga on 27/02/18.
 */

public interface AsyncTaskDelegate<T> {
    public void onPostProcess(T entity);
    public void onPreProcess();
}
