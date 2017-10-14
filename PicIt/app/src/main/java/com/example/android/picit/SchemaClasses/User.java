package com.example.android.picit.SchemaClasses;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.android.picit.ServerHandler.ServerClient;
import com.example.android.picit.ServerHandler.ServerInterface;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void setUserId(final Context context) {
        ServerInterface serverService = ServerClient.getClient(context).create(ServerInterface.class);
        final SharedPreferences.Editor prefsedit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        Call<Integer> addUserCall = serverService.addUser();
        addUserCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.code() < 300) {
                    prefsedit.putInt("usertoken", response.body());
                    prefsedit.apply();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("ONLOGINERROR", t.toString());
                Toast.makeText(context, "Something went wrong. Try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
