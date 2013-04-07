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
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.devoxy.fgawidget.R;
import com.devoxy.fgawidget.service.UpdateWidgetService;
import com.devoxy.fgawidget.web.AdviceUpdater;
import com.dmitriy.tarasov.android.intents.IntentUtils;

/**
 * Created by Dmitriy Tarasov.
 * <p/>
 * Email: dm.vl.tarasov@gmail.com
 * Date: 18.01.12
 * Time: 21:01
 */
public class FGAWidgetProvider extends AppWidgetProvider {

    public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        context.startService(new Intent(context, UpdateWidgetService.class));
        updateView(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        RemoteViews updateViews = buildUpdate(context);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.updateAppWidget(
                new ComponentName(context.getPackageName(),
                FGAWidgetProvider.class.getName()),
                updateViews);
    }

    private RemoteViews buildUpdate(Context context) {
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        String advice = AdviceUpdater.getRandomAdvice(context);
        if (advice == null) {
            advice = context.getString(R.string.connection_problem);
        }
        updateViews.setTextViewText(R.id.advice, "- " + advice);

        Intent share = getShareIntent(context, advice);
        if (IntentUtils.isIntentAvailable(context, share)) {
            PendingIntent pendingShare = PendingIntent.getActivity(context, 0, share, PendingIntent.FLAG_UPDATE_CURRENT);
            updateViews.setOnClickPendingIntent(R.id.root, pendingShare);
        }
        return updateViews;
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

    private Intent getShareIntent(Context context, String advice) {
        String title = context.getString(R.string.fucking_great_advice);
        Intent shareIntent = IntentUtils.shareText(title, advice);
        return Intent.createChooser(shareIntent, context.getString(R.string.share_with_friends));
    }
}
