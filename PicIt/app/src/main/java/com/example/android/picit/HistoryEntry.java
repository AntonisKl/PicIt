package com.example.android.picit;

/**
 * Created by heliix on 10/14/2017.
 */

public class HistoryEntry {
    private int imgId;
    private String pName, Date;

    public HistoryEntry(int imgId, String pName, String date) {
        this.imgId = imgId;
        this.pName = pName;
        this.Date = date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public int getImgId() {
        return imgId;
    }

    public String getDate() {
        return Date;
    }

    public String getpName() {
        return pName;
    }
}
