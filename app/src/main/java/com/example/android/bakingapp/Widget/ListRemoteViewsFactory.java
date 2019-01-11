package com.example.android.bakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.Models.Ingredient;
import com.example.android.bakingapp.R;

import java.util.List;



    public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context mContext;
    List<Ingredient> ingredientList;

    public ListRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {
        Log.e("Factory onCreate", "Wurde gecalled");

    }

    @Override
    public void onDataSetChanged() {
        Log.e("onDataSetChanged", "Wurde gecalled");

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.e("getCount", "Wurde gecalled");
        if(ingredientList ==null){
            return 5;
        }
        return ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.e("getViewAt", "Wurde gecalled");
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
    }  //TODO May change this depoening on final listview layout!

    @Override
    public long getItemId(int i) {
        return i; //TODO oder einfach 1 setzen?
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
