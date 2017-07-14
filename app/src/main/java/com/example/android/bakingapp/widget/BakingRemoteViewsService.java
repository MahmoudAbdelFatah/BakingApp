package com.example.android.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Mahmoud on 7/11/2017.
 */

public class BakingRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingRemoteViewsFactory(this.getApplicationContext());
    }
}
