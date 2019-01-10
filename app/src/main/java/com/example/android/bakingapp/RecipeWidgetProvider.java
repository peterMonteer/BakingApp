package com.example.android.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.example.android.bakingapp.Data.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    public static Recipe mRecipe;
    public static Intent mIntent;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetIds, Recipe recipe) {
        mRecipe = recipe;
        for (int appWidgetId : appWidgetIds) {

            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

            Intent adapterIntent = new Intent(context, WidgetService.class);
            views.setRemoteAdapter(R.id.widget_list_view, adapterIntent);

            if (mRecipe == null){
                mIntent = new Intent(context, MainActivity.class);
                views.setViewVisibility(R.id.no_recipe_warning_textview, View.VISIBLE);
                views.setViewVisibility(R.id.widget_list_view,View.GONE);
            } else {
                mIntent = new Intent(context, RecipeDetailsActivity.class);
                mIntent.putExtra("Recipe",recipe);
                views.setViewVisibility(R.id.no_recipe_warning_textview, View.GONE);
                views.setViewVisibility(R.id.widget_list_view,View.VISIBLE);
            }

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);
            views.setOnClickPendingIntent(R.id.widget_container, pendingIntent);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
            updateAppWidget(context, appWidgetManager, appWidgetIds,null);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

