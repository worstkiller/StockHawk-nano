package com.sam_chordas.android.stockhawk.service;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.models.WidgetItem;
import com.sam_chordas.android.stockhawk.ui.StocksWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OFFICE on 10/30/2016.
 */

public class WidgetService extends RemoteViewsService {

    public WidgetService() {
        super();
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider (this.getApplicationContext(), intent);
    }

    private class WidgetDataProvider  implements RemoteViewsFactory {

        private List<WidgetItem> mWidgetItems = new ArrayList<WidgetItem>();
        private Context mContext;
        private int mAppWidgetId;

        public WidgetDataProvider (Context applicationContext, Intent intent) {

            mContext = applicationContext;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

        }

        @Override
        public void onCreate() {

            //first clear all the values if present any
            mWidgetItems.clear();

            Cursor cursor = null;
            String change = null;
            String symbol =  null;
            String price =  null;

              cursor = getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                    new String[] { QuoteColumns.SYMBOL, QuoteColumns.CHANGE, QuoteColumns.PERCENT_CHANGE }, null,
                    null, null);

            if (cursor.getCount() != 0) {

                cursor.moveToFirst();

                while (!cursor.isAfterLast()){
                        symbol = cursor.getString(cursor.getColumnIndex(QuoteColumns.SYMBOL));
                        change = cursor.getString(cursor.getColumnIndex(QuoteColumns.PERCENT_CHANGE));
                        price = cursor.getString(cursor.getColumnIndex(QuoteColumns.CHANGE));
                        mWidgetItems.add(new WidgetItem(symbol,change,price));
                        cursor.moveToNext();
                }
            }

            cursor.close();
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            mWidgetItems.clear();
        }

        @Override
        public int getCount() {
            return mWidgetItems.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.list_item_quote);
            rv.setTextViewText(R.id.stock_symbol, mWidgetItems.get(position).symbol);
            rv.setTextViewText(R.id.bid_price, mWidgetItems.get(position).price);
            rv.setTextViewText(R.id.change, mWidgetItems.get(position).change);
            // Return the remote views object.
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
