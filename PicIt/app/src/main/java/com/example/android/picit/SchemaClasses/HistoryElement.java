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

public class HistoryElement {

    @SerializedName("pictureid")
    private int pictureId;
    @SerializedName("time")
    private String datetime;
    @SerializedName("ProductName")
    private String productName;
    @SerializedName("ProductId")
    private int productId;

    public int getPictureId() {
        return pictureId;
    }

    public String getDatetime() {
        String formatteddatetime = datetime.substring(0, datetime.indexOf("T")) + " " + datetime.substring(datetime.indexOf("T")+1, datetime.indexOf("Z")-4);
        return formatteddatetime;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductId() {
        return productId;
    }
}
