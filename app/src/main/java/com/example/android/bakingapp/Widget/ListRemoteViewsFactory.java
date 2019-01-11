package com.example.android.bakingapp.Widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.Models.Ingredient;
import com.example.android.bakingapp.R;

import java.util.List;



    public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context mContext;
    List<Ingredient> ingredientList;

    public ListRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

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
        if( ingredientList == null || ingredientList.size() == 0){
            return null;
        }
        String ingredient = ingredientList.get(position).getIngredient();
        String quantity = "" +ingredientList.get(position).getQuantity();
        String measure = ingredientList.get(position).getMeasure();

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredients_widget);
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
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
