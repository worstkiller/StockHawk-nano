package com.sam_chordas.android.stockhawk.models;

/**
 * Created by OFFICE on 11/4/2016.
 */
public class WidgetItem {

    public String symbol,change,price;

    public WidgetItem(String symbol, String change, String price) {
        this.symbol = symbol;
        this.change = change;
        this.price = price;
    }

    public WidgetItem() {
    }
}
