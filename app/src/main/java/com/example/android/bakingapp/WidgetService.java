package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsAdapterRemoteFactory(this.getApplicationContext());
    }
}


