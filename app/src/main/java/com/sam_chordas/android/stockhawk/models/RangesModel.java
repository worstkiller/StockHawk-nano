package com.sam_chordas.android.stockhawk.models;

/**
 * Created by OFFICE on 10/24/2016.
 */

public class RangesModel {

    private CloseModel close;
    private HighModel high;
    private LowModel low;
    private OpenModel open;
    private VolumeModel volume;

    /**
     *
     * @return
     * The close
     */
    public CloseModel getClose() {
        return close;
    }

    /**
     *
     * @param close
     * The close
     */
    public void setClose(CloseModel close) {
        this.close = close;
    }

    /**
     *
     * @return
     * The high
     */
    public HighModel getHigh() {
        return high;
    }

    /**
     *
     * @param high
     * The high
     */
    public void setHigh(HighModel high) {
        this.high = high;
    }

    /**
     *
     * @return
     * The low
     */
    public LowModel getLow() {
        return low;
    }

    /**
     *
     * @param low
     * The low
     */
    public void setLow(LowModel low) {
        this.low = low;
    }

    /**
     *
     * @return
     * The open
     */
    public OpenModel getOpen() {
        return open;
    }

    /**
     *
     * @param open
     * The open
     */
    public void setOpen(OpenModel open) {
        this.open = open;
    }

    /**
     *
     * @return
     * The volume
     */
    public VolumeModel getVolume() {
        return volume;
    }

    /**
     *
     * @param volume
     * The volume
     */
    public void setVolume(VolumeModel volume) {
        this.volume = volume;
    }
}
