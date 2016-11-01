package com.sam_chordas.android.stockhawk.models;

/**
 * Created by OFFICE on 10/24/2016.
 */

public class SeriesModel {

    private Integer Date;
    private Double close;
    private Double high;
    private Double low;
    private Double open;
    private Integer volume;

    /**
     *
     * @return
     * The date
     */
    public Integer getDate() {
        return Date;
    }

    /**
     *
     * @param date
     * The Date
     */
    public void setDate(Integer date) {
        this.Date = date;
    }

    /**
     *
     * @return
     * The close
     */
    public Double getClose() {
        return close;
    }

    /**
     *
     * @param close
     * The close
     */
    public void setClose(Double close) {
        this.close = close;
    }

    /**
     *
     * @return
     * The high
     */
    public Double getHigh() {
        return high;
    }

    /**
     *
     * @param high
     * The high
     */
    public void setHigh(Double high) {
        this.high = high;
    }

    /**
     *
     * @return
     * The low
     */
    public Double getLow() {
        return low;
    }

    /**
     *
     * @param low
     * The low
     */
    public void setLow(Double low) {
        this.low = low;
    }

    /**
     *
     * @return
     * The open
     */
    public Double getOpen() {
        return open;
    }

    /**
     *
     * @param open
     * The open
     */
    public void setOpen(Double open) {
        this.open = open;
    }

    /**
     *
     * @return
     * The volume
     */
    public Integer getVolume() {
        return volume;
    }

    /**
     *
     * @param volume
     * The volume
     */
    public void setVolume(Integer volume) {
        this.volume = volume;
    }


}
