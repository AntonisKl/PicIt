package com.example.android.picit;

/**
 * Created by antonis on 15-Oct-17.
 */

public class SimilarProduct {
    private int imgId;
    private String pName;

    public SimilarProduct(int imgId, String pName) {
        this.imgId = imgId;
        this.pName = pName;
    }


    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}
