package com.example.android.bakingapp.IngredientsWidget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.Models.Ingredient;
import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.R;
import com.google.gson.Gson;

import java.util.List;



    public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context mContext;
    List<Ingredient> ingredientList;
    SharedPreferences mPrefs;

    public ListRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {

        mPrefs = mContext.getSharedPreferences("mPreference", 0);
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(mPrefs.getString("ingredients", ""),  Recipe.class);
        ingredientList = recipe.getIngredients();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {

        if(ingredientList ==null){
            return 0;
        }
        return ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if( ingredientList == null || ingredientList.size() == 0) {
            return null;
        }

        String ingredient = ingredientList.get(position).getIngredient();
        String quantity = ""+ ingredientList.get(position).getQuantity();
        String measure = ingredientList.get(position).getMeasure();

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.activity_recipe_ingredient_list_item);
        views.setTextViewText(R.id.tv_ingredients_ingredient, ingredient);
        views.setTextViewText(R.id.tv_ingredients_quantity, quantity);
        views.setTextViewText(R.id.tv_ingredients_measure, measure);

        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }  //TODO May change this depending on final listview layout!

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
