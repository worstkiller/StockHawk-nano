package com.sam_chordas.android.stockhawk.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OFFICE on 10/24/2016.
 */

public class HistoryModel {

    private MetaModel meta;
    private DateModel date;
    private List<Integer> labels = new ArrayList<Integer>();
    private RangesModel ranges;
    private List<SeriesModel> series = new ArrayList<SeriesModel>();

    /**
     *
     * @return
     * The meta
     */
    public MetaModel getMeta() {
        return meta;
    }

    /**
     *
     * @param meta
     * The meta
     */
    public void setMeta(MetaModel meta) {
        this.meta = meta;
    }

    /**
     *
     * @return
     * The date
     */
    public DateModel getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The Date
     */
    public void setDate(DateModel date) {
        this.date = date;
    }


    /**
     *
     * @return
     * The labels
     */
    public List<Integer> getLabels() {
        return labels;
    }

    /**
     *
     * @param labels
     * The labels
     */
    public void setLabels(List<Integer> labels) {
        this.labels = labels;
    }

    /**
     *
     * @return
     * The ranges
     */
    public RangesModel getRanges() {
        return ranges;
    }

    /**
     *
     * @param ranges
     * The ranges
     */
    public void setRanges(RangesModel ranges) {
        this.ranges = ranges;
    }

    /**
     *
     * @return
     * The series
     */
    public List<SeriesModel> getSeries() {
        return series;
    }

    /**
     *
     * @param series
     * The series
     */
    public void setSeries(List<SeriesModel> series) {
        this.series = series;
    }

    private List<TimeStampRange> timeStampRanges = new ArrayList<TimeStampRange>();

    /**
     *
     * @return
     * The timeStampRanges
     */
    public List<TimeStampRange> getTimeStampRanges() {
        return timeStampRanges;
    }

    /**
     *
     * @param timeStampRanges
     * The TimeStamp-Ranges
     */
    public void setTimeStampRanges(List<TimeStampRange> timeStampRanges) {
        this.timeStampRanges = timeStampRanges;
    }
}
