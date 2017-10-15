package com.example.android.picit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by antonis on 14-Oct-17.
 */

public class StoreResult {
    @SerializedName("store_storeid")
    private int shopId;
    @SerializedName("price")
    private int Price;
    @SerializedName("description")
    private String pDescription;
    @SerializedName("name")
    private String sName;


    public void getImgId(final ImageView imgView, Context context) {
        Picasso.with(context)
                .load("http://10.0.2.2:3003/store/" + shopId + "/logo")
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

    public String getsName() {
        return sName;
    }

    public String getpDescription() {
        return pDescription;
    }

    public int getPrice() {
        return Price;
    }

    public int getShopId() {
        return shopId;
    }
}
