package com.example.android.bakingapp.IngredientsWidget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class IngredientWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public ListRemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.e("onGetViewFactory", "Wurde gecalled");
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
