package com.example.android.picit.SchemaClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DimitrisLPC on 14/10/2017.
 */

public class User {

    @SerializedName("UserId")
    private int userId;

    public int getUserId(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt("usertoken", -1);
    }

    public int setUserId(Context context) {
        SharedPreferences.Editor prefsedit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefsedit.putInt("usertoken", )
    }

}
