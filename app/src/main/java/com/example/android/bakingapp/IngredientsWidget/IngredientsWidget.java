package com.example.android.bakingapp.IngredientsWidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {
    Recipe mRecipe;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("onReceive", "" + intent.getSerializableExtra("Recipe"));
        mRecipe = (Recipe) intent.getSerializableExtra("Recipe");
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        if(mRecipe != null){
            Log.e("OnUpdate Recipe:", ""+mRecipe);
        }
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(
                    context.getPackageName(),
                    R.layout.ingredients_widget
            );

            Intent intent = new Intent(context, IngredientWidgetRemoteViewsService.class);
            if(mRecipe != null) {
                intent.putExtra("Recipe", mRecipe);
                Log.e("OnUpdate:", "Added Recipe to intent: "+mRecipe);

            }
            views.setRemoteAdapter(R.id.lv_ingredients_widget, intent);
            appWidgetManager.updateAppWidget(appWidgetId, views);

            Log.e("onUpdate", "Wurde gecalled");
        }
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_ingredients_widget);




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

