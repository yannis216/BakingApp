package com.example.android.bakingapp.Widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class WidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.e("onGetViewFactory", "Wurde gecalled");
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
