package com.udacity.baking.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.udacity.baking.R;
import com.udacity.baking.model.Recipe;

public class IngredientListWidgetProvider extends AppWidgetProvider {

    public static void widgetsUpdate(final Context context, final Recipe recipe) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final String json = new Gson().toJson(recipe);
        sharedPreferences.edit().putString(context.getResources().getString(R.string.key_widget_value), json).apply();
        final Class<IngredientListWidgetProvider> widgetProviderClass
                = IngredientListWidgetProvider.class;
        final Intent intent = new Intent(context, widgetProviderClass);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        final int[] appWidgetIds = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(new ComponentName(context, widgetProviderClass));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        context.sendBroadcast(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            final AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
            final ComponentName componentName =
                    new ComponentName(context, IngredientListWidgetProvider.class);
            widgetManager.notifyAppWidgetViewDataChanged(
                    widgetManager.getAppWidgetIds(componentName), R.id.lv_widget_ingredients);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(final Context context,
                         final AppWidgetManager appWidgetManager,
                         final int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            final Recipe recipe = getRecipe(context);
            final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient_list);
            if (null != recipe) {
                showIngredientList(views, context, appWidgetId, recipe);
            } else {
                showEmptyMessage(views);
            }
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void showIngredientList(final RemoteViews views,
                                    final Context context,
                                    final int appWidgetId,
                                    final Recipe recipe) {
        views.setViewVisibility(R.id.ll_empty_data, View.GONE);

        views.setViewVisibility(R.id.ll_title, View.VISIBLE);
        views.setTextViewText(R.id.tv_widget_title, recipe.getName());

        views.setViewVisibility(R.id.lv_widget_ingredients, View.VISIBLE);
        final Intent serviceIntent = new Intent(context, IngredientListWidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.lv_widget_ingredients, serviceIntent);
    }

    private void showEmptyMessage(final RemoteViews views) {
        views.setViewVisibility(R.id.ll_title, View.GONE);
        views.setViewVisibility(R.id.lv_widget_ingredients, View.GONE);
        views.setViewVisibility(R.id.ll_empty_data, View.VISIBLE);
    }

    private Recipe getRecipe(final Context context) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final String json = sharedPreferences.getString(context.getResources().getString(R.string.key_widget_value),null);
        return (null == json) ? null : new Gson().fromJson(json, Recipe.class);
    }
}
