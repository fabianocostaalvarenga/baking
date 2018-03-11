package com.udacity.baking.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.udacity.baking.R;
import com.udacity.baking.model.Ingredient;
import com.udacity.baking.model.Recipe;

import java.util.List;

public class IngredientListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private Context context = null;
    private List<Ingredient> ingredients = null;

    public IngredientListRemoteViewsFactory(final Context context) {
        this.context = context;
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this.context);
        getIngredients(sharedPreferences);
    }

    @Override
    public void onCreate() {
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public int getCount() {
        return (null == ingredients) ? 0 : ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.rv_ingredient_item);
        final Ingredient ingredient = ingredients.get(position);
        final String ingredientNameText = ingredient.getIngredient();
        final String ingredientText = ingredient.getQuantity() +" - "+  ingredient.getMeasure();
        rv.setTextViewText(R.id.tv_ingredient, ingredientNameText);
        rv.setTextViewText(R.id.tv_quantity_measure, ingredientText);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences,
                                          final String key) {
        if (key.equals(context.getResources().getString(R.string.key_widget_value))) {
            getIngredients(sharedPreferences);
        }
    }

    private void getIngredients(final SharedPreferences sharedPreferences) {
        final String json = sharedPreferences.getString(context.getResources().getString(R.string.key_widget_value),null);
        final Recipe recipe = (null == json) ? null : new Gson().fromJson(json, Recipe.class);
        if (null != recipe) {
            ingredients = recipe.getIngredients();
            onDataSetChanged();
        }
    }

}
