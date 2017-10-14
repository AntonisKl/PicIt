package com.example.android.picit.ServerHandler;

import android.content.Context;
import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DimitrisLPC on 14/10/2017.
 */

public class ServerClient {

    private static final String BASE_HOSTNAME = "10.0.2.2";
    public static final String BASE_URL = "http://" + BASE_HOSTNAME + ":3003";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
