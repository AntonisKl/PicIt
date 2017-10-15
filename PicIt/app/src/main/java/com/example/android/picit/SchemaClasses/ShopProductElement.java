package com.example.android.picit.SchemaClasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by DimitrisLPC on 15/10/2017.
 */

public class ShopProductElement {

    @SerializedName("storeid")
    private int storeId;
    @SerializedName("Name")
    private String sName;
    @SerializedName("Url")
    private String url;
    @SerializedName("price")
    private int price;
    @SerializedName("available_quantity")
    private int quantity;
    @SerializedName("productname")
    private String pName;

    public void getsImage(final ImageView imgView, Context context) {
        Picasso.with(context)
                .load("http://10.0.2.2:3003/store/" + getStoreId() + "/logo")
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

    public int getStoreId() {
        return storeId;
    }

    public String getsName() {
        return sName;
    }

    public String getUrl() {
        return url;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getpName() {
        return pName;
    }
}
