/*
 * Copyright 2012 Oxygen Development
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.devoxy.fgawidget.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.devoxy.fgawidget.R;
import com.devoxy.fgawidget.service.UpdateWidgetService;

/**
 * Created by Dmitriy Tarasov.
 * <p/>
 * Email: dm.vl.tarasov@gmail.com
 * Date: 18.01.12
 * Time: 21:01
 */
public class FGAWidgetProvider extends AppWidgetProvider {

    public static String ACTION = "MyAction";
    public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";
    public static final int REQUEST_CODE = 0;

    public FGAWidgetProvider() {
        super();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        context.startService(new Intent(context, UpdateWidgetService.class));
        updateView(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO widget update
        super.onReceive(context, intent);
        Toast.makeText(context, "TODO update to random advice", Toast.LENGTH_LONG).show();
    }

    private void updateView(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        remoteViews.setOnClickPendingIntent(R.id.refresh, getIntent(context));
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    private PendingIntent getIntent(Context context) {
        Intent active = new Intent(context, FGAWidgetProvider.class);
        active.setAction(ACTION_WIDGET_RECEIVER);
        return PendingIntent.getBroadcast(context, 0, active, 0); // TODO magic constants
    }
}
