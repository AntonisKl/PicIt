package com.example.android.picit.SchemaClasses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DimitrisLPC on 14/10/2017.
 */

public class Product {

    @SerializedName("ProductId")
    private int ProductId;
    @SerializedName("ProductName")
    private String productName;

    public int getProductId() {
        return ProductId;
    }

    public String getProductName() {
        return productName;
    }
}
