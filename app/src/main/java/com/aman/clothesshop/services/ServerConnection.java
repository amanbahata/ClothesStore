package com.aman.clothesshop.services;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerConnection {

    private static Retrofit retrofit;

    public static RequestInterface getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://private-anon-86f35620de-ddshop.apiary-mock.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(RequestInterface.class);
    }

}
