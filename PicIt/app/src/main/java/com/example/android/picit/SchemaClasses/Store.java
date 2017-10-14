package com.example.android.picit.SchemaClasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by DimitrisLPC on 14/10/2017.
 */

public class Store {

    @SerializedName("StoreId")
    private int storeId;
    @SerializedName("Name")
    private String Name;
    @SerializedName("Url")
    private String url;

    public void getLogo(final ImageView imgView, Context context) {
        Picasso.with(context)
                .load("https://10.0.2.2:3003/shops/" + getStoreId() + "/logo")
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

    public String getName() {
        return Name;
    }

    public String getUrl() {
        return url;
    }
}
