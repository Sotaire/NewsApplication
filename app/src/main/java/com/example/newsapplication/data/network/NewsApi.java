package com.example.newsapplication.data.network;

import com.example.newsapplication.data.models.StatusModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("v2/top-headlines")
    Call<StatusModel> getNews(@Query("apiKey") String apikey,
                              @Query("country") String country,
                              @Query("category")String category,
                              @Query("page") int page,
                              @Query("pageSize") int pageSize);

    @GET("v2/everything")
    Call<StatusModel> getNewsByKeyWord(@Query("q") String q,
                                   @Query("language") String country,
                                   @Query("apiKey") String apikey,
                                   @Query("page") int page,
                                   @Query("pageSize") int pageSize);

}
