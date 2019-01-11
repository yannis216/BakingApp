package com.example.android.bakingapp.Widget;

import android.content.Intent;

public class RemoteViewsService extends android.widget.RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}
