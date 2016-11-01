package com.sam_chordas.android.stockhawk.models;

/**
 * Created by OFFICE on 10/24/2016.
 */

public class MetaModel {
    private String uri;
    private String ticker;
    private String companyName;
    private String exchangeName;
    private String unit;
    private String timestamp;
    private String firstTrade;
    private String lastTrade;
    private String currency;
    private Double previous_close_price;

    /**
     *
     * @return
     * The uri
     */
    public String getUri() {
        return uri;
    }

    /**
     *
     * @param uri
     * The uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     *
     * @return
     * The ticker
     */
    public String getTicker() {
        return ticker;
    }

    /**
     *
     * @param ticker
     * The ticker
     */
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    /**
     *
     * @return
     * The companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *
     * @param companyName
     * The Company-Name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     *
     * @return
     * The exchangeName
     */
    public String getExchangeName() {
        return exchangeName;
    }

    /**
     *
     * @param exchangeName
     * The Exchange-Name
     */
    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    /**
     *
     * @return
     * The unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     *
     * @param unit
     * The unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     *
     * @return
     * The timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp
     * The timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     *
     * @return
     * The firstTrade
     */
    public String getFirstTrade() {
        return firstTrade;
    }

    /**
     *
     * @param firstTrade
     * The first-trade
     */
    public void setFirstTrade(String firstTrade) {
        this.firstTrade = firstTrade;
    }

    /**
     *
     * @return
     * The lastTrade
     */
    public String getLastTrade() {
        return lastTrade;
    }

    /**
     *
     * @param lastTrade
     * The last-trade
     */
    public void setLastTrade(String lastTrade) {
        this.lastTrade = lastTrade;
    }

    /**
     *
     * @return
     * The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency
     * The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     *
     * @return
     * The previousClosePrice
     */
    public Double getPreviousClosePrice() {
        return previous_close_price;
    }

    /**
     *
     * @param previousClosePrice
     * The previous_close_price
     */
    public void setPreviousClosePrice(Double previousClosePrice) {
        this.previous_close_price = previousClosePrice;
    }
}
