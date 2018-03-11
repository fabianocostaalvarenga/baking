package com.udacity.baking.service;

import android.content.Context;
import android.util.Log;

import com.bluelinelabs.logansquare.LoganSquare;
import com.udacity.baking.R;
import com.udacity.baking.model.Recipe;
import com.udacity.baking.utils.NetWorkUtils;

import java.util.List;

/**
 * Created by fabiano.alvarenga on 26/02/18.
 */

public class BakingService {

    private static final String TAG = BakingService.class.getSimpleName();

    private String urlApi;

    public BakingService(Context context) {
        this.urlApi = context.getString(R.string.url_api);
    }

    public List<Recipe> getRecipes() {
        List<Recipe> recipes = null;
        try {
            recipes = LoganSquare.parseList(NetWorkUtils.launchRequest(urlApi), Recipe.class);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return recipes;
    }

}
