package com.example.android.picit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by heliix on 10/14/2017.
 */

public class HistoryEntry {
    private int imgId, prodId;
    private String pName, Date;

    public HistoryEntry(int imgId, String pName, String date, int prodId) {
        this.imgId = imgId;
        this.pName = pName;
        this.Date = date;
        this.prodId = prodId;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public int getProdId() {
        return prodId;
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

    public void getImage(final ImageView imgView, Context context) {
        Picasso.with(context)
                .load("http://10.0.2.2:3003/picture/" + getImgId())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
                        imgView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        //todo add default img
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }
}
