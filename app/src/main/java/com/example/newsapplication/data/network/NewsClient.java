package com.example.newsapplication.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsClient {
    private static NewsApi androidApi;

    public static NewsApi getClient(){
        if (androidApi == null){
            androidApi = retrofitBuilder();
        }else {
            return androidApi;
        }
        return androidApi;
    }

    public static NewsApi retrofitBuilder(){
        return new Retrofit.Builder()
                .baseUrl("http://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(NewsApi.class);
    }

}
