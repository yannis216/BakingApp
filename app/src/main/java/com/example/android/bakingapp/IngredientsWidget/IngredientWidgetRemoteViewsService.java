package com.example.android.bakingapp.IngredientsWidget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class IngredientWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public ListRemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
