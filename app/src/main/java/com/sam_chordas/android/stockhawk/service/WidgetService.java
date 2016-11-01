package com.sam_chordas.android.stockhawk.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;
import com.sam_chordas.android.stockhawk.ui.StocksWidget;
import com.sam_chordas.android.stockhawk.util.WebContants;

import java.util.Random;

/**
 * Created by OFFICE on 10/30/2016.
 */

public class WidgetService extends IntentService {
    private static final String LOG = "de.vogella.android.widget.example";

    public WidgetService() {
        super(LOG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
                .getApplicationContext());

        //get data from the database

        Cursor cursor = null;
        String change = getBaseContext().getResources().getString(R.string.screen_Reader_stock);
        String symbol =  getBaseContext().getResources().getString(R.string.screen_Reader_change);;

        int position  = intent.getIntExtra(WebContants.EXTRA_COUNT,0);

        cursor = getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                new String[] { QuoteColumns.SYMBOL, QuoteColumns.CHANGE }, null,
                null, null);

        if (cursor.getCount() != 0) {

            cursor.moveToFirst();
            while (!cursor.isAfterLast()){

                if (cursor.getPosition()==position){
                    symbol = cursor.getString(cursor.getColumnIndex(QuoteColumns.SYMBOL));
                    change = cursor.getString(cursor.getColumnIndex(QuoteColumns.CHANGE));
                    break;
                }
                cursor.moveToNext();
            }
        }

        cursor.close();


        ComponentName thisWidget = new ComponentName(getApplicationContext(),
                StocksWidget.class);
        int[] allWidgetIds2 = appWidgetManager.getAppWidgetIds(thisWidget);


        for (int widgetId : allWidgetIds2) {
            // create some random data

            RemoteViews remoteViews = new RemoteViews(this
                    .getApplicationContext().getPackageName(),
                    R.layout.widget_layout);

            // Set the text
            remoteViews.setTextViewText(R.id.widgetChange,
                    change);

            // Set the text
            remoteViews.setTextViewText(R.id.widgetStock,
                    symbol);

            // Register an onClickListener
            Intent clickIntent = new Intent(this.getApplicationContext(),
                    MyStocksActivity.class);

            clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                    allWidgetIds2);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    getApplicationContext(), 0, clickIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.llWidget, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        stopSelf();

    }
}
