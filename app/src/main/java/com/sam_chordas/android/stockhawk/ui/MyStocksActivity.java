package com.sam_chordas.android.stockhawk.ui;

import android.app.LoaderManager;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.models.MessageNotify;
import com.sam_chordas.android.stockhawk.models.WidgetItem;
import com.sam_chordas.android.stockhawk.rest.QuoteCursorAdapter;
import com.sam_chordas.android.stockhawk.rest.RecyclerViewItemClickListener;
import com.sam_chordas.android.stockhawk.rest.Utils;
import com.sam_chordas.android.stockhawk.service.StockIntentService;
import com.sam_chordas.android.stockhawk.service.StockTaskService;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.melnykov.fab.FloatingActionButton;
import com.sam_chordas.android.stockhawk.touch_helper.SimpleItemTouchHelperCallback;
import com.sam_chordas.android.stockhawk.util.ConnectionDetector;
import com.sam_chordas.android.stockhawk.util.WebContants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MyStocksActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

  /**
   * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
   */

  /**
   * Used to store the last screen title. For use in {@link #restoreActionBar()}.
   */
  private CharSequence mTitle;
  private Intent mServiceIntent;
  private ItemTouchHelper mItemTouchHelper;
  private static final int CURSOR_LOADER_ID = 0;
  private QuoteCursorAdapter mCursorAdapter;
  private Context mContext;
  private Cursor mCursor;
  boolean isConnected;
    TextView textViewError;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      mContext = this;

      isConnected = new ConnectionDetector(mContext).isConnectingToInternet();

      setContentView(R.layout.activity_my_stocks);

      textViewError = (TextView) findViewById(R.id.tvStockError);

      loadData(savedInstanceState);

  }

    private void loadData(Bundle savedInstanceState) {

        //here load the data as internet is available

        //task to get vales from the service
        mServiceIntent = new Intent(this, StockIntentService.class);
        if (savedInstanceState == null){
            // Run the initialize task service so that some stocks appear upon an empty database
            mServiceIntent.putExtra(WebContants.STOCKS_TAG, WebContants.STOCKS_INIT);
            if (isConnected){
                startService(mServiceIntent);
                textViewError.setVisibility(View.GONE);
            } else{
            }
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);

        mCursorAdapter = new QuoteCursorAdapter(this, null);
        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(this,
                new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View v, int position) {
                        //TODO:
                        // do something on item click

                        String symbol = null;

                        mCursor.moveToFirst();
                        while (!mCursor.isAfterLast()){

                            if (mCursor.getPosition()==position){
                                symbol = mCursor.getString(mCursor.getColumnIndex(QuoteColumns.SYMBOL));
                            }
                            mCursor.moveToNext();
                        }
                        mCursor.close();
                        Intent intent = new Intent(MyStocksActivity.this,StockHistory.class);
                        intent.putExtra(WebContants.SYMBOL,symbol);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                }));
        recyclerView.setAdapter(mCursorAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (isConnected){
                    new MaterialDialog.Builder(MyStocksActivity.this).title(R.string.symbol_search)
                            .content(R.string.content_test)
                            .inputType(InputType.TYPE_CLASS_TEXT)
                            .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                                @Override public void onInput(MaterialDialog dialog, final CharSequence input) {
                                    // On FAB click, receive user input. Make sure the stock doesn't already exist
                                    // in the DB and proceed accordingly

                                    Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {

                                            Cursor cursor = getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                                                    new String[] { QuoteColumns.SYMBOL }, QuoteColumns.SYMBOL + " LIKE ?",
                                                    new String[] { input.toString().toUpperCase() }, null);

                                            String toBeCompare = null;

                                            cursor.moveToFirst();

                                            if (cursor.getCount() != 0) {

                                                cursor.moveToFirst();

                                                while (!cursor.isAfterLast()){

                                                    toBeCompare=cursor.getString(cursor.getColumnIndex(QuoteColumns.SYMBOL));
                                                    cursor.moveToNext();
                                                }


                                                if (cursor.getCount() != 0  && toBeCompare.equalsIgnoreCase(input.toString())) {
                                                    Toast toast =
                                                            Toast.makeText(MyStocksActivity.this, getString(R.string.stocks_toast),
                                                                    Toast.LENGTH_LONG);
                                                    toast.setGravity(Gravity.CENTER, Gravity.CENTER, 0);
                                                    toast.show();
                                                    return;
                                                } else if (input.toString().equals(toBeCompare)){
                                                    Toast toast =
                                                            Toast.makeText(MyStocksActivity.this, getString(R.string.stocks_toast),
                                                                    Toast.LENGTH_LONG);
                                                    toast.setGravity(Gravity.CENTER, Gravity.CENTER, 0);
                                                    toast.show();

                                                }

                                            } else {
                                                // Add the stock to DB
                                                mServiceIntent.putExtra(WebContants.STOCKS_TAG, WebContants.STOCKS_ADD);
                                                mServiceIntent.putExtra(WebContants.SYMBOL, input.toString());
                                                startService(mServiceIntent);
                                            }

                                        }
                                    };
                                    runnable.run();
                                }
                            })
                            .show();
                } else {
                    networkToast();
                }

            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mCursorAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        mTitle = getTitle();
        if (isConnected){
            long period = 3600L;
            long flex = 10L;
            String periodicTag = WebContants.STOCKS_PERIODIC;

            // create a periodic task to pull stocks once every hour after the app has been opened. This
            // is so Widget data stays up to date.
            PeriodicTask periodicTask = new PeriodicTask.Builder()
                    .setService(StockTaskService.class)
                    .setPeriod(period)
                    .setFlex(flex)
                    .setTag(periodicTag)
                    .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED)
                    .setRequiresCharging(false)
                    .build();
            // Schedule task with tag "periodic." This ensure that only the stocks present in the DB
            // are updated.
            GcmNetworkManager.getInstance(this).schedule(periodicTask);
        }

    }


    @Override
  public void onResume() {
    super.onResume();
    getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
  }

  public void networkToast(){
    Toast.makeText(mContext, getString(R.string.network_toast), Toast.LENGTH_SHORT).show();
  }

  public void restoreActionBar() {
    ActionBar actionBar = getSupportActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    actionBar.setDisplayShowTitleEnabled(true);
    actionBar.setTitle(mTitle);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.my_stocks, menu);
      restoreActionBar();
      return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    if (id == R.id.action_change_units){
      // this is for changing stock changes from percent value to dollar value
      Utils.showPercent = !Utils.showPercent;
      this.getContentResolver().notifyChange(QuoteProvider.Quotes.CONTENT_URI, null);
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args){
    // This narrows the return to only the stocks that are most current.
    return new CursorLoader(this, QuoteProvider.Quotes.CONTENT_URI,
        new String[]{ QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE,
            QuoteColumns.PERCENT_CHANGE, QuoteColumns.CHANGE, QuoteColumns.ISUP},
        QuoteColumns.ISCURRENT + " = ?",
        new String[]{"1"},
        null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data){
    mCursorAdapter.swapCursor(data);
    mCursor = data;

      if (!isConnected  && data.getCount()!=0){

          textViewError.setVisibility(View.GONE);
          Toast.makeText(MyStocksActivity.this,getResources().getString(R.string.error_no_internet),Toast.LENGTH_SHORT).show();
      }
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader){
    mCursorAdapter.swapCursor(null);
  }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageNotify event) {
        Toast.makeText(MyStocksActivity.this, event.message, Toast.LENGTH_SHORT).show();
    }

  @Override
  public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override
  public void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }

}
