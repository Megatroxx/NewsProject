package com.example.newsproject.network;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("v2/everything")
    Call<NewsResponse> getNews(
            @Query("page") int page,
            @Query("domains") String domains,
            @Query("language") String language,
            @Query("apiKey") String apiKey
    );

    String APIKEY = "2d8a511aea6644d9ae9a60260628c193";

    static ApiService create() {
        return new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(ApiService.class);
    }
}

