package com.sam_chordas.android.stockhawk.ui;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.models.HistoryModel;
import com.sam_chordas.android.stockhawk.models.SeriesModel;
import com.sam_chordas.android.stockhawk.util.ConnectionDetector;
import com.sam_chordas.android.stockhawk.util.WebContants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by OFFICE on 10/22/2016.
 */

public class StockHistory extends AppCompatActivity {

    String END_URL ;
    LineChart chart;
    ProgressBar progressBar;
    TextView textView,textViewMin,textViewMax,textViewError;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        chart = (LineChart) findViewById(R.id.chart);
        textView = (TextView) findViewById(R.id.tvHistorySymbol);
        textViewMax = (TextView) findViewById(R.id.tvHistorySymbolMax);
        textViewMin = (TextView) findViewById(R.id.tvHistorySymbolMin);
        textViewError = (TextView) findViewById(R.id.tvHistoryError);
        linearLayout = (LinearLayout) findViewById(R.id.llHistoryData);
        progressBar = (ProgressBar) findViewById(R.id.pbHistoryLoading);


        if (new ConnectionDetector(StockHistory.this).isConnectingToInternet()){

            //internet is working load data

            linearLayout.setVisibility(View.GONE);
            textViewError.setVisibility(View.GONE);
            progressBar.setProgress(0);

            String symbolData = getIntent().getStringExtra(WebContants.SYMBOL);

            END_URL = "instrument/1.0/"+symbolData+"/chartdata;type=quote;range=5y/json";

            textView.setText( symbolData.toUpperCase());


            new FinanceBackgroundCall().execute();

        }else{
            linearLayout.setVisibility(View.GONE);
            progressBar.setProgress(0);
            progressBar.setVisibility(View.GONE);
            textViewError.setVisibility(View.VISIBLE);

        }
    }


    class FinanceBackgroundCall extends AsyncTask<Void,Void , String>{
        @Override
        protected String doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient();
            // code request code here
            Request request = new Request.Builder()
                    .url(WebContants.BASE_URL+END_URL)
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                String rawResponse = s.replace(getString(R.string.data_extra),"");
                rawResponse = rawResponse.substring(0,rawResponse.length()-1);
                String jsonConsumable = rawResponse.trim();
                Gson gson = new Gson();
                HistoryModel historyModel =  gson.fromJson(jsonConsumable, HistoryModel.class);
                parseData(historyModel);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                progressBar.setProgress(100);
                progressBar.setVisibility(View.GONE);
                textViewError.setVisibility(View.VISIBLE);
            }
        }
    }

    private void parseData(HistoryModel historyModel) {

        //parse data here and make consumable for app
        List<Entry> entries = new ArrayList<Entry>();

        for (SeriesModel data : historyModel.getSeries()) {

            // turn your data into Entry objects

            String toStringOpen = String.valueOf(data.getDate());
            String toStringClose = String.valueOf(data.getHigh());

            if (String.valueOf(data.getDate()).contains(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))){

                entries.add(new Entry(Float.valueOf(toStringOpen), Float.valueOf(toStringClose)));

            }

        }
        // add entries to dataset
        textViewMax.setText(String.valueOf(historyModel.getRanges().getClose().getMax()));
        textViewMin.setText(String.valueOf(historyModel.getRanges().getClose().getMin()));

        LineDataSet dataSet = new LineDataSet(entries, getString(R.string.stocks));
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();

        //data is parsed now make view visible
        linearLayout.setVisibility(View.VISIBLE);
        textViewError.setVisibility(View.GONE);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.GONE);
    }

}
