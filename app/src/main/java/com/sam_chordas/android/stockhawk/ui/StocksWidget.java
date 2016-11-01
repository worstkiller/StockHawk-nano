package com.sam_chordas.android.stockhawk.ui;

import android.app.LoaderManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.rest.QuoteCursorAdapter;
import com.sam_chordas.android.stockhawk.service.WidgetService;
import com.sam_chordas.android.stockhawk.util.WebContants;

/**
 * Created by OFFICE on 10/30/2016.
 */

public class StocksWidget extends AppWidgetProvider{

    Intent intent = null;

    Context context;

    int position = 0;

    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        intent = new Intent(context, WidgetService.class);
        intent.putExtra(WebContants.EXTRA_COUNT,position);
        context.startService(intent);
        Log.d("**widget called ",String.valueOf(position));
        position = position + 1;

    }

}
