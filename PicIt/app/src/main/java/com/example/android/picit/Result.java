package com.example.android.picit;

/**
 * Created by antonis on 14-Oct-17.
 */

public class Result {
    private int imgId;
    private float Price;
    private String pName, pDescription;

    public Result(int imgId, String pName, String pDescription, float price) {
        this.imgId = imgId;
        this.pName = pName;
        this.pDescription = pDescription;
        this.Price = price;
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

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }
}
