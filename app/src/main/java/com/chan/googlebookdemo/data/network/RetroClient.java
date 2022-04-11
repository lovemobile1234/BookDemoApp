package com.chan.googlebookdemo.data.network;

import com.chan.googlebookdemo.BuildConfig;
import com.chan.googlebookdemo.constatnts.NetworkConstant;
import com.chan.googlebookdemo.utils.StringConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {
    private Retrofit retrofit;
    private static RetroClient object;

    private RetroClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
           // httpClient.addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstant.BASE_URL)
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        service = retrofit.create(RestApis.class);
    }

    public static RetroClient getRetroClient() {
        if (object == null) {
            object = new RetroClient();
        } else if (!object.getRetrofit().baseUrl().toString().equalsIgnoreCase(NetworkConstant.BASE_URL)) {
            object = new RetroClient();
        }
        return object;
    }

    private RestApis service;

    public RestApis getApiServices() {
        return object.service;
    }

    public Retrofit getRetrofit() {
        return object.retrofit;
    }
}
