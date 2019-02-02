package com.didiksazali.androidexpert.androidnews.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitRetrofit {

    public static String API_URL = "http://192.168.43.140/api/api_berita/";

    public static Retrofit setInit() {
        return new Retrofit.Builder().baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static APIServices getInstances() {
        return setInit().create(APIServices.class);
    }
}
