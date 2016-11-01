package com.sam_chordas.android.stockhawk.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ${vikas} on 10-06-2016.
 */
public class ConnectionDetector {

    private Context context;

    public ConnectionDetector(Context context){
        this.context = context;
    }

    public boolean isConnectingToInternet(){

    final ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null)
        {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null)
            {
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }

        return false;
    }
}
