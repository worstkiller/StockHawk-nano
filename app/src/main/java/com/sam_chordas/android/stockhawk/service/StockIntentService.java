package com.sam_chordas.android.stockhawk.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.gcm.TaskParams;
import com.sam_chordas.android.stockhawk.util.WebContants;

import static com.sam_chordas.android.stockhawk.util.WebContants.STOCKS_ADD;
import static com.sam_chordas.android.stockhawk.util.WebContants.STOCKS_TAG;
import static com.sam_chordas.android.stockhawk.util.WebContants.SYMBOL;

/**
 * Created by sam_chordas on 10/1/15.
 */
public class StockIntentService extends IntentService {

  public StockIntentService(){
    super(StockIntentService.class.getName());
  }

  public StockIntentService(String name) {
    super(name);
  }

  @Override protected void onHandleIntent(Intent intent) {
    Log.d(StockIntentService.class.getSimpleName(), WebContants.STOCKS_SERVICE);
    StockTaskService stockTaskService = new StockTaskService(this);
    Bundle args = new Bundle();
    if (intent.getStringExtra(STOCKS_TAG).equals(STOCKS_ADD)){
      args.putString(SYMBOL, intent.getStringExtra(WebContants.SYMBOL));
    }
    // We can call OnRunTask from the intent service to force it to run immediately instead of
    // scheduling a task.
    stockTaskService.onRunTask(new TaskParams(intent.getStringExtra(STOCKS_TAG), args));
  }
}
