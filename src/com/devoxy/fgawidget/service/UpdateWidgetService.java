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

package com.devoxy.fgawidget.service;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import com.devoxy.fgawidget.R;
import com.devoxy.fgawidget.web.AdviceUpdater;
import com.devoxy.fgawidget.widget.FGAWidgetProvider;

/**
 * Created by Dmitriy Tarasov.
 * <p/>
 * Email: dm.vl.tarasov@gmail.com
 * Date: 20.01.12
 * Time: 22:41
 */
public class UpdateWidgetService extends Service {

    @Override
    public void onStart(Intent intent, int startId) {
        RemoteViews updateViews = buildUpdate(this);
        ComponentName thisWidget = new ComponentName(this, FGAWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(thisWidget, updateViews);
    }

    private RemoteViews buildUpdate(Context context) {
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        String advice = AdviceUpdater.getTodayAdvice();
        if (advice == null) {
            advice = getString(R.string.connection_problem);
        }
        //updateViews.setTextViewText(R.id.advice, "- " + advice);
        return updateViews;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // don't need binding
    }
}
