package com.example.knbs.censusapp;

import com.example.knbs.censusapp.api.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by killer on 2/15/17.
 */

public class RetrofitInit {

    public RetrofitInit (){

    }

    public ApiService initializeRetrofit(){
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client.addInterceptor(loggingInterceptor);

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EnumeratorIDActivity.BASE_URL)
                //.client(client.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService restAPI = retrofit.create(ApiService.class);

        return restAPI;
    }
}
