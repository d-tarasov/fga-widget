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

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import com.devoxy.fgawidget.service.UpdateWidgetService;

/**
 * Created by Dmitriy Tarasov.
 * <p/>
 * Email: dm.vl.tarasov@gmail.com
 * Date: 18.01.12
 * Time: 21:01
 */
public class FGAWidgetProvider extends AppWidgetProvider {

    public FGAWidgetProvider() {
        super();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        context.startService(new Intent(context, UpdateWidgetService.class));
    }
}
